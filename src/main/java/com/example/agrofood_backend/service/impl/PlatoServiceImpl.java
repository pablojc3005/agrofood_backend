package com.example.agrofood_backend.service.impl;

import com.example.agrofood_backend.dto.PlatoRequestDTO;
import com.example.agrofood_backend.dto.PlatoResponseDTO;
import com.example.agrofood_backend.entity.CategoriaPlato;
import com.example.agrofood_backend.entity.Plato;
import com.example.agrofood_backend.repository.CategoriaPlatoRepository;
import com.example.agrofood_backend.repository.PlatoRepository;
import com.example.agrofood_backend.service.PlatoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlatoServiceImpl implements PlatoService {

    private final PlatoRepository platoRepository;
    private final CategoriaPlatoRepository categoriaPlatoRepository;

    @Override
    public List<PlatoResponseDTO> findAll() {
        return platoRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PlatoResponseDTO findById(Integer id) {
        return platoRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Plato no encontrado con ID: " + id));
    }

    @Override
    public PlatoResponseDTO create(PlatoRequestDTO requestDTO) {
        Plato plato = new Plato();
        mapToEntity(requestDTO, plato);
        Plato savedPlato = platoRepository.save(plato);
        return mapToDTO(savedPlato);
    }

    @Override
    public PlatoResponseDTO update(Integer id, PlatoRequestDTO requestDTO) {
        Plato plato = platoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plato no encontrado con ID: " + id));
        mapToEntity(requestDTO, plato);
        Plato updatedPlato = platoRepository.save(plato);
        return mapToDTO(updatedPlato);
    }

    @Override
    public void deleteById(Integer id) {
        platoRepository.deleteById(id);
    }

    private PlatoResponseDTO mapToDTO(Plato plato) {
        return PlatoResponseDTO.builder()
                .idPlato(plato.getIdPlato())
                .nombrePlato(plato.getNombrePlato())
                .descripcion(plato.getDescripcion())
                .precioBase(plato.getPrecioBase())
                .imagenUrl(plato.getImagenUrl())
                .idCategoria(plato.getCategoriaPlato() != null ? plato.getCategoriaPlato().getIdCategoria() : null)
                .nombreCategoria(
                        plato.getCategoriaPlato() != null ? plato.getCategoriaPlato().getNombreCategoria() : null)
                .activo(plato.getActivo())
                .build();
    }

    private void mapToEntity(PlatoRequestDTO dto, Plato plato) {
        plato.setNombrePlato(dto.getNombrePlato());
        plato.setDescripcion(dto.getDescripcion());
        plato.setPrecioBase(dto.getPrecioBase());
        plato.setImagenUrl(dto.getImagenUrl());
        plato.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        if (dto.getIdCategoria() != null) {
            CategoriaPlato categoria = categoriaPlatoRepository.findById(dto.getIdCategoria())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + dto.getIdCategoria()));
            plato.setCategoriaPlato(categoria);
        }
    }
}
