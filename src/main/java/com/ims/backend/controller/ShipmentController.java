package com.ims.backend.controller;

import com.ims.backend.dto.ShipmentRequestDto;
import com.ims.backend.dto.ShipmentResponseDto;
import com.ims.backend.service.ShipmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping
    public ShipmentResponseDto create(@RequestBody ShipmentRequestDto dto) {
        return shipmentService.save(dto);
    }

    @GetMapping("/{id}")
    public ShipmentResponseDto getById(@PathVariable String id) {
        return shipmentService.getById(id);
    }

    @GetMapping
    public List<ShipmentResponseDto> getAll() {
        return shipmentService.getAll();
    }

    @DeleteMapping("/{id}")
    public void disable(@PathVariable String id) {
        shipmentService.disable(id);
    }
}
