package calc;

import calc.operation.*;


public class Calculator {

    public double calculate(String toCalc) {
        Operation op=new Nothing();


         String[] args=toCalc.split("[+\\-*/mM]");
         if(args.length==2){

             double value1=Double.parseDouble(args[0]);
             double value2=Double.parseDouble(args[1]);

            String operator= toCalc.replaceAll("[\\d.]", "");
            switch(operator){
                case "+": op=new Plus();break;
                case "-": op=new Minus();break;
                case "*": op=new Multiply();break;
                case "/": op=new Divide();break;
                case "m": op=new Minim();break;
                case "M": op=new Maxim();break;

                default:new Nothing();

            }

            return op.execute(value1,value2);
        }
        else {

             throw new NumberFormatException("invalid");
         }

    }
}
