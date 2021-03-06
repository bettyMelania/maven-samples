package tests;

import calc.Calculator;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class TestCalculator{

    private Calculator calc;

    @Before
    public void setUp() {
        calc = new Calculator();
    }

    @Test
    public void testPlus() {
        String a1="1+2";
        assertThat(calc.calculate(a1),is((double)3));
        String a2="19+2";
        assertThat(calc.calculate(a2),is((double)21));
    }

    @Test
    public void testMinus() {
        String a1="1-2";
        assertThat(calc.calculate(a1),is((double)-1));
        String a2="19-2";
        assertThat(calc.calculate(a2),is((double)17));
    }

    @Test
    public void testMultiply() {
        String a1="1*2";
        assertThat(calc.calculate(a1),is((double)2));
        String a2="19*2";
        assertThat(calc.calculate(a2),is((double)38));
    }

    @Test
    public void testDivide() {
        String a1="1/2";
        assertThat(calc.calculate(a1),is(0.5));
        String a2="18/2";
        assertThat(calc.calculate(a2),is((double)9));
    }

    @Test
    public void testMin() {

        String a1="1m2";
        assertThat(calc.calculate(a1),is((double)1));
        String a2="9m12";
        assertThat(calc.calculate(a2),is((double)9));
    }

    @Test
    public void testMax() {

        String a1="1M2";
        assertThat(calc.calculate(a1),is((double)2));
        String a2="9M12";
        assertThat(calc.calculate(a2),is((double)12));
    }

    @Test(expected = NumberFormatException.class)
    public void testError() {
        String a1="1/";
        calc.calculate(a1);
        String a2="1gedfv/hgbfv";
        calc.calculate(a2);
    }


}
