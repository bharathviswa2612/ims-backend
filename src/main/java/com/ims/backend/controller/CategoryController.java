package com.ims.backend.controller;

import com.ims.backend.dto.CategoryRequestDto;
import com.ims.backend.dto.CategoryResponseDto;
import com.ims.backend.service.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> create(
            @Valid @RequestBody CategoryRequestDto dto) {

        log.info("Creating category");
        CategoryResponseDto response = categoryService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public CategoryResponseDto getById(@PathVariable String id) {
        return categoryService.getById(id);
    }

    @GetMapping("/paged")
    public Page<CategoryResponseDto> getAllPaginated(
            @RequestParam int page,
            @RequestParam int size) {

        log.info("Fetching categories page={} size={}", page, size);
        return categoryService.getAllPaginated(page, size);
    }

    @GetMapping
    public List<CategoryResponseDto> getAll() {
        return categoryService.getAll();
    }

    @PutMapping("/{id}")
    public CategoryResponseDto update(
            @PathVariable String id,
            @Valid @RequestBody CategoryRequestDto dto) {

        log.info("Updating category {}", id);
        return categoryService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> disable(@PathVariable String id) {

        categoryService.disable(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/list")
    public ResponseEntity<List<CategoryResponseDto>> createBulk(
            @Valid @RequestBody List<CategoryRequestDto> dtoList) {

        log.info("Bulk creating categories");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.saveAll(dtoList));
    }
}
