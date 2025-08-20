package com.estudo.eCommerce.controllers;

import com.estudo.eCommerce.dto.UserDTO;
import com.estudo.eCommerce.repositories.UserRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIntegration {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    Long existingId = 1L;
    Long nonExistingId = 999L;

    @Test
    void testGetUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Margit"))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetUsersById() throws Exception {
        mockMvc.perform(get("/users/" + existingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Margit"))
                .andExpect(jsonPath("$.id").value(existingId));
    }

    @Test
    void testGetUsersByIdShoulReturnResourceNotFoundWhenIdDoesntExist() throws Exception {
        mockMvc.perform(get("/users/" + nonExistingId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateUser() throws Exception {
        UserDTO user = new UserDTO(1L,"John","John@test.com","123456", LocalDate.now());

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void testUpdateUser() throws Exception {
        UserDTO user = new UserDTO(1L,"John","John@test.com","123456", LocalDate.now());

        mockMvc.perform(put("/users/" + existingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void testUpdateUserShouldReturnResourceNotFoundWhenIdDoesntExist() throws Exception {
        UserDTO user = new UserDTO(1L,"John","John@test.com","123456", LocalDate.now());

        mockMvc.perform(put("/users/" + nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/" + existingId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteUserShouldReturnResourceNotFoundWhenIdDoesntExist() throws Exception {
        mockMvc.perform(delete("/users/" + nonExistingId))
                .andExpect(status().isNotFound());

    }

}
