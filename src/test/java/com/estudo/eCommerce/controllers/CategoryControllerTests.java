package com.estudo.eCommerce.controllers;

import com.estudo.eCommerce.dto.CategoryDTO;


import com.estudo.eCommerce.repositories.CategoryRepository;
import com.estudo.eCommerce.services.CategoryService;
import com.estudo.eCommerce.services.Exceptions.DbException;
import com.estudo.eCommerce.services.Exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.List;


@WebMvcTest(CategoryController.class)
public class CategoryControllerTests {

    @MockitoBean
    private CategoryService categoryService;

    @MockitoBean
    private CategoryRepository categoryRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    Long existingId = 1L;
    Long nonExistingId = 999L;
    Long dependingId = 2L;

    @Test
    void findAllShouldReturnAllCategories() throws Exception {
        CategoryDTO categoryDTO1 = new CategoryDTO(1L, "test1");
        CategoryDTO categoryDTO2 = new CategoryDTO(2L, "test2");
        List<CategoryDTO> categories = List.of(categoryDTO1, categoryDTO2);

        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("test1"));
    }

    @Test
    void findByIdShouldReturnCategory() throws Exception {
        CategoryDTO categoryDTO1 = new CategoryDTO(1L, "test1");
        when(categoryService.findById(existingId)).thenReturn(categoryDTO1);
        mockMvc.perform(get("/categories/" + existingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test1"));
    }
    @Test
    void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesntExist() throws Exception {
        when(categoryService.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get("/categories/" + nonExistingId)).andExpect(status().isNotFound());
    }

    @Test
    void saveShouldSaveCategory() throws Exception {
        CategoryDTO categoryDTO1 = new CategoryDTO(1L, "test1");
        when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(categoryDTO1);
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("test1"));
    }

    @Test
    void updateShouldUpdateCategoryWhenIdExists() throws Exception {
        CategoryDTO categoryDTO1 = new CategoryDTO(1L, "test1");

        when(categoryService.updateCategory(eq(existingId), any(CategoryDTO.class))).thenReturn(categoryDTO1);
        mockMvc.perform(put("/categories/" + existingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test1"));
    }

    @Test
    void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() throws Exception {
        CategoryDTO categoryDTO1 = new CategoryDTO(1L, "test1");
        when(categoryService.updateCategory(eq(nonExistingId), any(CategoryDTO.class))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(put("/categories/" + nonExistingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteShouldDoNothingWhenIdExists() throws Exception {
        when(categoryRepository.existsById(existingId)).thenReturn(true);
        mockMvc.perform(delete("/categories/" + existingId)).andExpect(status().isNoContent());
        verify(categoryService).deleteCategory(existingId);
    }

    @Test
    void deleteShouldThrowResourceNotFoundWhenIdDoesNotExist() throws Exception {
        doThrow(ResourceNotFoundException.class).when(categoryService).deleteCategory(nonExistingId);
        mockMvc.perform(delete("/categories/" + nonExistingId)).andExpect(status().isNotFound());
        verify(categoryService).deleteCategory(nonExistingId);
    }

    @Test
    void deleteShouldThrowDbExceptionWhenIdDepends() throws Exception {
        doThrow(DbException.class).when(categoryService).deleteCategory(dependingId);
        mockMvc.perform(delete("/categories/" + dependingId))
                .andExpect(status().isBadRequest());
        verify(categoryService).deleteCategory(dependingId);
    }
}
