package com.estudo.eCommerce.services;

import com.estudo.eCommerce.dto.ProductDTO;
import com.estudo.eCommerce.entities.Product;
import com.estudo.eCommerce.repositories.ProductRepository;
import com.estudo.eCommerce.services.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        List<Product> products = repository.findAll();
        return products.stream().map(product -> new ProductDTO(product)).toList();
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        ProductDTO productDTO = new ProductDTO(product);
        return productDTO;
    }

    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = new Product();

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImgUrl(productDTO.getImgUrl());

        product = repository.save(product);

        repository.save(product);
        return new ProductDTO(product);
    }

}
