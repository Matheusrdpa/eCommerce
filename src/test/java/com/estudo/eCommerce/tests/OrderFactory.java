package com.estudo.eCommerce.tests;

import com.estudo.eCommerce.entities.*;

import java.time.Instant;
import java.time.LocalDate;

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
}
