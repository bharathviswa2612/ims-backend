package com.ims.backend.controller;

import com.ims.backend.dto.ProductRequestDto;
import com.ims.backend.dto.ProductResponseDto;
import com.ims.backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/paged")
    public Page<ProductResponseDto> getAllPaginated(
            @RequestParam int page,
            @RequestParam int size) {

        return productService.getAllPaginated(page, size);
    }

    @PostMapping
    public ProductResponseDto create(@Valid @RequestBody ProductRequestDto dto) {
        logger.info("Product created with Id : {}", dto.getId());
        return productService.save(dto);
    }

    @PostMapping("/list")
    public List<ProductResponseDto> create(@Valid @RequestBody List<@Valid ProductRequestDto> dto) {
        return productService.saveAll(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(
            @PathVariable String id,
            @RequestBody @Valid ProductRequestDto dto) {

        log.info("Updating product {}", id);
        return ResponseEntity.ok(productService.update(id, dto));
    }


    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable String id) {
        return productService.getById(id);
    }

    @GetMapping
    public List<ProductResponseDto> getAll() {
        return productService.getAll();
    }

    @DeleteMapping("/{id}")
    public void disable(@PathVariable String id) {
        productService.disable(id);
    }
}
