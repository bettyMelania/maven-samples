package decimals;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

 private BlockingQueue<BigDecimal> queue;

 public Producer(BlockingQueue<BigDecimal> q){
  this.queue=q;
 }
 @Override
 public void run() {

     try (ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("ProducerConsumer/bigList.list"))) {
         for (int i = 0; i < 1000000; i++) {
             BigDecimal b = new BigDecimal(i);
             out.writeObject(b);
             queue.put(b);
         }
     }catch (Exception e) {
             e.printStackTrace();
     }


     BigDecimal bD=new BigDecimal(-1);
     try {
         queue.put(bD);
     } catch (InterruptedException e) {
         e.printStackTrace();
     }
 }

}