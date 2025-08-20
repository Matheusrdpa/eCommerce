package com.estudo.eCommerce.services;


import com.estudo.eCommerce.dto.OrderDTO;
import com.estudo.eCommerce.entities.Order;
import com.estudo.eCommerce.repositories.OrderRepository;
import com.estudo.eCommerce.services.Exceptions.ResourceNotFoundException;
import com.estudo.eCommerce.tests.OrderFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
@Import(OrderService.class)
@Transactional
public class OrderServiceIntegrationTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    Long existingId = 1L;
    Long nonExistingId = 99L;

    @Test
    void findAllShouldReturnListOfOrderDTO() {
        List<OrderDTO> orders = orderService.findAll();
        Assertions.assertNotNull(orders);
        Assertions.assertFalse(orders.isEmpty());
        Assertions.assertEquals(4, orders.size());
    }

    @Test
    void findByIdShouldReturnOrderDTO() {
        OrderDTO order = orderService.findById(existingId);
        Assertions.assertNotNull(order);
        Assertions.assertEquals(existingId, order.getId());
        Assertions.assertEquals("Messmer", order.getUser().getName());
    }

    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesntExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            orderService.findById(nonExistingId);
        });
    }

    @Test
    void insertShouldCreateOrderInDatabase(){
        OrderDTO order = OrderFactory.createOrderDTO();
        OrderDTO savedOrder = orderService.insert(order, 1L);
        Assertions.assertNotNull(savedOrder);
        Assertions.assertEquals(5L, savedOrder.getId());
        Assertions.assertEquals("Margit", savedOrder.getUser().getName());
    }

    @Test
    void deleteShouldDeleteOrderInDatabaseWhenIdExists(){
        orderService.deleteOrder(existingId);
        Assertions.assertEquals(3, orderRepository.count());
        Assertions.assertFalse(orderRepository.existsById(existingId));
    }

    @Test
    void deleteShouldThrowResourceNotFoundWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            orderService.deleteOrder(nonExistingId);
        });
        Assertions.assertEquals(4, orderRepository.count());
    }
}
