package com.estudo.eCommerce.dto;

import com.estudo.eCommerce.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.imgUrl = product.getImgUrl();
    }
}
