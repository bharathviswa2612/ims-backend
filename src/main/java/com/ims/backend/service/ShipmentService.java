package com.ims.backend.service;

import com.ims.backend.dto.ShipmentRequestDto;
import com.ims.backend.dto.ShipmentResponseDto;
import com.ims.backend.entity.Shipment;
import com.ims.backend.entity.Supplier;
import com.ims.backend.repository.ShipmentRepository;
import com.ims.backend.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final SupplierRepository supplierRepository;

    public ShipmentService(ShipmentRepository shipmentRepository,
                           SupplierRepository supplierRepository) {
        this.shipmentRepository = shipmentRepository;
        this.supplierRepository = supplierRepository;
    }

    public ShipmentResponseDto save(ShipmentRequestDto dto) {
        Supplier supplier = supplierRepository.findById(dto.getSupplierId()).orElse(null);

        Shipment shipment = Shipment.builder()
                .id(dto.getId())
                .shipmentCode(dto.getShipmentCode())
                .trackingNumber(dto.getTrackingNumber())
                .shipmentDate(dto.getShipmentDate())
                .status("CREATED")
                .supplier(supplier)
                .active(true)
                .build();

        return mapToResponse(shipmentRepository.save(shipment));
    }

    public ShipmentResponseDto getById(String id) {
        return shipmentRepository.findById(id)
                .map(this::mapToResponse)
                .orElse(null);
    }

    public List<ShipmentResponseDto> getAll() {
        return shipmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void disable(String id) {
        shipmentRepository.findById(id).ifPresent(shipment -> {
            shipment.setActive(false);
            shipmentRepository.save(shipment);
        });
    }

    private ShipmentResponseDto mapToResponse(Shipment shipment) {
        ShipmentResponseDto dto = new ShipmentResponseDto();
        dto.setId(shipment.getId());
        dto.setShipmentCode(shipment.getShipmentCode());
        dto.setTrackingNumber(shipment.getTrackingNumber());
        dto.setShipmentDate(shipment.getShipmentDate());
        dto.setStatus(shipment.getStatus());
        dto.setActive(shipment.getActive());
        dto.setSupplierId(
                shipment.getSupplier() != null ? shipment.getSupplier().getId() : null
        );
        return dto;
    }
}
