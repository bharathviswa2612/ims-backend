package com.ims.backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ShipmentResponseDto {
    private String id;
    private String shipmentCode;
    private String trackingNumber;
    private LocalDate shipmentDate;
    private String status;
    private Boolean active;
    private String supplierId;
}
