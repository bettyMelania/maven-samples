package producerConsumer;

import model.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestPC {

    public static void main(String[] args) {
        Matcher m=getMatcher();
        BlockingQueue<Person> queue = new ArrayBlockingQueue<Person>(1000);
        Producer producer = new Producer(queue,m);
        Consumer consumer = new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();

        System.out.println("Producer and Consumer has been started");
    }

    public static Matcher getMatcher() {
        Path p= Paths.get("ProducerConsumer/inputPC.txt");
        String content="";
        try {
            content= Files.readAllLines(p).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String regexp="(([A-Z]([A-Za-z])*~){3}[12]\\d{12}~[A-Za-z][A-Za-z._]*@[a-z]+.[a-z]+%)";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(content);


        return matcher;
    }
}
