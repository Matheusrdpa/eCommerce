package com.estudo.eCommerce.repositories;

import com.estudo.eCommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
