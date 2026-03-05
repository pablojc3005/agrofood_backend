package com.example.agrofood_backend.controller;

import com.example.agrofood_backend.entity.CategoriaPlato;
import com.example.agrofood_backend.service.CategoriaPlatoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaPlatoController {

    private final CategoriaPlatoService categoriaPlatoService;

    @GetMapping
    public ResponseEntity<List<CategoriaPlato>> getAllCategorias() {
        return ResponseEntity.ok(categoriaPlatoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaPlato> getCategoriaById(@PathVariable Integer id) {
        return categoriaPlatoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoriaPlato> createCategoria(@RequestBody CategoriaPlato categoriaPlato) {
        return new ResponseEntity<>(categoriaPlatoService.save(categoriaPlato), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaPlato> updateCategoria(@PathVariable Integer id,
            @RequestBody CategoriaPlato categoriaPlato) {
        return categoriaPlatoService.findById(id)
                .map(existingCategoria -> {
                    categoriaPlato.setIdCategoria(id);
                    return ResponseEntity.ok(categoriaPlatoService.save(categoriaPlato));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Integer id) {
        return categoriaPlatoService.findById(id)
                .map(categoria -> {
                    categoriaPlatoService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
