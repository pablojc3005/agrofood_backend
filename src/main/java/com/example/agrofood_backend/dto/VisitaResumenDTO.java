package com.example.agrofood_backend.dto;

import lombok.Data;

@Data
public class VisitaResumenDTO {
    private String nombreVisitante; // Opcional
    private Integer idMenuEntrada; // Opcional
    private Integer idMenuSegundo; // Obligatorio
}
