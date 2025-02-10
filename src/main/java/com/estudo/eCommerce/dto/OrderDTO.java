package com.estudo.eCommerce.dto;

import com.estudo.eCommerce.entities.Order;
import com.estudo.eCommerce.entities.OrderItem;
import com.estudo.eCommerce.entities.OrderStatus;
import com.estudo.eCommerce.entities.Payment;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class OrderDTO {
    private Long id;
    private Instant moment;
    private OrderStatus status;
    private PaymentDTO payment;
    private UserDTO user;
    @NotEmpty(message = "Needs to have at least one item")
    private List<OrderItemDTO> items = new ArrayList<>();

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.moment = order.getMoment();
        this.status = order.getStatus();
        this.user = new UserDTO(order.getClient());
        this.payment = (order.getPayment() == null) ? null : new PaymentDTO(order.getPayment());

        for (OrderItem item : order.getOrderItems()){
                OrderItemDTO itemDTO = new OrderItemDTO(item);
                items.add(itemDTO);
        }
    }
}
