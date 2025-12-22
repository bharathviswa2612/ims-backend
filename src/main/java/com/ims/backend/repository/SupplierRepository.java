package com.ims.backend.repository;

import com.ims.backend.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String> {

    Optional<Supplier> findByIdAndActiveTrue(String id);

    List<Supplier> findByActiveTrue();

    Page<Supplier> findByActiveTrue(Pageable pageable);
}
