package com.example.agrofood_backend.service;

import com.example.agrofood_backend.dto.UsuarioRequestDTO;
import com.example.agrofood_backend.dto.UsuarioResponseDTO;

import java.util.List;

public interface UsuarioService {
    List<UsuarioResponseDTO> findAll();

    UsuarioResponseDTO findById(Integer id);

    UsuarioResponseDTO create(UsuarioRequestDTO requestDTO);

    UsuarioResponseDTO update(Integer id, UsuarioRequestDTO requestDTO);

    void deleteById(Integer id);

    UsuarioResponseDTO authenticate(String username, String password);
}
