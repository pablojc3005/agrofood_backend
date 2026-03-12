package com.example.agrofood_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDetalleResponseDTO {
    private Integer idDetalle;
    private Integer idPlato;
    private String nombrePlato;
    private String categoriaPlato;
    private Integer idVisitante;
    private String nombreVisitante;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private String observaciones;
}
