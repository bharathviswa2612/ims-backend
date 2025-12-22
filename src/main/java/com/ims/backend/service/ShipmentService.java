package com.ims.backend.service;

import com.ims.backend.dto.ShipmentRequestDto;
import com.ims.backend.dto.ShipmentResponseDto;
import com.ims.backend.entity.Shipment;
import com.ims.backend.entity.Supplier;
import com.ims.backend.exception.ResourceNotFoundException;
import com.ims.backend.repository.ShipmentRepository;
import com.ims.backend.repository.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

        log.debug("Saving shipment");

        Supplier supplier = supplierRepository.findByIdAndActiveTrue(dto.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

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
        return shipmentRepository.findByIdAndActiveTrue(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found"));
    }

    public List<ShipmentResponseDto> getAll() {
        return shipmentRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Page<ShipmentResponseDto> getAllPaginated(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return shipmentRepository.findByActiveTrue(pageable)
                .map(this::mapToResponse);
    }

    public ShipmentResponseDto update(String id, ShipmentRequestDto dto) {

        Shipment shipment = shipmentRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found"));

        Supplier supplier = supplierRepository.findByIdAndActiveTrue(dto.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        shipment.setShipmentCode(dto.getShipmentCode());
        shipment.setTrackingNumber(dto.getTrackingNumber());
        shipment.setShipmentDate(dto.getShipmentDate());
        shipment.setSupplier(supplier);

        Shipment updated = shipmentRepository.save(shipment);
        log.debug("Shipment {} updated", id);

        return mapToResponse(updated);
    }

    public void disable(String id) {

        Shipment shipment = shipmentRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found"));

        shipment.setActive(false);
        shipmentRepository.save(shipment);

        log.info("Shipment {} disabled", id);
    }

    public List<ShipmentResponseDto> saveAll(List<ShipmentRequestDto> dtoList) {

        log.debug("Saving {} shipments", dtoList.size());

        List<Shipment> shipments = new ArrayList<>();

        for (ShipmentRequestDto dto : dtoList) {

            Supplier supplier = supplierRepository.findByIdAndActiveTrue(dto.getSupplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

            Shipment shipment = Shipment.builder()
                    .id(dto.getId())
                    .shipmentCode(dto.getShipmentCode())
                    .trackingNumber(dto.getTrackingNumber())
                    .shipmentDate(dto.getShipmentDate())
                    .status("CREATED")
                    .supplier(supplier)
                    .active(true)
                    .build();

            shipments.add(shipment);
        }

        return shipmentRepository.saveAll(shipments)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ShipmentResponseDto mapToResponse(Shipment shipment) {
        ShipmentResponseDto dto = new ShipmentResponseDto();
        dto.setId(shipment.getId());
        dto.setShipmentCode(shipment.getShipmentCode());
        dto.setTrackingNumber(shipment.getTrackingNumber());
        dto.setShipmentDate(shipment.getShipmentDate());
        dto.setStatus(shipment.getStatus());
        dto.setActive(shipment.getActive());
        dto.setSupplierId(shipment.getSupplier().getId());
        return dto;
    }
}
