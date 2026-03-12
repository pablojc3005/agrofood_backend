package com.example.agrofood_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {
    private Integer idPedido;

    // Usuario simplificado
    private Integer idUsuario;
    private String username;
    private String email;

    // Trabajador simplificado
    private Integer idTrabajador;
    private String nombreTrabajador; // nombres + apellidos
    private String area;

    // MenuDiario simplificado (el menú principal asociado al pedido)
    private Integer idMenuDiario;
    private LocalDate fechaMenu;
    private String horaLimite; // Formateada como HH:mm

    // Datos del pedido
    private LocalDate fechaPedido;
    private String estadoPedido;
    private String notasGenerales;
    private BigDecimal totalPedido;

    // Detalles optimizados
    private List<PedidoDetalleResponseDTO> detalles;
}
