package com.ims.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    private String id;   // CAT-001

    private String name;
    private String description;
    private Boolean active;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
