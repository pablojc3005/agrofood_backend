package com.example.agrofood_backend.service;

import com.example.agrofood_backend.entity.PedidoDetalle;

import java.util.List;
import java.util.Optional;

public interface PedidoDetalleService {
    List<PedidoDetalle> findAll();

    Optional<PedidoDetalle> findById(Integer id);

    List<PedidoDetalle> findByPedidoId(Integer idPedido);

    PedidoDetalle save(PedidoDetalle pedidoDetalle);

    void deleteById(Integer id);
}
