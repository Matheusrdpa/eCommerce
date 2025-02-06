package com.estudo.eCommerce.controllers;

import com.estudo.eCommerce.dto.ProductDTO;
import com.estudo.eCommerce.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIntegration {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existingId;
    private Long nonExistingId;
    private Long quantity;
    private ProductDTO  productDTO;

    @BeforeEach
    void setUp() throws Exception {
        productDTO = Factory.createProductDTO();
        existingId = 1L;
        nonExistingId = 1000L;
        quantity = 25L;

    }

    @Test
    public void findAllShouldReturnSortedPageWhenSortByName() throws Exception {
        ResultActions res = mockMvc.perform(get("/products?page=0&size=12&sort=name,asc")
                .accept(MediaType.APPLICATION_JSON));

        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(status().isOk());
        res.andExpect(jsonPath("$.totalElements").value(quantity));
        res.andExpect(jsonPath("$.content").exists());
        res.andExpect(jsonPath("$.content[0].name").value("Aurora Skies ring"));
        res.andExpect(jsonPath("$.content[1].name").value("Bike a"));
        res.andExpect(jsonPath("$.content[2].name").value("Bike b"));
    }

    @Test
    public void findByIdShouldReturnProductDtoWhenIdExists() throws Exception {
        ResultActions res = mockMvc.perform(get("/products/{id}", existingId)
                .accept(MediaType.APPLICATION_JSON));

        res.andExpect(status().isOk());
        res.andExpect(jsonPath("$.id").value(existingId));
        res.andExpect(jsonPath("$.name").value("Mistborn"));
        res.andExpect(jsonPath("$.description").value("Product description"));
    }

    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions res = mockMvc.perform(get("/products/{id}", nonExistingId)
                .accept(MediaType.APPLICATION_JSON));

        res.andExpect(status().isNotFound());
    }

    @Test
    public void updateProductShouldReturnProductDTOWhenIdExists() throws Exception {
        String json = objectMapper.writeValueAsString(productDTO);
        ResultActions res = mockMvc.perform(put("/products/{id}", + existingId)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        res.andExpect(MockMvcResultMatchers.status().isOk());
        res.andExpect(jsonPath("$.id").value(existingId));
        res.andExpect(jsonPath("$.name").value("Ripe"));
        res.andExpect(jsonPath("$.description").value(productDTO.getDescription()));
    }

    @Test
    public void updateProductShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        String json = objectMapper.writeValueAsString(productDTO);
        ResultActions res = mockMvc.perform(put("/products/{id}", + nonExistingId)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        res.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deleteProductShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
        ResultActions res = mockMvc.perform(delete("/products/{id}", + nonExistingId)
                .accept(MediaType.APPLICATION_JSON));

        res.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deleteProductShouldReturnNoContentWhenIdExists() throws Exception {
        ResultActions res = mockMvc.perform(delete("/products/{id}", + existingId)
                .accept(MediaType.APPLICATION_JSON));

        res.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
