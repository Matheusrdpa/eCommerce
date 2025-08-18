package com.estudo.eCommerce.controllers;

import com.estudo.eCommerce.dto.ProductDTO;
import com.estudo.eCommerce.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    @Operation(description = "Returns all existing products paged")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products successfully returned"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity<Page<ProductDTO>> getAllProducts(@RequestParam(name = "name", defaultValue = "") String name, Pageable pageable) {
        Page<ProductDTO> productList = service.findAll(name,pageable);
        return ResponseEntity.ok(productList);
    }

    @GetMapping(value = "/{id}")
    @Operation(description = "Returns a product searching by product ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product was sucessfully found"),
            @ApiResponse(responseCode = "404", description = "Product matching specific id was not found"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = service.findById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    @Operation(description = "Creates a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product successfully created"),
            @ApiResponse(responseCode = "422", description = "Invalid product fields"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        productDTO = service.createProduct(productDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(productDTO);
    }



    @PutMapping(value = "/{id}")
    @Operation(description = "Updates an already existing product searching by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product was successfully updated"),
            @ApiResponse(responseCode = "404", description = "Product matching specific id was not found"),
            @ApiResponse(responseCode = "422", description = "Invalid product fields"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        service.updateProduct(id, productDTO);
        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(description = "Searches a product by id and deletes it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product was sucessfully found and removed"),
            @ApiResponse(responseCode = "400", description = "Order depends on another entity"),
            @ApiResponse(responseCode = "404", description = "Product matching specific id was not found"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
