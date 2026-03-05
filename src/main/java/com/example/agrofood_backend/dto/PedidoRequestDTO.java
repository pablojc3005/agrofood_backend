package com.example.agrofood_backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class PedidoRequestDTO {
    private Integer idUsuario;
    private Integer idTrabajador;

    private Integer idMenuEntrada; // Opcional, puede pedir solo segundo
    private Integer idMenuSegundo; // Obligatorio como base

    private String notasGenerales;
    private List<VisitaResumenDTO> visitas;
}
