package decimals;
import java.math.BigDecimal;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TestDecimal {

    public static void main(String[] args) {
        BlockingQueue<BigDecimal> queue = new ArrayBlockingQueue<BigDecimal>(1000);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();

        System.out.println("Producer and Consumer for decimals has been started");
    }

}
