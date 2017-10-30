package order;

import java.util.Objects;

public class Order implements Comparable<Order> {
    private int id;

    private int price;

    private int quantity;

    public Order(int i){
        this(i,i,i);
    }
    public Order(int id, int price, int quantity) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Order) {
            final Order that = (Order) o;
            return Objects.equals(this.id, that.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + price;
        result = 31 * result + quantity;
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
    @Override
    public int compareTo(Order object){
        return Integer.compare(this.id,object.id);

    }


}
