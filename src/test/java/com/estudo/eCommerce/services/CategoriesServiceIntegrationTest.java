package com.estudo.eCommerce.services;

import com.estudo.eCommerce.dto.CategoryDTO;
import com.estudo.eCommerce.entities.Category;
import com.estudo.eCommerce.repositories.CategoryRepository;
import com.estudo.eCommerce.services.Exceptions.DbException;
import com.estudo.eCommerce.services.Exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
@Import(CategoryService.class)
@Transactional
public class CategoriesServiceIntegrationTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;


    @Test
    void findAllShouldReturnAllCategories() {
        List<CategoryDTO> categories = categoryService.findAll();
        Assertions.assertNotNull(categories);
        Assertions.assertFalse(categories.isEmpty());
        Assertions.assertEquals("Eletronics", categories.get(0).getName());
    }

    @Test
    void findByIdShouldReturnCategory() {
        CategoryDTO categoryDTO = categoryService.findById(1L);
        Assertions.assertNotNull(categoryDTO);
        Assertions.assertEquals("Eletronics", categoryDTO.getName());
    }

    @Test
    void findByIdShouldThrowResourceNotFoundWhenIdDoesntExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.findById(999L);
        });
    }

    @Test
    void findByIdShouldThrowResourceNotFoundWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            CategoryDTO categoryDTO = categoryService.findById(999L);
        });
    }

    @Test
    void saveShouldIncrementInDatabase() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryService.createCategory(categoryDTO);
        Assertions.assertEquals(4, categoryRepository.count());
    }

    @Test
    void deleteShouldDeleteCategoryWhenIdExists() {
        CategoryDTO categoryDTO = new CategoryDTO();
        CategoryDTO saved = categoryService.createCategory(categoryDTO);

        Long countBefore = categoryRepository.count();

        categoryService.deleteCategory(saved.getId());
        Assertions.assertEquals(countBefore - 1 , categoryRepository.count());
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void deleteShouldThrowDbExceptionWhenIdDepends() {
        Assertions.assertThrows(DbException.class, () -> {
            categoryService.deleteCategory(1L);
        });
    }

    @Test
    void deleteShouldThrowResourceNotFoundWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.deleteCategory(999L);
        });
    }
}
