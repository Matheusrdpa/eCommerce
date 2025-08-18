package com.estudo.eCommerce.services;


import com.estudo.eCommerce.dto.UserDTO;
import com.estudo.eCommerce.entities.User;
import com.estudo.eCommerce.repositories.UserRepository;
import com.estudo.eCommerce.services.Exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import(UserService.class)
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    Long existingId;
    Long nonExistingId;

    @BeforeEach
    void setup(){
         existingId = 1L;
         nonExistingId = 999L;
    }

    @Test
    public void findAllShouldReturnListOfUsers() {
        List<UserDTO> users = userService.findAll();
        Assertions.assertNotNull(users);
        assertEquals(5, users.size());
        assertEquals("Margit", users.get(0).getName());

    }

    @Test
    void findByIdShouldReturnUserWhenExists() {
        UserDTO userDTO = userService.findById(existingId);
        assertEquals("Margit", userDTO.getName());
    }

    @Test
    void findByIdShouldReturnResourceNotFoundWhenDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> userService.findById(nonExistingId));
    }

    @Test
    void saveShouldSaveUser() {
        UserDTO user = new UserDTO();
        user.setName("John");
        user.setEmail("John@test.com");
        user.setBirthDate(LocalDate.now());
        user.setPhone("123456");

        UserDTO savedUser = userService.createUser(user);

        Assertions.assertNotNull(savedUser.getId());
        Assertions.assertEquals("John", savedUser.getName());
        Assertions.assertEquals(6,userRepository.count());
    }

    @Test
    void updateShouldModifyUser() {
        UserDTO updateduser = new UserDTO();
        updateduser.setName("John");
        updateduser.setEmail("John@test.com");
        updateduser.setBirthDate(LocalDate.now());
        updateduser.setPhone("123456");

        UserDTO savedUser = userService.updateUser(existingId,updateduser);

        Assertions.assertNotNull(savedUser.getId());
        Assertions.assertEquals("John", savedUser.getName());
        Assertions.assertEquals(1L,savedUser.getId());

        User user = userRepository.findById(existingId).get();
        assertEquals("John", user.getName());
    }

    @Test
    void updateShouldThrowResourceNotFoundExceptionWhenDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(nonExistingId,new UserDTO()));
    }

    @Test
    void deleteShouldDeleteUser() {
        userService.deleteUser(existingId);
        Assertions.assertEquals(4, userRepository.count());
        Assertions.assertFalse(userRepository.existsById(existingId));
    }

    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenDoesNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(nonExistingId));
    }

}
