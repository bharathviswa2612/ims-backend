package com.ims.backend.service;

import com.ims.backend.dto.CategoryRequestDto;
import com.ims.backend.dto.CategoryResponseDto;
import com.ims.backend.entity.Category;
import com.ims.backend.exception.ResourceNotFoundException;
import com.ims.backend.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDto save(CategoryRequestDto dto) {

        log.debug("Saving category with id {}", dto.getId());

        Category category = Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .active(true)
                .build();

        return mapToResponse(categoryRepository.save(category));
    }

    public CategoryResponseDto getById(String id) {
        return categoryRepository.findByIdAndActiveTrue(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    public Page<CategoryResponseDto> getAllPaginated(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return categoryRepository.findByActiveTrue(pageable)
                .map(this::mapToResponse);
    }

    public List<CategoryResponseDto> getAll() {
        return categoryRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<CategoryResponseDto> saveAll(List<CategoryRequestDto> dtoList) {

        log.debug("Saving {} categories", dtoList.size());

        List<Category> categories = dtoList.stream()
                .map(dto -> Category.builder()
                        .id(dto.getId())
                        .name(dto.getName())
                        .description(dto.getDescription())
                        .active(true)
                        .build())
                .toList();

        return categoryRepository.saveAll(categories)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public void disable(String id) {

        Category category = categoryRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        category.setActive(false);
        categoryRepository.save(category);

        log.info("Category {} disabled", id);
    }

    public CategoryResponseDto update(String id, CategoryRequestDto dto) {

        Category category = categoryRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());

        Category updated = categoryRepository.save(category);
        log.debug("Category {} updated", id);

        return mapToResponse(updated);
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
