package com.ims.backend.controller;

import com.ims.backend.dto.SupplierRequestDto;
import com.ims.backend.dto.SupplierResponseDto;
import com.ims.backend.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public SupplierResponseDto create(@Valid @RequestBody SupplierRequestDto dto) {
        return supplierService.save(dto);
    }

    @GetMapping("/{id}")
    public SupplierResponseDto getById(@PathVariable String id) {
        return supplierService.getById(id);
    }

    @GetMapping
    public List<SupplierResponseDto> getAll() {
        return supplierService.getAll();
    }

    @DeleteMapping("/{id}")
    public void disable(@PathVariable String id) {
        supplierService.disable(id);
    }
}
