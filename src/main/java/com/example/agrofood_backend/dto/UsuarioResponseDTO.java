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
public class UsuarioResponseDTO {

    private Integer idUsuario;
    private String username;
    private String email;
    private Boolean activo;
    private Timestamp ultimoAcceso;
    private Timestamp fechaCreacion;
    private Timestamp fechaActualizacion;

    // Datos del rol aplanados
    private Integer idRol;
    private String nombreRol;

    // Datos del trabajador aplanados (puede ser null si no tiene trabajador
    // asociado)
    private Integer idTrabajador;
    private String codigoTrabajador;
    private String nombresTrabajador;
    private String apellidosTrabajador;
    private String emailTrabajador;
}
