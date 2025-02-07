package com.estudo.eCommerce.services;

import com.estudo.eCommerce.dto.OrderDTO;
import com.estudo.eCommerce.dto.OrderItemDTO;
import com.estudo.eCommerce.dto.UserDTO;
import com.estudo.eCommerce.entities.*;
import com.estudo.eCommerce.repositories.OrderItemRepository;
import com.estudo.eCommerce.repositories.OrderRepository;
import com.estudo.eCommerce.repositories.ProductRepository;
import com.estudo.eCommerce.repositories.UserRepository;
import com.estudo.eCommerce.services.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id){
       Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found for this id"));
       return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO insert(OrderDTO orderDTO, Long userId){
        Order order = new Order();

        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        User user = userRepository.getReferenceById(userId);
        order.setClient(user);

        for (OrderItemDTO item : orderDTO.getItems()) {
            Product product = productRepository.getReferenceById(item.getProductId());
            OrderItem orderItem = new OrderItem(item.getQuantity(), item.getPrice(), product,order);
            order.getOrderItems().add(orderItem);
        }
        order = orderRepository.save(order);
        orderItemRepository.saveAll(order.getOrderItems());

        return new OrderDTO(order);
    }
}
