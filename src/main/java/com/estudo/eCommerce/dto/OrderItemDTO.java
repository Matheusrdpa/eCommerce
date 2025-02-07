package com.estudo.eCommerce.dto;

import com.estudo.eCommerce.entities.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItemDTO {

    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
    private String imgUrl;

    public OrderItemDTO(OrderItem orderItem) {
        this.productId = orderItem.getProduct().getId();
        this.productName = orderItem.getProduct().getName();
        this.price = orderItem.getProduct().getPrice();
        this.quantity = orderItem.getQuantity();
        this.imgUrl = orderItem.getProduct().getImgUrl();
    }
}
