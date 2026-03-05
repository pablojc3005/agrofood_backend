package com.example.agrofood_backend.service;

import com.example.agrofood_backend.dto.PlatoRequestDTO;
import com.example.agrofood_backend.dto.PlatoResponseDTO;

import java.util.List;

public interface PlatoService {
    List<PlatoResponseDTO> findAll();

    PlatoResponseDTO findById(Integer id);

    PlatoResponseDTO create(PlatoRequestDTO requestDTO);

    PlatoResponseDTO update(Integer id, PlatoRequestDTO requestDTO);

    void deleteById(Integer id);
}
