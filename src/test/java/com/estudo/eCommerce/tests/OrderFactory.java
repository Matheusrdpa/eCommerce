package com.estudo.eCommerce.tests;

import com.estudo.eCommerce.dto.OrderDTO;
import com.estudo.eCommerce.entities.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class OrderFactory {
    public static Order createOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.PAID);
        User user = new User(1L,"John","John@test.com","123456", LocalDate.now());
        order.setClient(user);
        Payment payment = new Payment(1L,Instant.now(), PaymentMethod.CASH,order);
        order.setPayment(payment);
        return order;
    }

    public static OrderDTO createOrderDTO() {
        Order order = new Order();
        Set<OrderItem> items = new HashSet<>();

        Product product = createProduct();

        OrderItem item = new OrderItem();
        item.setProduct(product);
        items.add(item);

        order.setId(1L);
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.PAID);
        User user = new User(1L,"John","John@test.com","123456", LocalDate.now());
        order.setClient(user);
        Payment payment = new Payment(1L,Instant.now(), PaymentMethod.CASH,order);
        order.setPayment(payment);
        order.setOrderItems(items);
        return new OrderDTO(order);
    }

    public static Product createProduct() {
       return new Product(1L, "product", "description", 20.00, "img");
    }
}
