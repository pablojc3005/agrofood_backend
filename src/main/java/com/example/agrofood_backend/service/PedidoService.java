package com.example.agrofood_backend.service;

import com.example.agrofood_backend.entity.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoService {
    List<Pedido> findAll();

    Optional<Pedido> findById(Integer id);

    List<Pedido> findByUsuarioId(Integer idUsuario);

    Pedido save(Pedido pedido);

    Pedido crearPedidoCompleto(com.example.agrofood_backend.dto.PedidoRequestDTO pedidoRequest);

    void deleteById(Integer id);
}
