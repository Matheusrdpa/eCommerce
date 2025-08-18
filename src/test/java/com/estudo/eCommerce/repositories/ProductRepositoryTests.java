package com.estudo.eCommerce.repositories;

import com.estudo.eCommerce.entities.Product;
import com.estudo.eCommerce.tests.ProductFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    private Long existingId;
    private Long quantity;

    @BeforeEach
    public void setUp() {
        existingId = 1L;
        quantity = 25L;
    }

    @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
        Product product = ProductFactory.createProduct();
        product.setId(null);

        product = productRepository.save(product);
        Optional<Product> foundProduct = productRepository.findById(product.getId());

        Assertions.assertNotNull(product.getId());
        Assertions.assertTrue(foundProduct.isPresent());
        Assertions.assertSame(product, foundProduct.get());
        Assertions.assertEquals(quantity + 1, foundProduct.get().getId());
    }
}
