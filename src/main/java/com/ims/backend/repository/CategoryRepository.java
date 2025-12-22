package com.ims.backend.repository;

import com.ims.backend.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    Optional<Category> findByIdAndActiveTrue(String id);

    List<Category> findByActiveTrue();

    Page<Category> findByActiveTrue(Pageable pageable);
}
