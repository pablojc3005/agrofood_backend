package com.example.agrofood_backend.service.impl;

import com.example.agrofood_backend.dto.MenuDiarioRequestDTO;
import com.example.agrofood_backend.dto.MenuDiarioResponseDTO;
import com.example.agrofood_backend.entity.MenuDiario;
import com.example.agrofood_backend.entity.Plato;
import com.example.agrofood_backend.entity.Usuario;
import com.example.agrofood_backend.repository.MenuDiarioRepository;
import com.example.agrofood_backend.repository.PlatoRepository;
import com.example.agrofood_backend.repository.UsuarioRepository;
import com.example.agrofood_backend.service.MenuDiarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuDiarioServiceImpl implements MenuDiarioService {

    private final MenuDiarioRepository menuDiarioRepository;
    private final PlatoRepository platoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<MenuDiarioResponseDTO> findAll() {
        return menuDiarioRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MenuDiarioResponseDTO findById(Integer id) {
        MenuDiario menu = menuDiarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menú Diario no encontrado"));
        return mapToDTO(menu);
    }

    @Override
    public List<MenuDiarioResponseDTO> findByFechaMenu(LocalDate fechaMenu) {
        return menuDiarioRepository.findByFechaMenu(fechaMenu).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MenuDiarioResponseDTO save(MenuDiarioRequestDTO dto) {
        Plato plato = platoRepository.findById(dto.getIdPlato())
                .orElseThrow(() -> new RuntimeException("Plato no encontrado"));
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        MenuDiario menuDiario = MenuDiario.builder()
                .fechaMenu(dto.getFechaMenu())
                .plato(plato)
                .horaLimite(dto.getHoraLimite())
                .precioDia(dto.getPrecioDia())
                .notas(dto.getNotas())
                .creadoPor(usuario)
                .activo(dto.getActivo() != null ? dto.getActivo() : true)
                .build();

        return mapToDTO(menuDiarioRepository.save(menuDiario));
    }

    @Override
    public MenuDiarioResponseDTO update(Integer id, MenuDiarioRequestDTO dto) {
        MenuDiario menuDiario = menuDiarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menú Diario no encontrado"));

        Plato plato = platoRepository.findById(dto.getIdPlato())
                .orElseThrow(() -> new RuntimeException("Plato no encontrado"));

        menuDiario.setFechaMenu(dto.getFechaMenu());
        menuDiario.setPlato(plato);
        menuDiario.setHoraLimite(dto.getHoraLimite());
        menuDiario.setPrecioDia(dto.getPrecioDia());
        menuDiario.setNotas(dto.getNotas());
        if (dto.getActivo() != null) {
            menuDiario.setActivo(dto.getActivo());
        }

        return mapToDTO(menuDiarioRepository.save(menuDiario));
    }

    @Override
    public void deleteById(Integer id) {
        menuDiarioRepository.deleteById(id);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void updateHoraLimiteByFecha(java.time.LocalDate fechaMenu, java.time.LocalTime horaLimite) {
        menuDiarioRepository.updateHoraLimiteByFecha(fechaMenu, horaLimite);
    }

    private MenuDiarioResponseDTO mapToDTO(MenuDiario menu) {
        return MenuDiarioResponseDTO.builder()
                .idMenuDiario(menu.getIdMenuDiario())
                .fechaMenu(menu.getFechaMenu())
                .idPlato(menu.getPlato().getIdPlato())
                .nombrePlato(menu.getPlato().getNombrePlato())
                .descripcionPlato(menu.getPlato().getDescripcion())
                .imagenUrl(menu.getPlato().getImagenUrl())
                .nombreCategoria(
                        menu.getPlato().getCategoriaPlato() != null
                                ? menu.getPlato().getCategoriaPlato().getNombreCategoria()
                                : "Sin Categoría")
                .horaLimite(menu.getHoraLimite())
                .precioDia(menu.getPrecioDia())
                .notas(menu.getNotas())
                .activo(menu.getActivo())
                .build();
    }
}
