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
    @Transactional
    public void deleteById(Integer id) {
        // 1. Limpiar detalles y visitantes
        List<PedidoDetalle> detalles = pedidoDetalleRepository.findByPedidoIdPedido(id);
        for (PedidoDetalle det : detalles) {
            Visitante v = det.getVisitante();
            pedidoDetalleRepository.delete(det);
            if (v != null) {
                visitanteRepository.delete(v);
            }
        }
        pedidoDetalleRepository.flush();

        // 2. Eliminar pedido
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
        java.math.BigDecimal total = java.math.BigDecimal.ZERO;
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

        // 2.5 Validar si ya existe un pedido para hoy y eliminarlo (Modificar)
        java.time.LocalDate hoy = java.time.LocalDate.now(java.time.ZoneId.of("America/Lima"));
        pedidoRepository.findByUsuarioIdUsuarioAndFechaPedido(dto.getIdUsuario(), hoy)
                .ifPresent(p -> {
                    // 1. Eliminar detalles explícitamente si no hay cascade
                    List<PedidoDetalle> detalles = pedidoDetalleRepository.findByPedidoIdPedido(p.getIdPedido());
                    for (PedidoDetalle det : detalles) {
                        Visitante v = det.getVisitante();
                        pedidoDetalleRepository.delete(det);
                        // Opcional: Eliminar visitante si es huérfano (solo si es dummy/visita)
                        if (v != null) {
                            visitanteRepository.delete(v);
                        }
                    }
                    pedidoDetalleRepository.flush();

                    // 2. Eliminar pedido
                    pedidoRepository.delete(p);
                    pedidoRepository.flush();
                });

        // 3. Crear Pedido (Cabecera)
        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .trabajador(trabajador)
                .menuDiario(menuSegundo)
                .fechaPedido(hoy) // Forzamos la fecha de hoy con la zona horaria correcta
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

    @Override
    public Optional<com.example.agrofood_backend.dto.PedidoResponseDTO> findPedidoHoyByUsuario(Integer idUsuario) {
        java.time.LocalDate hoy = java.time.LocalDate.now(java.time.ZoneId.of("America/Lima"));
        return pedidoRepository.findByUsuarioIdUsuarioAndFechaPedido(idUsuario, hoy)
                .map(this::mapToResponseDTO);
    }

    @Override
    public com.example.agrofood_backend.dto.PedidoResponseDTO mapToResponseDTO(Pedido pedido) {
        if (pedido == null)
            return null;

        com.example.agrofood_backend.dto.PedidoResponseDTO dto = com.example.agrofood_backend.dto.PedidoResponseDTO
                .builder()
                .idPedido(pedido.getIdPedido())
                .idUsuario(pedido.getUsuario().getIdUsuario())
                .username(pedido.getUsuario().getUsername())
                .email(pedido.getUsuario().getEmail())
                .fechaPedido(pedido.getFechaPedido())
                .estadoPedido(pedido.getEstadoPedido())
                .notasGenerales(pedido.getNotasGenerales())
                .totalPedido(pedido.getTotalPedido())
                .build();

        if (pedido.getTrabajador() != null) {
            dto.setIdTrabajador(pedido.getTrabajador().getIdTrabajador());
            dto.setNombreTrabajador(pedido.getTrabajador().getNombres() + " " + pedido.getTrabajador().getApellidos());
            if (pedido.getTrabajador().getArea() != null) {
                dto.setArea(pedido.getTrabajador().getArea().getNombreArea());
            }
        }

        if (pedido.getMenuDiario() != null) {
            dto.setIdMenuDiario(pedido.getMenuDiario().getIdMenuDiario());
            dto.setFechaMenu(pedido.getMenuDiario().getFechaMenu());
            if (pedido.getMenuDiario().getHoraLimite() != null) {
                dto.setHoraLimite(String.format("%02d:%02d",
                        pedido.getMenuDiario().getHoraLimite().getHour(),
                        pedido.getMenuDiario().getHoraLimite().getMinute()));
            }
        }

        if (pedido.getDetalles() != null) {
            List<com.example.agrofood_backend.dto.PedidoDetalleResponseDTO> detallesDTO = pedido.getDetalles().stream()
                    .map(det -> com.example.agrofood_backend.dto.PedidoDetalleResponseDTO.builder()
                            .idDetalle(det.getIdDetalle())
                            .idPlato(det.getPlato().getIdPlato())
                            .nombrePlato(det.getPlato().getNombrePlato())
                            .categoriaPlato(det.getPlato().getCategoriaPlato() != null
                                    ? det.getPlato().getCategoriaPlato().getNombreCategoria()
                                    : null)
                            .idVisitante(det.getVisitante() != null ? det.getVisitante().getIdVisitante() : null)
                            .nombreVisitante(det.getVisitante() != null ? det.getVisitante().getNombreCompleto() : null)
                            .cantidad(det.getCantidad())
                            .precioUnitario(det.getPrecioUnitario())
                            .subtotal(det.getSubtotal())
                            .observaciones(det.getObservaciones())
                            .build())
                    .toList();
            dto.setDetalles(detallesDTO);
        }

        return dto;
    }
}
