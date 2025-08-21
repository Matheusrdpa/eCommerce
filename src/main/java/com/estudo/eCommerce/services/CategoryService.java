package com.estudo.eCommerce.services;

import com.estudo.eCommerce.dto.CategoryDTO;
import com.estudo.eCommerce.dto.ProductDTO;
import com.estudo.eCommerce.entities.Category;
import com.estudo.eCommerce.entities.Product;
import com.estudo.eCommerce.repositories.CategoryRepository;
import com.estudo.eCommerce.repositories.ProductRepository;
import com.estudo.eCommerce.services.Exceptions.DbException;
import com.estudo.eCommerce.services.Exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<CategoryDTO> categories = repository.findAll().stream().map(x -> new CategoryDTO(x)).toList();
        return categories;
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        CategoryDTO categoryDTO = new CategoryDTO(category);
        return categoryDTO;
    }

    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category = repository.save(category);
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
       try {
           Category category = repository.getReferenceById(id);

           category.setName(categoryDTO.getName());

           category = repository.save(category);

           return new CategoryDTO(category);
       }catch (EntityNotFoundException e) {
           throw new ResourceNotFoundException("Category not found");
       }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteCategory(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found");
        }try {
            repository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DbException("Entity depends on another entity and can't be removed");
        }
    }
}
