package com.example.agrofood_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PlatoRequestDTO {

    @NotBlank(message = "El nombre del plato no puede estar en blanco")
    private String nombrePlato;

    private String descripcion;

    @NotNull(message = "Debe asignar un precio base")
    private BigDecimal precioBase;

    private String imagenUrl;

    @NotNull(message = "El ID de la categoría es obligatorio")
    private Integer idCategoria;

    private Boolean activo;
}
