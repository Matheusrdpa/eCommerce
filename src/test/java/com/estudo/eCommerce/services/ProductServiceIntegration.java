package com.estudo.eCommerce.services;

import com.estudo.eCommerce.dto.ProductDTO;
import com.estudo.eCommerce.repositories.ProductRepository;
import com.estudo.eCommerce.services.Exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
public class ProductServiceIntegration {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private Long existingId;
    private Long nonExistingId;
    private Long quantity;
    private String orderName;
    private String emptyName;

    @BeforeEach
    public void setUp() {
        emptyName = "";
        orderName = "Bike";
        existingId = 1L;
        nonExistingId = 1000L;
        quantity = 25L;
    }

    @Test
    public void findAllPagedeShouldReturnPageWhenPage0Size10(){
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ProductDTO> res = productService.findAll(emptyName,pageRequest);

        Assertions.assertEquals(0, res.getNumber());
        Assertions.assertEquals(10,res.getSize());
    }

    @Test
    public void findAllPagedOrderedByNameShouldReturnOrderedPage(){
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ProductDTO> res = productService.findAll(orderName,pageRequest);

        Assertions.assertEquals("Bike a", res.getContent().get(0).getName());
        Assertions.assertEquals("Bike b", res.getContent().get(1).getName());
        Assertions.assertEquals("Bike c", res.getContent().get(2).getName());
    }

    @Test
    public void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist(){
        PageRequest pageRequest = PageRequest.of(11, 10);
        Page<ProductDTO> res = productService.findAll(orderName,pageRequest);
        Assertions.assertTrue(res.isEmpty());
    }


    @Test
    public void deleteProductShouldReturn1LessProduct(){
        productService.deleteProduct(existingId);
        Assertions.assertEquals(quantity - 1L, productRepository.count());
    }

    @Test
    public void deleteShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(nonExistingId));
    }


}
