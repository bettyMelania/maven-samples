package calc;

import calc.operation.*;

import java.util.HashMap;
import java.util.Map;


public class Calculator {

    private static final Map<String, Operation> myMap;
    static
    {
        myMap = new HashMap<String, Operation>();
        myMap.put("+", new Plus());
        myMap.put("-", new Minus());
        myMap.put("*", new Multiply());
        myMap.put("/", new Divide());
        myMap.put("M", new Maxim());
        myMap.put("m", new Minim());

    }

    public double calculate(String toCalc) throws NumberFormatException{
        Operation op=new Nothing();


         String[] args=toCalc.split("[+\\-*/mM=]");
         if(args.length==2){

             double value1=Double.parseDouble(args[0]);
             double value2=Double.parseDouble(args[1]);

            String operator= toCalc.replaceAll("[\\d.]", "");


           if (myMap.containsKey(operator)){
                op=myMap.get(operator);
             }
             else
                op=new Nothing();



            return op.execute(value1,value2);
        }
        else {

             throw new NumberFormatException("invalid");
         }

    }
}
