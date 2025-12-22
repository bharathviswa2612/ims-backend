package com.ims.backend.repository;

import com.ims.backend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByIdAndActiveTrue(String id);

    List<Product> findByActiveTrue();

    Page<Product> findByActiveTrue(Pageable pageable);
}
