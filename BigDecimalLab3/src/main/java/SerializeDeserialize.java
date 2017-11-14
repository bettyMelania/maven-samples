import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SerializeDeserialize {
    private List<BigDecimal> list;

    public SerializeDeserialize(){
        list=new ArrayList<>();

        for(int i=0;i<100000;i++){
            list.add(new BigDecimal(i));
        }
    }

    public void serialize(){
        try (ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("bigList.list"))) {
            out.writeObject(list.size());
            for (BigDecimal b: list) {
                out.writeObject(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deserialize(){
        list=new ArrayList<>();
        try (ObjectInputStream in=new ObjectInputStream(new FileInputStream("bigList.list"))) {
            int nr=(int)in.readObject();
            for(int i=0;i<nr;i++)
                    list.add((BigDecimal) in.readObject());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void print(){
        for (BigDecimal b:list) {
            System.out.print(b);
        }
    }


}
