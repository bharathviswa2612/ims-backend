package com.ims.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ShipmentRequestDto {

    @NotBlank(message = "Shipment id cannot be blank")
    private String id;

    @NotBlank(message = "Shipment code cannot be blank")
    private String shipmentCode;

    @NotBlank(message = "Tracking number cannot be blank")
    private String trackingNumber;

    @NotNull(message = "Shipment date is required")
    @PastOrPresent(message = "Shipment date cannot be in the future")
    private LocalDate shipmentDate;

    @NotBlank(message = "Supplier id is required")
    private String supplierId;
}
