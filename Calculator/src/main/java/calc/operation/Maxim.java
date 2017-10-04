
package calc.operation;


import static java.lang.Math.max;

public class Maxim extends Operation {


    public double execute(double value1, double value2)
    {
        return max(value1,value2);
    }
}