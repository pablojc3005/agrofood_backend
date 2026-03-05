package com.example.agrofood_backend.mapper;

import com.example.agrofood_backend.dto.TrabajadorRequestDTO;
import com.example.agrofood_backend.dto.TrabajadorResponseDTO;
import com.example.agrofood_backend.entity.Area;
import com.example.agrofood_backend.entity.Trabajador;
import org.springframework.stereotype.Component;

@Component
public class TrabajadorMapper {

    public TrabajadorResponseDTO toDTO(Trabajador trabajador) {
        if (trabajador == null) {
            return null;
        }

        TrabajadorResponseDTO dto = TrabajadorResponseDTO.builder()
                .idTrabajador(trabajador.getIdTrabajador())
                .codigo(trabajador.getCodigo())
                .nombres(trabajador.getNombres())
                .apellidos(trabajador.getApellidos())
                .sexo(trabajador.getSexo())
                .email(trabajador.getEmail())
                .telefono(trabajador.getTelefono())
                .activo(trabajador.getActivo())
                .fechaCreacion(trabajador.getFechaCreacion())
                .fechaActualizacion(trabajador.getFechaActualizacion())
                .build();

        // Extraer informaci\u00f3n del \u00e1rea de forma segura
        if (trabajador.getArea() != null) {
            dto.setIdArea(trabajador.getArea().getIdArea());
            dto.setCencosArea(trabajador.getArea().getCencos());
            dto.setNombreArea(trabajador.getArea().getNombreArea());
        }

        return dto;
    }

    public Trabajador toEntity(TrabajadorRequestDTO dto, Area area) {
        if (dto == null) {
            return null;
        }

        return Trabajador.builder()
                .area(area)
                .codigo(dto.getCodigo())
                .nombres(dto.getNombres())
                .apellidos(dto.getApellidos())
                .sexo(dto.getSexo())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .activo(dto.getActivo() != null ? dto.getActivo() : true)
                .build();
    }

    // M\u00e9todo para actualizar una entidad existente
    public void updateEntityFromDTO(TrabajadorRequestDTO dto, Trabajador trabajador, Area area) {
        if (dto == null || trabajador == null) {
            return;
        }

        if (area != null) {
            trabajador.setArea(area);
        }

        trabajador.setCodigo(dto.getCodigo());
        trabajador.setNombres(dto.getNombres());
        trabajador.setApellidos(dto.getApellidos());
        trabajador.setSexo(dto.getSexo());
        trabajador.setEmail(dto.getEmail());
        trabajador.setTelefono(dto.getTelefono());

        if (dto.getActivo() != null) {
            trabajador.setActivo(dto.getActivo());
        }
    }
}
