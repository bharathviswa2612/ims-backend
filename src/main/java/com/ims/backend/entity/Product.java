package com.ims.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    private String id;   // PROD-001

    private String name;
    private Double price;
    private Integer quantity;
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
