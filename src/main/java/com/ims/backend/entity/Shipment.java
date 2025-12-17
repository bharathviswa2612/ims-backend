package com.ims.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment {

    @Id
    private String id;   // SHIP-001

    private String shipmentCode;
    private String trackingNumber;
    private LocalDate shipmentDate;
    private String status;
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
}
