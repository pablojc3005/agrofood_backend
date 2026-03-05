package com.example.agrofood_backend.mapper;

import com.example.agrofood_backend.dto.UsuarioRequestDTO;
import com.example.agrofood_backend.dto.UsuarioResponseDTO;
import com.example.agrofood_backend.entity.Rol;
import com.example.agrofood_backend.entity.Trabajador;
import com.example.agrofood_backend.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponseDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioResponseDTO dto = UsuarioResponseDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .activo(usuario.getActivo())
                .ultimoAcceso(usuario.getUltimoAcceso())
                .fechaCreacion(usuario.getFechaCreacion())
                .fechaActualizacion(usuario.getFechaActualizacion())
                .build();

        // Extraer información del rol de forma segura
        if (usuario.getRol() != null) {
            dto.setIdRol(usuario.getRol().getIdRol());
            dto.setNombreRol(usuario.getRol().getNombreRol());
        }

        // Extraer información del trabajador de forma segura
        if (usuario.getTrabajador() != null) {
            dto.setIdTrabajador(usuario.getTrabajador().getIdTrabajador());
            dto.setCodigoTrabajador(usuario.getTrabajador().getCodigo());
            dto.setNombresTrabajador(usuario.getTrabajador().getNombres());
            dto.setApellidosTrabajador(usuario.getTrabajador().getApellidos());
            dto.setEmailTrabajador(usuario.getTrabajador().getEmail());
        }

        return dto;
    }

    public Usuario toEntity(UsuarioRequestDTO dto, Rol rol, Trabajador trabajador) {
        if (dto == null) {
            return null;
        }

        return Usuario.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .rol(rol)
                .trabajador(trabajador)
                .activo(dto.getActivo() != null ? dto.getActivo() : true)
                .build();
    }

    public void updateEntityFromDTO(UsuarioRequestDTO dto, Usuario usuario, Rol rol, Trabajador trabajador) {
        if (dto == null || usuario == null) {
            return;
        }

        usuario.setUsername(dto.getUsername());
        usuario.setEmail(dto.getEmail());

        // Solo actualizar password si se envía uno nuevo
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPassword(dto.getPassword());
        }

        if (rol != null) {
            usuario.setRol(rol);
        }

        // Trabajador puede ser null (desvinculado)
        usuario.setTrabajador(trabajador);

        if (dto.getActivo() != null) {
            usuario.setActivo(dto.getActivo());
        }
    }
}
