package com.example.agrofood_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrabajadorResponseDTO {

    private Integer idTrabajador;
    private String codigo;
    private String nombres;
    private String apellidos;
    private String sexo;
    private String email;
    private String telefono;
    private Boolean activo;
    private Timestamp fechaCreacion;
    private Timestamp fechaActualizacion;

    // En lugar de enviar la entidad Area completa, enviamos solo los datos
    // relevantes del área
    private Integer idArea;
    private String cencosArea;
    private String nombreArea;
}
