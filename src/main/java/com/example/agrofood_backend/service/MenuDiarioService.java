package com.example.agrofood_backend.service;

import com.example.agrofood_backend.dto.MenuDiarioRequestDTO;
import com.example.agrofood_backend.dto.MenuDiarioResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface MenuDiarioService {
    List<MenuDiarioResponseDTO> findAll();

    MenuDiarioResponseDTO findById(Integer id);

    List<MenuDiarioResponseDTO> findByFechaMenu(LocalDate fechaMenu);

    MenuDiarioResponseDTO save(MenuDiarioRequestDTO menuDiarioDTO);

    MenuDiarioResponseDTO update(Integer id, MenuDiarioRequestDTO menuDiarioDTO);
    
    void updateHoraLimiteByFecha(java.time.LocalDate fechaMenu, java.time.LocalTime horaLimite);

    void deleteById(Integer id);
}
