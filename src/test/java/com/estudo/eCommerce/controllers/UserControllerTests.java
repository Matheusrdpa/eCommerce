package com.estudo.eCommerce.controllers;

import com.estudo.eCommerce.dto.UserDTO;
import com.estudo.eCommerce.services.Exceptions.ResourceNotFoundException;
import com.estudo.eCommerce.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    Long existingId = 1L;
    Long nonExistingId = 999L;


    @Test
    void testGetUsers() throws Exception {

        List<UserDTO> users = Arrays.asList(
                new UserDTO(1L,"John","John@test.com","123456", LocalDate.now())
                ,new UserDTO(2L,"Jane","Jane@test.com","123456", LocalDate.now()));

        when(userService.findAll()).thenReturn(users);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].name").value("Jane"));
    }

    @Test
    void testGetUsersById() throws Exception {
        UserDTO user = new UserDTO(1L,"John","John@test.com","123456", LocalDate.now());

        when(userService.findById(existingId)).thenReturn(user);
        mockMvc.perform(get("/users/" + existingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void testGetUsersByIdShoulReturnResourceNotFoundWhenIdDoesntExist() throws Exception {
        when(userService.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get("/users/" + nonExistingId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateUser() throws Exception {
        UserDTO user = new UserDTO(1L,"John","John@test.com","123456", LocalDate.now());

        when(userService.createUser(any(UserDTO.class))).thenReturn(user);

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

        when(userService.updateUser(eq(1L), any(UserDTO.class))).thenReturn(user);

        mockMvc.perform(put("/users/" + existingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void testUpdateUserShouldReturnResourceNotFoundWhenIdDoesntExist() throws Exception {
        UserDTO user = new UserDTO(1L,"John","John@test.com","123456", LocalDate.now());
        when(userService.updateUser(eq(nonExistingId), any(UserDTO.class))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(put("/users/" + nonExistingId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(existingId);

        mockMvc.perform(delete("/users/" + existingId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteUserShouldReturnResourceNotFoundWhenIdDoesntExist() throws Exception {
        doThrow(ResourceNotFoundException.class).when(userService).deleteUser(nonExistingId);

        mockMvc.perform(delete("/users/" + nonExistingId))
                .andExpect(status().isNotFound());

    }

}
