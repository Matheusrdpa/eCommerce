package com.estudo.eCommerce.services;

import com.estudo.eCommerce.dto.ProductDTO;
import com.estudo.eCommerce.entities.Product;
import com.estudo.eCommerce.repositories.ProductRepository;
import com.estudo.eCommerce.services.Exceptions.DbException;
import com.estudo.eCommerce.services.Exceptions.ResourceNotFoundException;
import com.estudo.eCommerce.tests.ProductFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;


@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Long existingId;
    private Long nonExistingd;
    private Long dependantId;
    private String productName;
    private Product product;
    private ProductDTO productDTO;
    private PageImpl<Product> page;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingd = 1000L;
        dependantId = 3L;
        productName = "Phone";
        product = ProductFactory.createProduct(productName);
        productDTO = new ProductDTO(product);
        page = new PageImpl<>(List.of(product));

        Mockito.when(productRepository.findAll(ArgumentMatchers.any(),(Pageable)ArgumentMatchers.any())).thenReturn(page);

        Mockito.when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.findById(nonExistingd)).thenThrow(ResourceNotFoundException.class);

        Mockito.when(productRepository.searchProductsByName(ArgumentMatchers.any(), (Pageable) ArgumentMatchers.any())).thenReturn(page);

        Mockito.when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);

        Mockito.when(productRepository.getReferenceById(existingId)).thenReturn(product);
        Mockito.when(productRepository.getReferenceById(nonExistingd)).thenThrow(ResourceNotFoundException.class);

        Mockito.when(productRepository.existsById(existingId)).thenReturn(true);
        Mockito.when(productRepository.existsById(nonExistingd)).thenReturn(false);
        Mockito.when(productRepository.existsById(dependantId)).thenReturn(true);

        Mockito.doNothing().when(productRepository).deleteById(existingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(dependantId);
    }

    @Test
    public void findAllShouldReturnProductDTOPaged() {

        Pageable pageable = PageRequest.of(0, 1);

        Page<ProductDTO> result = productService.findAll(productName, pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getSize(), 1);
    }

    @Test
    public void findByIdShouldReturnProductDTOWhenIdExists() {

        ProductDTO result = productService.findById(existingId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), existingId);
        Assertions.assertEquals(result.getName(), product.getName());
        Assertions.assertEquals(result.getDescription(), product.getDescription());

    }

    @Test
    public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.findById(nonExistingd);
        });
    }



    @Test
    public void insertShouldReturnProductDTO() {

        ProductDTO result = productService.createProduct(productDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), product.getId());
    }

    @Test
    public void createProductsShouldReturnProductDTOWhenIdExists() {

        ProductDTO result = productService.updateProduct(existingId, productDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), existingId);
        Assertions.assertEquals(result.getName(), productDTO.getName());
    }

    @Test
    public void updateProductsShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.updateProduct(nonExistingd, productDTO);
        });
    }

    @Test
    public void deleteProductsShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            productService.deleteProduct(existingId);
        });
    }

    @Test
    public void deleteProductsShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.deleteProduct(nonExistingd);
        });
    }

    @Test
    public void deleteProductsShouldThrowDbExceptionWhenIdIsDependant() {
        Assertions.assertThrows(DbException.class, () -> {
            productService.deleteProduct(dependantId);
        });
    }
}
