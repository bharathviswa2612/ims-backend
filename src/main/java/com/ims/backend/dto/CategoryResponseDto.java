package com.ims.backend.dto;

import lombok.Data;

@Data
public class CategoryResponseDto {
    private String id;
    private String name;
    private String description;
    private Boolean active;
}
