package com.example.agrofood_backend.controller;

import com.example.agrofood_backend.dto.MenuDiarioRequestDTO;
import com.example.agrofood_backend.dto.MenuDiarioResponseDTO;
import com.example.agrofood_backend.service.MenuDiarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuDiarioController {

    private final MenuDiarioService menuDiarioService;

    @GetMapping
    public ResponseEntity<List<MenuDiarioResponseDTO>> getAllMenus() {
        return ResponseEntity.ok(menuDiarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuDiarioResponseDTO> getMenuById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(menuDiarioService.findById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<MenuDiarioResponseDTO>> getMenuByFecha(@PathVariable String fecha) {
        return ResponseEntity.ok(menuDiarioService.findByFechaMenu(LocalDate.parse(fecha)));
    }

    @PostMapping
    public ResponseEntity<MenuDiarioResponseDTO> createMenu(@RequestBody MenuDiarioRequestDTO menuDiarioDTO) {
        return new ResponseEntity<>(menuDiarioService.save(menuDiarioDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuDiarioResponseDTO> updateMenu(@PathVariable Integer id,
            @RequestBody MenuDiarioRequestDTO menuDiarioDTO) {
        try {
            return ResponseEntity.ok(menuDiarioService.update(id, menuDiarioDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Integer id) {
        try {
            menuDiarioService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
