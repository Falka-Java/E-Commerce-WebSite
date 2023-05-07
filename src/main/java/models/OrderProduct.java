package models;

import java.util.List;

public class OrderProduct {
    private Order order;
    private List<CartProduct> productList;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<CartProduct> getProductList() {
        return productList;
    }

    public void setProductList(List<CartProduct> productList) {
        this.productList = productList;
    }

    public OrderProduct(Order order, List<CartProduct> productList) {
        this.order = order;
        this.productList = productList;
    }
}
