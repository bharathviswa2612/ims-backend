package com.ims.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "shipments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment {

    @Id
    @Column(nullable = false, updatable = false)
    private String id;   // SHIP-001

    @Column(nullable = false)
    private String shipmentCode;

    @Column(nullable = false)
    private String trackingNumber;

    @Column(nullable = false)
    private LocalDate shipmentDate;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;
}
