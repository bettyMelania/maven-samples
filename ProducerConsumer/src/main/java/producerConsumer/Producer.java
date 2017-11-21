package producerConsumer;

import model.Person;

import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;

public class Producer implements Runnable {

 private BlockingQueue<Person> queue;
 private Matcher matcher;

 public Producer(BlockingQueue<Person> q, Matcher m){
  this.queue=q;
  this.matcher=m;
 }
 @Override
 public void run() {
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
         try {
             queue.put(p);
             //System.out.println("Produced "+p.getName());
         }catch (InterruptedException e) {
             e.printStackTrace();
         }
     }

     Person pN = new Person("","","");
     try {
         queue.put(pN);
     } catch (InterruptedException e) {
         e.printStackTrace();
     }
 }

}