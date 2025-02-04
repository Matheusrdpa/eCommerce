package com.estudo.eCommerce.entities;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "orderItem")
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
}
