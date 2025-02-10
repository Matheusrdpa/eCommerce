package com.estudo.eCommerce.repositories;

import com.estudo.eCommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
