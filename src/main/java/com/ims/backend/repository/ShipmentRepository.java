package com.ims.backend.repository;

import com.ims.backend.entity.Shipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, String> {

    Optional<Shipment> findByIdAndActiveTrue(String id);

    List<Shipment> findByActiveTrue();

    Page<Shipment> findByActiveTrue(Pageable pageable);
}
