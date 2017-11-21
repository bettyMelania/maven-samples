package producerConsumer;

import model.Person;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{

    private BlockingQueue<Person> queue;

    public Consumer(BlockingQueue<Person> q){
        this.queue=q;
    }

    @Override
    public void run() {
        try{
            try (ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("ProducerConsumer/personsPC.list"))) {

                Person p;
                while((p = queue.take()).getName()!=""){
                    out.writeObject(p);
                    //System.out.println("Consumed "+p.getName());
                }
            } catch ( IOException e) {
                e.printStackTrace();
            }

        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}