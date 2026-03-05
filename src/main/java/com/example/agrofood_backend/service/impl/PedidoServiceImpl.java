package com.example.agrofood_backend.service.impl;

import com.example.agrofood_backend.entity.Pedido;
import com.example.agrofood_backend.repository.PedidoRepository;
import com.example.agrofood_backend.repository.PedidoDetalleRepository;
import com.example.agrofood_backend.repository.MenuDiarioRepository;
import com.example.agrofood_backend.repository.UsuarioRepository;
import com.example.agrofood_backend.repository.TrabajadorRepository;
import com.example.agrofood_backend.repository.VisitanteRepository;
import com.example.agrofood_backend.service.PedidoService;
import com.example.agrofood_backend.dto.PedidoRequestDTO;
import com.example.agrofood_backend.dto.VisitaResumenDTO;
import com.example.agrofood_backend.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoDetalleRepository pedidoDetalleRepository;
    private final MenuDiarioRepository menuDiarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final VisitanteRepository visitanteRepository;

    @Override
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public Optional<Pedido> findById(Integer id) {
        return pedidoRepository.findById(id);
    }

    @Override
    public List<Pedido> findByUsuarioId(Integer idUsuario) {
        return pedidoRepository.findByUsuarioIdUsuario(idUsuario);
    }

    @Override
    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public void deleteById(Integer id) {
        pedidoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Pedido crearPedidoCompleto(PedidoRequestDTO dto) {
        // 1. Obtener Entidades Base
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Trabajador trabajador = null;
        if (dto.getIdTrabajador() != null) {
            trabajador = trabajadorRepository.findById(dto.getIdTrabajador())
                    .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
        }

        MenuDiario menuSegundo = menuDiarioRepository.findById(dto.getIdMenuSegundo())
                .orElseThrow(() -> new RuntimeException("Menú Segundo principal no encontrado"));

        MenuDiario menuEntrada = null;
        if (dto.getIdMenuEntrada() != null) {
            menuEntrada = menuDiarioRepository.findById(dto.getIdMenuEntrada())
                    .orElseThrow(() -> new RuntimeException("Menú Entrada no encontrado"));
        }

        // 2. Calcular Total
        BigDecimal total = BigDecimal.ZERO;
        if (menuEntrada != null)
            total = total.add(menuEntrada.getPrecioDia());
        total = total.add(menuSegundo.getPrecioDia()); // El del propio trabajador

        if (dto.getVisitas() != null) {
            for (VisitaResumenDTO v : dto.getVisitas()) {
                if (v.getIdMenuEntrada() != null) {
                    MenuDiario me = menuDiarioRepository.findById(v.getIdMenuEntrada()).orElse(null);
                    if (me != null)
                        total = total.add(me.getPrecioDia());
                }
                MenuDiario ms = menuDiarioRepository.findById(v.getIdMenuSegundo())
                        .orElseThrow(() -> new RuntimeException("Menú Segundo de visita no encontrado"));
                total = total.add(ms.getPrecioDia());
            }
        }

        // 3. Crear Pedido (Cabecera)
        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .trabajador(trabajador)
                .menuDiario(menuSegundo)
                .notasGenerales(dto.getNotasGenerales())
                .totalPedido(total)
                .estadoPedido("confirmado")
                .build();
        // Usamos saveAndFlush para asegurar que el ID se genere y esté disponible
        pedido = pedidoRepository.saveAndFlush(pedido);

        // 4. Crear Detalles Propios (idVisita = null)
        if (menuEntrada != null) {
            PedidoDetalle detEntrada = PedidoDetalle.builder()
                    .pedido(pedido)
                    .plato(menuEntrada.getPlato())
                    .visitante(null)
                    .cantidad(1)
                    .precioUnitario(menuEntrada.getPrecioDia())
                    .build();
            pedidoDetalleRepository.save(detEntrada);
        }

        PedidoDetalle detSegundo = PedidoDetalle.builder()
                .pedido(pedido)
                .plato(menuSegundo.getPlato())
                .visitante(null)
                .cantidad(1)
                .precioUnitario(menuSegundo.getPrecioDia())
                .build();
        pedidoDetalleRepository.save(detSegundo);

        // 5. Crear Detalles Visitas
        if (dto.getVisitas() != null && !dto.getVisitas().isEmpty()) {
            int contVisitas = 1;
            for (VisitaResumenDTO v : dto.getVisitas()) {
                // Crear un Visitante dummy "Visita [Nombre]" o anónimo
                Visitante visitante = Visitante.builder()
                        .nombreCompleto(v.getNombreVisitante() != null && !v.getNombreVisitante().trim().isEmpty()
                                ? v.getNombreVisitante()
                                : "Visita Anónima " + contVisitas)
                        .numeroDocumento("0000000" + System.currentTimeMillis() % 1000) // Dummy unico
                        .build();
                // Usamos saveAndFlush para que el ID del visitante esté listo para el detalle
                visitante = visitanteRepository.saveAndFlush(visitante);
                contVisitas++;

                if (v.getIdMenuEntrada() != null) {
                    MenuDiario me = menuDiarioRepository.findById(v.getIdMenuEntrada()).orElse(null);
                    if (me != null) {
                        PedidoDetalle detVEntrada = PedidoDetalle.builder()
                                .pedido(pedido)
                                .plato(me.getPlato())
                                .visitante(visitante)
                                .cantidad(1)
                                .precioUnitario(me.getPrecioDia())
                                .build();
                        pedidoDetalleRepository.save(detVEntrada);
                    }
                }

                MenuDiario ms = menuDiarioRepository.findById(v.getIdMenuSegundo()).orElse(null);
                if (ms != null) {
                    PedidoDetalle detVSegundo = PedidoDetalle.builder()
                            .pedido(pedido)
                            .plato(ms.getPlato())
                            .visitante(visitante)
                            .cantidad(1)
                            .precioUnitario(ms.getPrecioDia())
                            .build();
                    pedidoDetalleRepository.save(detVSegundo);
                }
            }
        }

        return pedido;
    }
}
