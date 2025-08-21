package com.estudo.eCommerce.controllers;

import com.estudo.eCommerce.entities.Category;
import com.estudo.eCommerce.repositories.CategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CategoryControllerIntegrationTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    Long existingId = 1L;
    Long nonExistingId = 999L;
    Long dependentId = 2L;

    @Test
    void findAllShouldReturnAllCategories() throws Exception {
        mockMvc.perform(get("/categories")).andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("Eletronics"));
    }

    @Test
    void findByIdShouldReturnCategory() throws Exception {
        mockMvc.perform(get("/categories/" + existingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Eletronics"));
    }

    @Test
    void findByIdShouldThrowResourceNotFoundWhenIdDoesNotExist() throws Exception {
        mockMvc.perform(get("/categories/" + nonExistingId)).andExpect(status().isNotFound());
    }

    @Test
    void saveShouldSaveCategory() throws Exception {
        Category category = new Category();
        category.setName("teste");
        mockMvc.perform(post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value("teste"))
        .andExpect(jsonPath("$.id").value(4L));
    }

    @Test
    void updateShouldUpdateCategoryWhenIdExists() throws Exception {
        Category category = new Category();
        category.setName("teste");
        mockMvc.perform(put("/categories/" + existingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk());
        mockMvc.perform(get("/categories/" + existingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("teste"));
    }

    @Test
    void updateShouldThrowResourceNotFoundWhenIdDoesNotExist() throws Exception {
        Category category = new Category();
        category.setName("teste");
        mockMvc.perform(put("/categories/" + nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteShouldDeleteCategory() throws Exception {
        mockMvc.perform(delete("/categories/" + existingId)).andExpect(status().isNoContent());
    }

    @Test
    void deleteShouldThrowResourceNotFoundWhenIdDoesNotExist() throws Exception {
        mockMvc.perform(delete("/categories/" + nonExistingId)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void deleteShouldThrowDbExceptionWhenIdDepends() throws Exception {
        mockMvc.perform(delete("/categories/" + dependentId)).andExpect(status().isBadRequest());
    }
}
