package calc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String [] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            System.out.println("Enter operation");
            String s = br.readLine();
            Calculator c = new Calculator();
            try{
                System.out.println("=" + c.calculate(s));
            }catch(NumberFormatException e) {
                System.out.println("Incorrect operation");
            }
        }

    }
}
