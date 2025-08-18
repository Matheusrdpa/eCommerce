package com.estudo.eCommerce.services;

import com.estudo.eCommerce.dto.UserDTO;
import com.estudo.eCommerce.entities.User;
import com.estudo.eCommerce.repositories.UserRepository;
import com.estudo.eCommerce.services.Exceptions.DbException;
import com.estudo.eCommerce.services.Exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    User user1 = new User(1L,"John","John@test.com","123456", LocalDate.now());
    User user2 = new User(2L,"Jane","Jane@test.com","123456", LocalDate.now());
    Long existingId = 1L;
    Long nonExistingId = 99L;
    Long dependentId = 3L;

    private List<User> users = List.of(user1,user2);


    @Test
    void findAllReturnsListOfUsersDTO() {
        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<UserDTO> result = userService.findAll();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(users.size(),result.size());
        Assertions.assertEquals("John",result.get(0).getName());
        Assertions.assertEquals("Jane",result.get(1).getName());

        verify(userRepository).findAll();
    }

    @Test
    void findByIdReturnsUserDTOWhenIdExists() {
        Mockito.when(userRepository.findById(existingId)).thenReturn(Optional.of(user1));
        UserDTO result = userService.findById(existingId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals("John",result.getName());

        verify(userRepository).findById(existingId);

    }

    @Test
    void findByIdReturnsResourceNotFoundExceptionWhenIdDoesNotExist() {
        Mockito.when(userRepository.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.findById(nonExistingId));
        verify(userRepository).findById(nonExistingId);
    }

    @Test
    void saveReturnsUserDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John");
        userDTO.setEmail("john@test.com");
        userDTO.setPhone("123456");
        userDTO.setBirthDate(LocalDate.now());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        UserDTO result = userService.createUser(userDTO);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L,result.getId());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void deleteShouldCallMethodOnce(){
        when(userRepository.existsById(existingId)).thenReturn(true);
        userService.deleteUser(existingId);
        verify(userRepository).deleteById(existingId);
    }

    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        when(userRepository.existsById(nonExistingId)).thenReturn(false);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(nonExistingId));
    }

    @Test
    void deleteShouldThrowDbExceptionWhenIdisDependent() {
        when(userRepository.existsById(dependentId)).thenReturn(true);
        Mockito.doThrow(DataIntegrityViolationException.class).when(userRepository).deleteById(dependentId);
        Assertions.assertThrows(DbException.class, () -> userService.deleteUser(dependentId));

    }
}
