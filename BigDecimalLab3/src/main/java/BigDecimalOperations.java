import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class BigDecimalOperations {
    private List<BigDecimal> list;

    public BigDecimalOperations(int dim){
        list=new ArrayList<>();

        for(int i=0;i<dim;i++){
            list.add(new BigDecimal(i));
        }
    }
    public BigDecimal sum() {
        Stream<BigDecimal> stream=list.stream();
        return stream.reduce(BigDecimal::add).get();
    }
    public BigDecimal average() {
        Stream<BigDecimal> stream=list.stream();
        BigDecimal sum=sum();
        return sum.divide(new BigDecimal(list.size()));
    }

    public void top() {
        Stream<BigDecimal> stream=list.stream();
        stream.sorted((o1, o2) -> o2.compareTo(o1))
                .limit(list.size()/10)
                .forEach(System.out::println);
    }
}
