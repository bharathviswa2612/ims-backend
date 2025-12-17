package com.ims.backend.service;

import com.ims.backend.dto.SupplierRequestDto;
import com.ims.backend.dto.SupplierResponseDto;
import com.ims.backend.entity.Supplier;
import com.ims.backend.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public SupplierResponseDto save(SupplierRequestDto dto) {
        Supplier supplier = Supplier.builder()
                .id(dto.getId())
                .name(dto.getName())
                .supplierCode(dto.getSupplierCode())
                .phone(dto.getPhone())
                .active(true)
                .build();

        return mapToResponse(supplierRepository.save(supplier));
    }

    public SupplierResponseDto getById(String id) {
        return supplierRepository.findById(id)
                .map(this::mapToResponse)
                .orElse(null);
    }

    public List<SupplierResponseDto> getAll() {
        return supplierRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void disable(String id) {
        supplierRepository.findById(id).ifPresent(supplier -> {
            supplier.setActive(false);
            supplierRepository.save(supplier);
        });
    }

    private SupplierResponseDto mapToResponse(Supplier supplier) {
        SupplierResponseDto dto = new SupplierResponseDto();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setSupplierCode(supplier.getSupplierCode());
        dto.setPhone(supplier.getPhone());
        dto.setActive(supplier.getActive());
        return dto;
    }
}
