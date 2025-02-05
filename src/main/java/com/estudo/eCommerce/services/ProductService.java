package com.estudo.eCommerce.services;

import com.estudo.eCommerce.dto.ProductDTO;
import com.estudo.eCommerce.entities.Product;
import com.estudo.eCommerce.repositories.ProductRepository;
import com.estudo.eCommerce.services.Exceptions.DbException;
import com.estudo.eCommerce.services.Exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(String name, Pageable pageable) {
        Page<Product> products = repository.searchProductsByName(name, pageable);
        return products.map(product -> new ProductDTO(product));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
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

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
       try {
           Product product = repository.getReferenceById(id);

           product.setName(productDTO.getName());
           product.setDescription(productDTO.getDescription());
           product.setPrice(productDTO.getPrice());
           product.setImgUrl(productDTO.getImgUrl());

           product = repository.save(product);

           return new ProductDTO(product);
       }catch (EntityNotFoundException e) {
           throw new ResourceNotFoundException("Product not found");
       }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteProduct(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found");
        }
        try {
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
                throw new DbException("Entity depends on another entity and can't be removed");
        }
    }
}
