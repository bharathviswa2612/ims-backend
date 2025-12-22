package com.ims.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequestDto {

    @NotBlank(message = "Category id cannot be blank")
    private String id;

    @NotBlank(message = "Category name cannot be blank")
    private String name;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;
}
