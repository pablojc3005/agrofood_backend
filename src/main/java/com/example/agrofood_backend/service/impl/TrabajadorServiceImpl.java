package com.example.agrofood_backend.service.impl;

import com.example.agrofood_backend.dto.TrabajadorRequestDTO;
import com.example.agrofood_backend.dto.TrabajadorResponseDTO;
import com.example.agrofood_backend.entity.Area;
import com.example.agrofood_backend.entity.Trabajador;
import com.example.agrofood_backend.mapper.TrabajadorMapper;
import com.example.agrofood_backend.repository.AreaRepository;
import com.example.agrofood_backend.repository.TrabajadorRepository;
import com.example.agrofood_backend.service.TrabajadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrabajadorServiceImpl implements TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;
    private final AreaRepository areaRepository;
    private final TrabajadorMapper trabajadorMapper;

    @Override
    public List<TrabajadorResponseDTO> findAll() {
        return trabajadorRepository.findAll().stream()
                .map(trabajadorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TrabajadorResponseDTO findById(Integer id) {
        return trabajadorRepository.findById(id)
                .map(trabajadorMapper::toDTO)
                .orElse(null);
    }

    @Override
    public TrabajadorResponseDTO findByCodigo(String codigo) {
        return trabajadorRepository.findByCodigo(codigo)
                .map(trabajadorMapper::toDTO)
                .orElse(null);
    }

    @Override
    public TrabajadorResponseDTO create(TrabajadorRequestDTO requestDTO) {
        Area area = areaRepository.findById(requestDTO.getIdArea())
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));

        Trabajador trabajador = trabajadorMapper.toEntity(requestDTO, area);
        Trabajador savedTrabajador = trabajadorRepository.save(trabajador);

        return trabajadorMapper.toDTO(savedTrabajador);
    }

    @Override
    public TrabajadorResponseDTO update(Integer id, TrabajadorRequestDTO requestDTO) {
        Trabajador existingTrabajador = trabajadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));

        Area area = areaRepository.findById(requestDTO.getIdArea())
                .orElseThrow(() -> new RuntimeException("Área no encontrada"));

        trabajadorMapper.updateEntityFromDTO(requestDTO, existingTrabajador, area);
        Trabajador updatedTrabajador = trabajadorRepository.save(existingTrabajador);

        return trabajadorMapper.toDTO(updatedTrabajador);
    }

    @Override
    public void deleteById(Integer id) {
        trabajadorRepository.deleteById(id);
    }
}
