package com.example.agrofood_backend.controller;

import com.example.agrofood_backend.dto.PlatoRequestDTO;
import com.example.agrofood_backend.dto.PlatoResponseDTO;
import com.example.agrofood_backend.service.PlatoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/platos")
@RequiredArgsConstructor
public class PlatoController {

    private final PlatoService platoService;

    @GetMapping
    public ResponseEntity<List<PlatoResponseDTO>> getAllPlatos() {
        return ResponseEntity.ok(platoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatoResponseDTO> getPlatoById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(platoService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<PlatoResponseDTO> createPlato(@Valid @RequestBody PlatoRequestDTO requestDTO) {
        return new ResponseEntity<>(platoService.create(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlatoResponseDTO> updatePlato(@PathVariable Integer id,
            @Valid @RequestBody PlatoRequestDTO requestDTO) {
        try {
            return ResponseEntity.ok(platoService.update(id, requestDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlato(@PathVariable Integer id) {
        try {
            platoService.deleteById(id);
            return ResponseEntity.ok().<Void>build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
