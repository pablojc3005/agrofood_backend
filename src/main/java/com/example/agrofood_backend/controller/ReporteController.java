package com.example.agrofood_backend.controller;

import com.example.agrofood_backend.dto.ReporteConsumoDTO;
import com.example.agrofood_backend.repository.ReporteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteRepository reporteRepository;

    @GetMapping("/consumo")
    public ResponseEntity<List<ReporteConsumoDTO>> getReporteConsumo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(required = false) Integer idArea,
            @RequestParam(required = false) Integer idTrabajador) {

        List<ReporteConsumoDTO> reporte = reporteRepository.obtenerReporteConsumo(fechaInicio, fechaFin, idArea,
                idTrabajador);
        return ResponseEntity.ok(reporte);
    }

    @GetMapping("/historial/{idUsuario}")
    public ResponseEntity<List<com.example.agrofood_backend.dto.HistorialUsuarioDTO>> getHistorialUsuario(
            @PathVariable Integer idUsuario,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {

        List<com.example.agrofood_backend.dto.HistorialUsuarioDTO> historial = reporteRepository
                .obtenerHistorialPorUsuario(idUsuario, fechaInicio, fechaFin);
        return ResponseEntity.ok(historial);
    }
}
