package com.example.agrofood_backend.controller;

import com.example.agrofood_backend.dto.TrabajadorRequestDTO;
import com.example.agrofood_backend.dto.TrabajadorResponseDTO;
import com.example.agrofood_backend.service.TrabajadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trabajadores")
@RequiredArgsConstructor
public class TrabajadorController {

    private final TrabajadorService trabajadorService;

    @GetMapping
    public ResponseEntity<List<TrabajadorResponseDTO>> getAllTrabajadores() {
        return ResponseEntity.ok(trabajadorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrabajadorResponseDTO> getTrabajadorById(@PathVariable Integer id) {
        TrabajadorResponseDTO trabajador = trabajadorService.findById(id);
        if (trabajador != null) {
            return ResponseEntity.ok(trabajador);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TrabajadorResponseDTO> createTrabajador(@Valid @RequestBody TrabajadorRequestDTO requestDTO) {
        return new ResponseEntity<>(trabajadorService.create(requestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrabajadorResponseDTO> updateTrabajador(
            @PathVariable Integer id,
            @Valid @RequestBody TrabajadorRequestDTO requestDTO) {

        try {
            TrabajadorResponseDTO updatedTrabajador = trabajadorService.update(id, requestDTO);
            return ResponseEntity.ok(updatedTrabajador);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrabajador(@PathVariable Integer id) {
        if (trabajadorService.findById(id) != null) {
            trabajadorService.deleteById(id);
            return ResponseEntity.ok().<Void>build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
