package com.estudo.eCommerce.controllers;


import com.estudo.eCommerce.dto.OrderDTO;

import com.estudo.eCommerce.services.Exceptions.ResourceNotFoundException;
import com.estudo.eCommerce.services.OrderService;
import com.estudo.eCommerce.tests.OrderFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;



import java.util.List;

@WebMvcTest(OrderController.class)
public class OrderControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    Long existingId = 1L;
    Long nonExistingId = 999L;

    @Test
    void getAllOrdersShouldReturnListOfOrder() throws Exception {
        List<OrderDTO> orders = List.of(OrderFactory.createOrderDTO());
        when(orderService.findAll()).thenReturn(orders);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].user.name").value("John"));
    }

    @Test
    void getOrderByIdShouldReturnOrderIfIdExists() throws Exception {
        when(orderService.findById(existingId)).thenReturn(OrderFactory.createOrderDTO());

        mockMvc.perform(get("/orders/" + existingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.user.name").value("John"));

        verify(orderService).findById(existingId);
    }

    @Test
    void getOrderByIdShouldThrowResourceNotFoundWhenIdDoesNotExist() throws Exception {
        when(orderService.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get("/orders/" + nonExistingId)).andExpect(status().isNotFound());
    }

    @Test
    void insertShouldInsertOrder() throws Exception {
        OrderDTO orderDTO = OrderFactory.createOrderDTO();
        when(orderService.insert(any(OrderDTO.class), eq(1L))).thenReturn(orderDTO);

        mockMvc.perform(post("/orders/" + existingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.user.name").value("John"));
    }

    @Test
    void deleteShouldDeleteOrderWhenIdExists() throws Exception {
        doNothing().when(orderService).deleteOrder(existingId);
        mockMvc.perform(delete("/orders/" + existingId))
                .andExpect(status().isNoContent());

        verify(orderService,times(1)).deleteOrder(existingId);
    }

    @Test
    void deleteShouldThrowResourceNotFoundWhenIdDoesNotExist() throws Exception {
        doThrow(ResourceNotFoundException.class).when(orderService).deleteOrder(nonExistingId);
        mockMvc.perform(delete("/orders/" + nonExistingId))
                .andExpect(status().isNotFound());
    }

}
