package com.ims.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;   // SUP-001

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 50)
    private String supplierCode;

    @Column(nullable = false, length = 15)
    private String phone;

    @Column(nullable = false)
    private Boolean active = true;
}
