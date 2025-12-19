package com.ims.backend.controller;

import com.ims.backend.dto.ProductRequestDto;
import com.ims.backend.dto.ProductResponseDto;
import com.ims.backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ProductResponseDto create(@Valid @RequestBody ProductRequestDto dto) {
        return productService.save(dto);
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
