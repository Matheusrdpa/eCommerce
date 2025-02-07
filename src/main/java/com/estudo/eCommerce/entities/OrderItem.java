package com.estudo.eCommerce.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orderItems")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class OrderItem {

    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();

    private Integer quantity;
    private Double price;

    public OrderItem(Integer quantity, Double price, Product product, Order order) {
        this.quantity = quantity;
        this.price = price;
        id.setProduct(product);
        id.setOrder(order);
    }

    public Order getOrder() {
        return id.getOrder();
    }

    public void setOrder(Order order) {
        id.setOrder(order);
    }

    public Product getProduct() {
        return id.getProduct();
    }

    public void setProduct(Product product) {
        id.setProduct(product);
    }
}
