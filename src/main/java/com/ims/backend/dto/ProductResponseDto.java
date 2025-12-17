package com.ims.backend.dto;

import lombok.Data;

@Data
public class ProductResponseDto {
    private String id;
    private String name;
    private Double price;
    private Integer quantity;
    private Boolean active;
    private String categoryId;
}
