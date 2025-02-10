package com.estudo.eCommerce.controllers;

import com.estudo.eCommerce.dto.CategoryDTO;
import com.estudo.eCommerce.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    @Operation(description = "Returns all existing categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "categories successfully returned"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> result = service.findAll();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    @Operation(description = "Returns a category searching by category ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category was sucessfully found"),
            @ApiResponse(responseCode = "404", description = "Category matching specific id was not found"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        CategoryDTO result = service.findById(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    @Operation(description = "Creates a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category successfully created"),
            @ApiResponse(responseCode = "422", description = "Invalid category fields"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO categoryDTO) {
        categoryDTO = service.createCategory(categoryDTO);
        return ResponseEntity.ok().body(categoryDTO);
    }

    @PutMapping
    @Operation(description = "Updates an already existing category searching by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category was successfully updated"),
            @ApiResponse(responseCode = "404", description = "Category matching specific id was not found"),
            @ApiResponse(responseCode = "422", description = "Invalid category fields"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity<CategoryDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
        service.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(categoryDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Searches a category by id and deletes it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category was sucessfully found and removed"),
            @ApiResponse(responseCode = "400", description = "Category depends on another entity"),
            @ApiResponse(responseCode = "404", description = "Category matching specific id was not found"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
