package com.ims.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SupplierRequestDto {

    @NotBlank(message = "Supplier id cannot be blank")
    private String id;

    @NotBlank(message = "Supplier name cannot be blank")
    private String name;

    @NotBlank(message = "Supplier code cannot be blank")
    @Size(max = 50, message = "Supplier code cannot exceed 50 characters")
    private String supplierCode;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Phone number must be a valid 10-digit number"
    )
    private String phone;
}
