package com.example.agrofood_backend.controller;

import com.example.agrofood_backend.dto.DashboardStatsDTO;
import com.example.agrofood_backend.dto.HistorialUsuarioDTO;
import com.example.agrofood_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final AreaRepository areaRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final PlatoRepository platoRepository;
    private final PedidoRepository pedidoRepository;
    private final ReporteRepository reporteRepository;

    @GetMapping("/admin-stats")
    public ResponseEntity<DashboardStatsDTO> getAdminStats() {
        LocalDate hoy = LocalDate.now(ZoneId.of("America/Lima"));

        DashboardStatsDTO stats = DashboardStatsDTO.builder()
                .platosRegistrados(platoRepository.count())
                .trabajadores(trabajadorRepository.count())
                .areas(areaRepository.count())
                .pedidosHoy(pedidoRepository.countByFechaPedido(hoy))
                .build();

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/user-history/{idUsuario}")
    public ResponseEntity<List<HistorialUsuarioDTO>> getUserHistory(@PathVariable Integer idUsuario) {
        LocalDate hoy = LocalDate.now(ZoneId.of("America/Lima"));
        LocalDate haceUnMes = hoy.minusMonths(1);

        // Obtenemos el historial detallado usando el repositorio de reportes
        List<HistorialUsuarioDTO> historial = reporteRepository.obtenerHistorialPorUsuario(idUsuario, haceUnMes, hoy);

        // Limitamos a los últimos 5 para el dashboard si es necesario, aunque en el
        // frontend también se puede manejar
        return ResponseEntity.ok(historial.stream().limit(5).toList());
    }
}
