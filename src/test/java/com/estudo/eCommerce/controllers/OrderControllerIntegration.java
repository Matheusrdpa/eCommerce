package com.estudo.eCommerce.controllers;


import com.estudo.eCommerce.dto.OrderDTO;
import com.estudo.eCommerce.entities.Order;
import com.estudo.eCommerce.repositories.OrderRepository;
import com.estudo.eCommerce.services.OrderService;
import com.estudo.eCommerce.tests.OrderFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerIntegration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ObjectMapper objectMapper;

    Long existingId = 1L;
    Long nonExistingId = 999L;

    @Test
    void findAllOrdersShouldReturnListOfOrders() throws Exception {

        mockMvc.perform(get("/orders")).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].user.name").value("Messmer"))
        .andExpect(jsonPath("$").isArray());
    }

    @Test
    void findOrderByIdShouldReturnOrderWhenIdExists() throws Exception {
        mockMvc.perform(get("/orders/" + existingId)).andExpect(status().isOk())
                .andExpect(jsonPath("$.user.name").value("Messmer"));
    }

    @Test
    void findOrderByIdShouldThrowResourceNotFoundWhenIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/orders/" + nonExistingId)).andExpect(status().isNotFound());
    }

    @Test
    void insertOrderShouldInsertInDatabase() throws Exception {
        OrderDTO order = OrderFactory.createOrderDTO();
        mockMvc.perform(post("/orders/" + (existingId + 1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user.name").value("Radahn"))
                .andExpect(header().exists("Location"));
    }

    @Test
    void insertOrderShouldThrowResourceNotFoundWhenIdDoesNotExist() throws Exception {
        OrderDTO order = OrderFactory.createOrderDTO();
        mockMvc.perform(post("/orders/" + nonExistingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteOrderShouldDeleteOrderWhenIdExists() throws Exception {
        mockMvc.perform(delete("/orders/" + existingId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteOrderShouldThrowResourceNotFoundWhenIdDoesNotExist() throws Exception {
        mockMvc.perform(delete("/orders/" + nonExistingId))
                .andExpect(status().isNotFound());
    }

}
