package com.example.agrofood_backend.controller;

import com.example.agrofood_backend.entity.Visitante;
import com.example.agrofood_backend.service.VisitanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitantes")
@RequiredArgsConstructor
public class VisitanteController {

    private final VisitanteService visitanteService;

    @GetMapping
    public ResponseEntity<List<Visitante>> getAllVisitantes() {
        return ResponseEntity.ok(visitanteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visitante> getVisitanteById(@PathVariable Integer id) {
        return visitanteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Visitante> createVisitante(@RequestBody Visitante visitante) {
        return new ResponseEntity<>(visitanteService.save(visitante), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Visitante> updateVisitante(@PathVariable Integer id, @RequestBody Visitante visitante) {
        return visitanteService.findById(id)
                .map(existingVisitante -> {
                    visitante.setIdVisitante(id);
                    return ResponseEntity.ok(visitanteService.save(visitante));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisitante(@PathVariable Integer id) {
        return visitanteService.findById(id)
                .map(visitante -> {
                    visitanteService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
