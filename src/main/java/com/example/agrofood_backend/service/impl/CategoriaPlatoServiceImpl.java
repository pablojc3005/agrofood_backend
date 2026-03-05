package com.example.agrofood_backend.service.impl;

import com.example.agrofood_backend.entity.CategoriaPlato;
import com.example.agrofood_backend.repository.CategoriaPlatoRepository;
import com.example.agrofood_backend.service.CategoriaPlatoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaPlatoServiceImpl implements CategoriaPlatoService {

    private final CategoriaPlatoRepository categoriaPlatoRepository;

    @Override
    public List<CategoriaPlato> findAll() {
        return categoriaPlatoRepository.findAll();
    }

    @Override
    public Optional<CategoriaPlato> findById(Integer id) {
        return categoriaPlatoRepository.findById(id);
    }

    @Override
    public CategoriaPlato save(CategoriaPlato categoriaPlato) {
        return categoriaPlatoRepository.save(categoriaPlato);
    }

    @Override
    public void deleteById(Integer id) {
        categoriaPlatoRepository.deleteById(id);
    }
}
