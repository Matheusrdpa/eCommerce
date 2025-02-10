package com.estudo.eCommerce.controllers;

import com.estudo.eCommerce.dto.OrderDTO;
import com.estudo.eCommerce.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    @Operation(description = "Returns all existing orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "orders sucessfully returned"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity<List<OrderDTO>> findAll() {
        List<OrderDTO> orderDTO = orderService.findAll();
        return ResponseEntity.ok().body(orderDTO);
    }

    @GetMapping(value = "/{id}")
    @Operation(description = "Returns an order searching by order ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "order was sucessfully found"),
            @ApiResponse(responseCode = "404", description = "order matching specific id was not found"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
        OrderDTO orderDTO = orderService.findById(id);
        return ResponseEntity.ok().body(orderDTO);
    }

    @PostMapping("/{id}")
    @Operation(description = "Creates a new order using an already existing user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product successfully created"),
            @ApiResponse(responseCode = "404", description = "User matching specific id was not found"),
            @ApiResponse(responseCode = "422", description = "Invalid order fields"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity<OrderDTO> insert(@RequestBody @Valid OrderDTO orderDTO, @PathVariable Long id) {
        orderDTO = orderService.insert(orderDTO, id);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orderDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(orderDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Searches an order by id and deletes it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order was sucessfully found and removed"),
            @ApiResponse(responseCode = "404", description = "Order matching specific id was not found"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
