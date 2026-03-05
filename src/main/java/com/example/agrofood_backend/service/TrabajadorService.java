package com.example.agrofood_backend.service;

import com.example.agrofood_backend.dto.TrabajadorRequestDTO;
import com.example.agrofood_backend.dto.TrabajadorResponseDTO;

import java.util.List;

public interface TrabajadorService {
    List<TrabajadorResponseDTO> findAll();

    TrabajadorResponseDTO findById(Integer id);

    TrabajadorResponseDTO findByCodigo(String codigo);

    TrabajadorResponseDTO create(TrabajadorRequestDTO requestDTO);

    TrabajadorResponseDTO update(Integer id, TrabajadorRequestDTO requestDTO);

    void deleteById(Integer id);
}
