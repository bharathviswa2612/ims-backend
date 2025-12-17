package com.ims.backend.dto;

import lombok.Data;

@Data
public class SupplierResponseDto {
    private String id;
    private String name;
    private String supplierCode;
    private String phone;
    private Boolean active;
}
