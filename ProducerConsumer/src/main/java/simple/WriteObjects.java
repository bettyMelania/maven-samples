package simple;

import model.Person;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WriteObjects {
    private List<Person> list;

    public WriteObjects(){
        list=new ArrayList<>();
    }

    public String read(){

        Path p= Paths.get("ProducerConsumer/input4.txt");
        try {
            return Files.readAllLines(p).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
    public void createObjects(){
        String content=read();
        String regexp="(([A-Z]([A-Za-z])*~){3}[12]\\d{12}~[A-Za-z][A-Za-z._]*@[a-z]+.[a-z]+%)";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(content);
        String name;
        String[] personS;
        String cnp;
        String mail;
        while (matcher.find()) {
            personS=matcher.group(1).split("~");
            name=personS[0]+" "+personS[1]+" "+personS[2];
            cnp=personS[3];
            mail=personS[4];
            Person p=new Person(name,cnp,mail);
            list.add(p);
            //System.out.println(p);
        }

    }

    public void write(){
        try (ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("ProducerConsumer/persons.list"))) {
            out.writeObject(list.size());
            for (Person b: list) {
                out.writeObject(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
