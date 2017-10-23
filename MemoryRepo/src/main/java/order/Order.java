package order;

public class Order implements Comparable {
    private int id;

    private int price;

    private int quantity;

    public Order(int id, int price, int quantity) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        return true;
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
    public int compareTo(Object object){
        Order o=(Order) object;
        if (this.id > o.id)
            return 1;
        else if (this.id < o.id)
            return -1;
        else
            if(this.price>o.price)
                return 1;
            else if(this.price<o.price)
                return 0;
            else return 0;

    }


}
