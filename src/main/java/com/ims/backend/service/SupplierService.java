package com.ims.backend.service;

import com.ims.backend.dto.SupplierRequestDto;
import com.ims.backend.dto.SupplierResponseDto;
import com.ims.backend.entity.Supplier;
import com.ims.backend.exception.ResourceNotFoundException;
import com.ims.backend.repository.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public SupplierResponseDto save(SupplierRequestDto dto) {

        log.debug("Saving supplier");

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
        return supplierRepository.findByIdAndActiveTrue(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
    }

    public List<SupplierResponseDto> getAll() {
        return supplierRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Page<SupplierResponseDto> getAllPaginated(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return supplierRepository.findByActiveTrue(pageable)
                .map(this::mapToResponse);
    }

    public SupplierResponseDto update(String id, SupplierRequestDto dto) {

        Supplier supplier = supplierRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        supplier.setName(dto.getName());
        supplier.setSupplierCode(dto.getSupplierCode());
        supplier.setPhone(dto.getPhone());

        Supplier updated = supplierRepository.save(supplier);
        log.debug("Supplier {} updated", id);

        return mapToResponse(updated);
    }

    public void disable(String id) {

        Supplier supplier = supplierRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        supplier.setActive(false);
        supplierRepository.save(supplier);

        log.info("Supplier {} disabled", id);
    }

    public List<SupplierResponseDto> saveAll(List<SupplierRequestDto> dtoList) {

        log.debug("Saving {} suppliers", dtoList.size());

        List<Supplier> suppliers = dtoList.stream()
                .map(dto -> Supplier.builder()
                        .id(dto.getId())
                        .name(dto.getName())
                        .supplierCode(dto.getSupplierCode())
                        .phone(dto.getPhone())
                        .active(true)
                        .build())
                .toList();

        return supplierRepository.saveAll(suppliers)
                .stream()
                .map(this::mapToResponse)
                .toList();
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
