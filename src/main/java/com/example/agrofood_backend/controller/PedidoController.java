package com.example.agrofood_backend.controller;

import com.example.agrofood_backend.entity.Pedido;
import com.example.agrofood_backend.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> getAllPedidos() {
        return ResponseEntity.ok(pedidoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable Integer id) {
        return pedidoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Pedido>> getPedidosByUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(pedidoService.findByUsuarioId(idUsuario));
    }

    @GetMapping("/usuario/{idUsuario}/hoy")
    public ResponseEntity<com.example.agrofood_backend.dto.PedidoResponseDTO> getPedidoHoyByUsuario(
            @PathVariable Integer idUsuario) {
        return pedidoService.findPedidoHoyByUsuario(idUsuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Pedido> createPedido(@RequestBody Pedido pedido) {
        return new ResponseEntity<>(pedidoService.save(pedido), HttpStatus.CREATED);
    }

    @PostMapping("/completo")
    public ResponseEntity<Pedido> createPedidoCompleto(
            @RequestBody com.example.agrofood_backend.dto.PedidoRequestDTO dto) {
        return new ResponseEntity<>(pedidoService.crearPedidoCompleto(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> updatePedido(@PathVariable Integer id, @RequestBody Pedido pedido) {
        return pedidoService.findById(id)
                .map(existingPedido -> {
                    pedido.setIdPedido(id);
                    return ResponseEntity.ok(pedidoService.save(pedido));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Integer id) {
        return pedidoService.findById(id)
                .map(pedido -> {
                    pedidoService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
