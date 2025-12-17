package com.ims.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDto {

    @NotBlank(message = "Category id cannot be blank")
    private String id;

    @NotBlank(message = "Category name cannot be blank")
    private String name;

    private String description;
}
