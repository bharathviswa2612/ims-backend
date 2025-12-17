package com.ims.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SupplierRequestDto {

    @NotBlank(message = "Supplier id cannot be blank")
    private String id;

    @NotBlank(message = "Supplier name cannot be blank")
    private String name;

    @NotBlank(message = "Supplier code cannot be blank")
    private String supplierCode;

    @NotBlank(message = "Phone number cannot be blank")
    private String phone;
}
