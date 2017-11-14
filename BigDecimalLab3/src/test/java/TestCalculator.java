
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class TestCalculator {

    private BigDecimalOperations op;

    @Before
    public void setUp() {

    }

    @Test
    public void test() {
        int nr=0;
        for(int i=0;i<1000;i++){
            nr+=i;
        }
        int sum=nr;
        double medie=(double) nr/1000;
        op = new BigDecimalOperations(1000);
        assertEquals(new BigDecimal(sum), op.sum());
        assertEquals(new BigDecimal(medie), op.average());
        op.top();

       nr=0;
        for(int i=0;i<100;i++){
            nr+=i;
        }
        sum=nr;
        medie=(double) nr/100;
        op = new BigDecimalOperations(100);
        assertEquals(new BigDecimal(sum), op.sum());
        assertEquals(new BigDecimal(medie), op.average());
        op.top();
    }

    @Test
    public void testSerialize() {
        SerializeDeserialize sd=new SerializeDeserialize();
        sd.serialize();
        sd.deserialize();
        //sd.print();
    }
}