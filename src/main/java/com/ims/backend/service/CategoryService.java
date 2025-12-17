package com.ims.backend.service;

import com.ims.backend.dto.CategoryRequestDto;
import com.ims.backend.dto.CategoryResponseDto;
import com.ims.backend.entity.Category;
import com.ims.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDto save(CategoryRequestDto dto) {
        Category category = Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .active(true)
                .build();

        return mapToResponse(categoryRepository.save(category));
    }

    public CategoryResponseDto getById(String id) {
        return categoryRepository.findById(id)
                .map(this::mapToResponse)
                .orElse(null);
    }

    public List<CategoryResponseDto> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void disable(String id) {
        categoryRepository.findById(id).ifPresent(category -> {
            category.setActive(false);
            categoryRepository.save(category);
        });
    }

    private CategoryResponseDto mapToResponse(Category category) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setActive(category.getActive());
        return dto;
    }
}
