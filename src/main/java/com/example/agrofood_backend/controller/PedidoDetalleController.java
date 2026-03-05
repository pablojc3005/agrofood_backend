package com.example.agrofood_backend.controller;

import com.example.agrofood_backend.entity.PedidoDetalle;
import com.example.agrofood_backend.service.PedidoDetalleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedido-detalles")
@RequiredArgsConstructor
public class PedidoDetalleController {

    private final PedidoDetalleService pedidoDetalleService;

    @GetMapping
    public ResponseEntity<List<PedidoDetalle>> getAllPedidoDetalles() {
        return ResponseEntity.ok(pedidoDetalleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDetalle> getPedidoDetalleById(@PathVariable Integer id) {
        return pedidoDetalleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PedidoDetalle> createPedidoDetalle(@RequestBody PedidoDetalle pedidoDetalle) {
        return new ResponseEntity<>(pedidoDetalleService.save(pedidoDetalle), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDetalle> updatePedidoDetalle(@PathVariable Integer id,
            @RequestBody PedidoDetalle pedidoDetalle) {
        return pedidoDetalleService.findById(id)
                .map(existingPedidoDetalle -> {
                    pedidoDetalle.setIdDetalle(id);
                    return ResponseEntity.ok(pedidoDetalleService.save(pedidoDetalle));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedidoDetalle(@PathVariable Integer id) {
        return pedidoDetalleService.findById(id)
                .map(pedidoDetalle -> {
                    pedidoDetalleService.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
