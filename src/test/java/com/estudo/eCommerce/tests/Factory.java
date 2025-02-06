package com.estudo.eCommerce.tests;

import com.estudo.eCommerce.dto.ProductDTO;
import com.estudo.eCommerce.entities.Category;
import com.estudo.eCommerce.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct() {
        Category category = new Category(1L, "books");
        Product product = new Product(1L, "Ripe", "Amazing book", 100.0, "https://Pinterest.com/img.png");
        product.getCategories().add(category);
        return product;
    }

    public static Product createProduct(String name) {
        Product product = createProduct();
        product.setName(name);
        return product;
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product,product.getCategories());
    }
}
