package com.estudo.eCommerce.services;

import com.estudo.eCommerce.dto.UserDTO;
import com.estudo.eCommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

}
