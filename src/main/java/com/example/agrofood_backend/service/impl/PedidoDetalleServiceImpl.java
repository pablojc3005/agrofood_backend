package com.example.agrofood_backend.service.impl;

import com.example.agrofood_backend.entity.PedidoDetalle;
import com.example.agrofood_backend.repository.PedidoDetalleRepository;
import com.example.agrofood_backend.service.PedidoDetalleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoDetalleServiceImpl implements PedidoDetalleService {

    private final PedidoDetalleRepository pedidoDetalleRepository;

    @Override
    public List<PedidoDetalle> findAll() {
        return pedidoDetalleRepository.findAll();
    }

    @Override
    public Optional<PedidoDetalle> findById(Integer id) {
        return pedidoDetalleRepository.findById(id);
    }

    @Override
    public List<PedidoDetalle> findByPedidoId(Integer idPedido) {
        return pedidoDetalleRepository.findByPedidoIdPedido(idPedido);
    }

    @Override
    public PedidoDetalle save(PedidoDetalle pedidoDetalle) {
        return pedidoDetalleRepository.save(pedidoDetalle);
    }

    @Override
    public void deleteById(Integer id) {
        pedidoDetalleRepository.deleteById(id);
    }
}
