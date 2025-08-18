package com.estudo.eCommerce.controllers;

import com.estudo.eCommerce.dto.UserDTO;
import com.estudo.eCommerce.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(description = "Returns a list of all existing users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users successfully returned"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> result = userService.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/{id}")
    @Operation(description = "Returns a user searching by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was sucessfully found"),
            @ApiResponse(responseCode = "404", description = "User matching specific id was not found"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO result = userService.findById(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    @Operation(description = "Creates a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully created"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        userDTO = userService.createUser(userDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(userDTO);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(description = "Searches a user by id and deletes it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User was sucessfully found and removed"),
            @ApiResponse(responseCode = "400", description = "User depends on another entity"),
            @ApiResponse(responseCode = "404", description = "User matching specific id was not found"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "Updates an already existing user searching by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was successfully updated"),
            @ApiResponse(responseCode = "404", description = "User matching specific id was not found"),
            @ApiResponse(responseCode = "500", description = "Internal error")
    })
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        userService.updateUser(id, userDTO);
        return ResponseEntity.ok(userDTO);
    }
}
