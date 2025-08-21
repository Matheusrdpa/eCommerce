package com.estudo.eCommerce.services;

import com.estudo.eCommerce.dto.CategoryDTO;
import com.estudo.eCommerce.entities.Category;
import com.estudo.eCommerce.repositories.CategoryRepository;
import com.estudo.eCommerce.services.Exceptions.DbException;
import com.estudo.eCommerce.services.Exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class CategoriesServiceTests {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;

    List<Category> categories = Arrays.asList(new Category(1L, "test1"),new Category(2L, "test2"));
    Long existingId = 1L;
    Long nonExistingId = 999L;
    Long dependentId = 2L;

    @Test
    void findAllShouldReturnListOfCategories(){
        when(categoryRepository.findAll()).thenReturn(categories);
        List<CategoryDTO> res = categoryService.findAll();

        Assertions.assertNotNull(res);
        Assertions.assertEquals("test1", res.get(0).getName());
        Assertions.assertEquals("test2", res.get(1).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void findByIdShouldReturnCategory(){
        Category category1 = new Category(1L, "test1");
        when(categoryRepository.findById(existingId)).thenReturn(Optional.of(category1));
        CategoryDTO res = categoryService.findById(existingId);
        Assertions.assertEquals("test1", res.getName());
        verify(categoryRepository, times(1)).findById(existingId);
    }

    @Test
    void findByIdShouldThrowResourceNotFoundWhenIdDoesntExist(){
        when(categoryRepository.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.findById(nonExistingId);
        });
    }

    @Test
    void saveShouldSaveCategory(){
        CategoryDTO category1 = new CategoryDTO();
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> {
            Category category = invocation.getArgument(0);
            category.setId(existingId);
            category.setName("test");
            return category;
        });

        CategoryDTO res = categoryService.createCategory(category1);
        Assertions.assertEquals("test", res.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void updateShouldUpdateCategory(){
        Category startCategory = new Category(1L, "test1");
        Category updatedCategory = new Category(2L, "test2");
        CategoryDTO endCategory = new CategoryDTO(2L, "test2");
        when(categoryRepository.getReferenceById(existingId)).thenReturn(startCategory);
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);


        CategoryDTO category2 = categoryService.updateCategory(existingId, endCategory);

        Assertions.assertEquals("test2", category2.getName());
        Assertions.assertEquals(2L, category2.getId());
        verify(categoryRepository, times(1)).getReferenceById(existingId);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void updateShouldThrowResourceNotFoundWhenIdDoesNotExist(){
        when(categoryRepository.getReferenceById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.updateCategory(nonExistingId, new CategoryDTO());
        });
    }

    @Test
    void deleteShouldDoNothingWhenIdExists(){
        when(categoryRepository.existsById(existingId)).thenReturn(true);
        categoryService.deleteCategory(existingId);
        verify(categoryRepository, times(1)).deleteById(existingId);
    }

    @Test
    void deleteShouldThrowResourceNotFoundWhenIdDoesNotExist(){
        when(categoryRepository.existsById(nonExistingId)).thenReturn(false);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            categoryService.deleteCategory(nonExistingId);
        });
    }

    @Test
    void deleteShouldThrowDbExceptionWhenCategoryDependsOnEntity(){
        when(categoryRepository.existsById(dependentId)).thenReturn(true);
        doThrow(DataIntegrityViolationException.class).when(categoryRepository).deleteById(dependentId);
        Assertions.assertThrows(DbException.class, () -> {
            categoryService.deleteCategory(dependentId);
        });
    }


}
