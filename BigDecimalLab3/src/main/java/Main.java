import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        int nr=0;
        for(int i=0;i<1000;i++){
            nr+=i;
        }
        System.out.print("sum is: "+nr);
        System.out.print("avg is: "+nr/1000);

    }


}
