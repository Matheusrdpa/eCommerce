package com.estudo.eCommerce.dto;

import com.estudo.eCommerce.entities.Category;
import com.estudo.eCommerce.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ProductDTO {
    private Long id;
    @Size(min = 2, max = 50, message = "Name needs to have 2 to 50 characters")
    @NotBlank(message = "Name is required")
    private String name;
    @Size(min = 5, message = "Description needs at least 5 characters")
    @NotBlank
    private String description;
    @Positive(message = "Price can't be lesser than 0")
    private Double price;
    private String imgUrl;

    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.imgUrl = product.getImgUrl();
    }

    public ProductDTO(Product product, Set<Category> categories) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.imgUrl = product.getImgUrl();
        categories.forEach(category -> this.categories.add(new CategoryDTO(category)));
    }
}
