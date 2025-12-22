package com.ims.backend.service;

import com.ims.backend.dto.ProductRequestDto;
import com.ims.backend.dto.ProductResponseDto;
import com.ims.backend.entity.Category;
import com.ims.backend.entity.Product;
import com.ims.backend.exception.ResourceNotFoundException;
import com.ims.backend.repository.CategoryRepository;
import com.ims.backend.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponseDto save(ProductRequestDto dto) {

        log.debug("Saving product with id {}", dto.getId());

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Product product = Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .category(category)
                .active(true)
                .build();

        Product saved = productRepository.save(product);
        return mapToResponse(saved);
    }

    public ProductResponseDto getById(String id) {
        return productRepository.findByIdAndActiveTrue(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public List<ProductResponseDto> getAll() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void disable(String id) {

        Product product = productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setActive(false);
        productRepository.save(product);

        log.info("Product {} disabled", id);
    }

    public ProductResponseDto update(String id, ProductRequestDto dto) {

        Product product = productRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        product.setCategory(category);

        Product updated = productRepository.save(product);
        log.debug("Product {} updated", id);

        return mapToResponse(updated);
    }

    public Page<ProductResponseDto> getAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByActiveTrue(pageable)
                .map(this::mapToResponse);
    }

    public List<ProductResponseDto> saveAll(List<ProductRequestDto> dtoList) {

        log.info("Bulk product save started");

        List<Product> products = new ArrayList<>();

        for (ProductRequestDto dto : dtoList) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

            Product product = Product.builder()
                    .id(dto.getId())
                    .name(dto.getName())
                    .price(dto.getPrice())
                    .quantity(dto.getQuantity())
                    .category(category)
                    .active(true)
                    .build();

            products.add(product);
        }

        return productRepository.saveAll(products)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ProductResponseDto mapToResponse(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        dto.setActive(product.getActive());
        dto.setCategoryId(product.getCategory().getId());
        return dto;
    }
}
