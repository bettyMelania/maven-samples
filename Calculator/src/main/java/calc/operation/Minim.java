
package calc.operation;


import static java.lang.Math.min;

public class Minim extends Operation {


    public double execute(double value1, double value2)
    {
        return min(value1,value2);
    }
}