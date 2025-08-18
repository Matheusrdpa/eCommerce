package com.estudo.eCommerce.services;

import com.estudo.eCommerce.dto.UserDTO;
import com.estudo.eCommerce.entities.User;
import com.estudo.eCommerce.repositories.UserRepository;
import com.estudo.eCommerce.services.Exceptions.DbException;
import com.estudo.eCommerce.services.Exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<UserDTO> user = userRepository.findAll().stream().map(x -> new UserDTO(x)).toList();
        return user;
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setBirthDate(userDTO.getBirthDate());
        user.setPhone(userDTO.getPhone());
        userRepository.save(user);
        return new UserDTO(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }try{
            userRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DbException("Entity depends on another entity and can't be removed");
        }

    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setBirthDate(userDTO.getBirthDate());
        user.setPhone(userDTO.getPhone());
        userRepository.save(user);
        return new UserDTO(user);
    }

}
