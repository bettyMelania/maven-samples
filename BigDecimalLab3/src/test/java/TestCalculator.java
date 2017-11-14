
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
        op = new BigDecimalOperations();
    }

    @Test
    public void test() {

        assertEquals(new BigDecimal(499500), op.sum());
        assertEquals(new BigDecimal(499.5), op.average());
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