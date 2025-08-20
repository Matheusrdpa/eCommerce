package com.estudo.eCommerce.services;

import com.estudo.eCommerce.dto.OrderDTO;
import com.estudo.eCommerce.entities.*;

import com.estudo.eCommerce.repositories.OrderItemRepository;
import com.estudo.eCommerce.repositories.OrderRepository;

import com.estudo.eCommerce.repositories.UserRepository;
import com.estudo.eCommerce.services.Exceptions.DbException;
import com.estudo.eCommerce.services.Exceptions.ResourceNotFoundException;
import com.estudo.eCommerce.tests.OrderFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    OrderItemRepository orderItemRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    Order order = OrderFactory.createOrder();
    List<Order> orders = List.of(order);

    Long existingId = 1L;
    Long nonExistingId = 2L;
    Long dependentId = 3L;

    @Test
    void findAllShouldReturnListOfOrdersDto() {
        when(orderRepository.findAll()).thenReturn(orders);
        List<OrderDTO> result = orderService.findAll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(orders.size(), result.size());
        Assertions.assertEquals("John", result.get(0).getUser().getName());
    }

    @Test
    void findByIdShouldReturnOrderDtoWhenIdExists() {
        when(orderRepository.findById(existingId)).thenReturn(Optional.of(order));
        OrderDTO result = orderService.findById(existingId);
        Assertions.assertEquals(order.getId(), result.getId());
    }

    @Test
    void findByIdShouldReturnResourceNotFoundWhenIdDoesNotExist() {
        when(orderRepository.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> orderService.findById(nonExistingId));
    }

    @Test
    void insertShouldReturnOrderDto() {
        OrderDTO res = new OrderDTO();
        User user = new User(1L,"John","John@test.com","123456", LocalDate.now());

        when(userRepository.getReferenceById(existingId)).thenReturn(user);
        when(userRepository.existsById(existingId)).thenReturn(true);

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(1L);
            order.setStatus(OrderStatus.PAID);
            return order;
        });

        OrderDTO inserted = orderService.insert(res,existingId);
        Assertions.assertNotNull(order);
        Assertions.assertEquals(1L, order.getId());
        Assertions.assertEquals("John", inserted.getUser().getName());
        Assertions.assertEquals(OrderStatus.PAID, inserted.getStatus());
    }

    @Test
    void deleteShouldDoNothingWhenIdExists() {
        when(orderRepository.existsById(existingId)).thenReturn(true);
        orderService.deleteOrder(existingId);
        verify(orderRepository, times(1)).deleteById(existingId);
    }

    @Test
    void deleteShouldThrowExceptionWhenIdDoesNotExist() {
        when(orderRepository.existsById(nonExistingId)).thenReturn(false);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> orderService.deleteOrder(nonExistingId));
    }
}
