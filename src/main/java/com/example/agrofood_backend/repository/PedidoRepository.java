package com.example.agrofood_backend.repository;

import com.example.agrofood_backend.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByUsuarioIdUsuario(Integer idUsuario);

    Optional<Pedido> findByUsuarioIdUsuarioAndFechaPedido(Integer idUsuario, LocalDate fechaPedido);
}
