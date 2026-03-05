package com.example.agrofood_backend.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatoResponseDTO {

    private Integer idPlato;
    private String nombrePlato;
    private String descripcion;
    private BigDecimal precioBase;
    private String imagenUrl;
    private Integer idCategoria;
    private String nombreCategoria;
    private Boolean activo;

}
