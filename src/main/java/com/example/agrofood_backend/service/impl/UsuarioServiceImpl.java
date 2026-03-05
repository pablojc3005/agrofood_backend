package com.example.agrofood_backend.service.impl;

import com.example.agrofood_backend.dto.UsuarioRequestDTO;
import com.example.agrofood_backend.dto.UsuarioResponseDTO;
import com.example.agrofood_backend.entity.Rol;
import com.example.agrofood_backend.entity.Trabajador;
import com.example.agrofood_backend.entity.Usuario;
import com.example.agrofood_backend.mapper.UsuarioMapper;
import com.example.agrofood_backend.repository.RolRepository;
import com.example.agrofood_backend.repository.TrabajadorRepository;
import com.example.agrofood_backend.repository.UsuarioRepository;
import com.example.agrofood_backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final TrabajadorRepository trabajadorRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public List<UsuarioResponseDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO findById(Integer id) {
        return usuarioRepository.findById(id)
                .map(usuarioMapper::toDTO)
                .orElse(null);
    }

    @Override
    public UsuarioResponseDTO create(UsuarioRequestDTO requestDTO) {
        // Validar unicidad de username
        if (usuarioRepository.existsByUsername(requestDTO.getUsername())) {
            throw new RuntimeException("El username ya está en uso");
        }

        // Validar unicidad de email si se proporciona
        if (requestDTO.getEmail() != null && !requestDTO.getEmail().isBlank()
                && usuarioRepository.existsByEmail(requestDTO.getEmail())) {
            throw new RuntimeException("El email ya está en uso");
        }

        Rol rol = rolRepository.findById(requestDTO.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Trabajador trabajador = null;
        if (requestDTO.getIdTrabajador() != null) {
            trabajador = trabajadorRepository.findById(requestDTO.getIdTrabajador())
                    .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
        }

        Usuario usuario = usuarioMapper.toEntity(requestDTO, rol, trabajador);
        Usuario savedUsuario = usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(savedUsuario);
    }

    @Override
    public UsuarioResponseDTO update(Integer id, UsuarioRequestDTO requestDTO) {
        Usuario existingUsuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Rol rol = rolRepository.findById(requestDTO.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Trabajador trabajador = null;
        if (requestDTO.getIdTrabajador() != null) {
            trabajador = trabajadorRepository.findById(requestDTO.getIdTrabajador())
                    .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
        }

        usuarioMapper.updateEntityFromDTO(requestDTO, existingUsuario, rol, trabajador);
        Usuario updatedUsuario = usuarioRepository.save(existingUsuario);

        return usuarioMapper.toDTO(updatedUsuario);
    }

    @Override
    public void deleteById(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public UsuarioResponseDTO authenticate(String username, String password) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas"));

        // Comparación directa de password (sin encriptación por simplicidad del curso)
        if (!usuario.getPassword().equals(password)) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            throw new RuntimeException("La cuenta está desactivada");
        }

        // Actualizar último acceso
        usuario.setUltimoAcceso(Timestamp.from(Instant.now()));
        usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(usuario);
    }
}
