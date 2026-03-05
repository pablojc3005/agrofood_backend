package com.example.agrofood_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDiarioResponseDTO {
    private Integer idMenuDiario;
    private LocalDate fechaMenu;
    private Integer idPlato;
    private String nombrePlato;
    private String descripcionPlato;
    private String imagenUrl;
    private String nombreCategoria;
    private LocalTime horaLimite;
    private BigDecimal precioDia;
    private String notas;
    private Boolean activo;
}
