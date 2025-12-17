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
public class Supplier {

    @Id
    private String id;   // SUP-001

    private String name;
    private String supplierCode;
    private String phone;
    private Boolean active;

    @OneToMany(mappedBy = "supplier")
    private List<Shipment> shipments;
}
