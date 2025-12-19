package com.ims.backend.controller;

import com.ims.backend.dto.CategoryRequestDto;
import com.ims.backend.dto.CategoryResponseDto;
import com.ims.backend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryResponseDto create(@Valid @RequestBody CategoryRequestDto dto) {
        return categoryService.save(dto);
    }

    @GetMapping("/{id}")
    public CategoryResponseDto getById(@PathVariable String id) {
        return categoryService.getById(id);
    }

    @GetMapping
    public List<CategoryResponseDto> getAll() {
        return categoryService.getAll();
    }

    @DeleteMapping("/{id}")
    public void disable(@PathVariable String id) {
        categoryService.disable(id);
    }
}
