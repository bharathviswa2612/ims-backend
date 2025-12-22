package com.ims.backend.controller;

import com.ims.backend.dto.ShipmentRequestDto;
import com.ims.backend.dto.ShipmentResponseDto;
import com.ims.backend.service.ShipmentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping
    public ResponseEntity<ShipmentResponseDto> create(
            @Valid @RequestBody ShipmentRequestDto dto) {

        log.info("Creating shipment");
        ShipmentResponseDto response = shipmentService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ShipmentResponseDto getById(@PathVariable String id) {
        return shipmentService.getById(id);
    }

    @GetMapping
    public List<ShipmentResponseDto> getAll() {
        return shipmentService.getAll();
    }

    @PutMapping("/{id}")
    public ShipmentResponseDto update(
            @PathVariable String id,
            @Valid @RequestBody ShipmentRequestDto dto) {

        log.info("Updating shipment {}", id);
        return shipmentService.update(id, dto);
    }

    @GetMapping("/paged")
    public Page<ShipmentResponseDto> getAllPaginated(
            @RequestParam int page,
            @RequestParam int size) {

        log.info("Fetching shipments page={} size={}", page, size);
        return shipmentService.getAllPaginated(page, size);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> disable(@PathVariable String id) {

        shipmentService.disable(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/list")
    public ResponseEntity<List<ShipmentResponseDto>> createBulk(
            @Valid @RequestBody List<ShipmentRequestDto> dtoList) {

        log.info("Bulk creating shipments");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(shipmentService.saveAll(dtoList));
    }
}
