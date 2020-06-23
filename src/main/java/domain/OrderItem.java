package domain;

public class OrderItem {
    //购买数量
    private int buynum;
    private Order order;
    private Product product;

    @Override
    public String toString() {
        return "OrderItem{" +
                "buynum=" + buynum +
                ", order=" + order +
                ", product=" + product +
                '}';
    }

    public OrderItem() {
    }

    public int getBuynum() {
        return buynum;
    }

    public void setBuynum(int buynum) {
        this.buynum = buynum;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
