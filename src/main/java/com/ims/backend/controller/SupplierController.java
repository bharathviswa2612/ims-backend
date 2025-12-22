package com.ims.backend.controller;

import com.ims.backend.dto.SupplierRequestDto;
import com.ims.backend.dto.SupplierResponseDto;
import com.ims.backend.service.SupplierService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public ResponseEntity<SupplierResponseDto> create(
            @Valid @RequestBody SupplierRequestDto dto) {

        log.info("Creating supplier");
        SupplierResponseDto response = supplierService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public SupplierResponseDto getById(@PathVariable String id) {
        return supplierService.getById(id);
    }

    @GetMapping
    public List<SupplierResponseDto> getAll() {
        return supplierService.getAll();
    }

    @PutMapping("/{id}")
    public SupplierResponseDto update(
            @PathVariable String id,
            @Valid @RequestBody SupplierRequestDto dto) {

        log.info("Updating supplier {}", id);
        return supplierService.update(id, dto);
    }

    @GetMapping("/paged")
    public Page<SupplierResponseDto> getAllPaginated(
            @RequestParam int page,
            @RequestParam int size) {

        log.info("Fetching suppliers page={} size={}", page, size);
        return supplierService.getAllPaginated(page, size);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> disable(@PathVariable String id) {

        supplierService.disable(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/list")
    public ResponseEntity<List<SupplierResponseDto>> createBulk(
            @Valid @RequestBody List<SupplierRequestDto> dtoList) {

        log.info("Bulk creating suppliers");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(supplierService.saveAll(dtoList));
    }
}
