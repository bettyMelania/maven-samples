package decimals;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{

    private BlockingQueue<BigDecimal> queue;

    public Consumer(BlockingQueue<BigDecimal> q){
        this.queue=q;
    }

    @Override
    public void run() {
        ArrayList<BigDecimal> list=new ArrayList<>();
        try (ObjectInputStream in=new ObjectInputStream(new FileInputStream("ProducerConsumer/bigList.list"))) {


            BigDecimal b;
            BigDecimal limit=new BigDecimal(-1);
            while((b = queue.take()).compareTo(limit)!=0){
                list.add((BigDecimal) in.readObject());
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
        /*
            for (BigDecimal b:list) {
                System.out.print(b);
            }
            */


    }
}