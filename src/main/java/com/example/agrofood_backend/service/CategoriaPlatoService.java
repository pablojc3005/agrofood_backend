package com.example.agrofood_backend.service;

import com.example.agrofood_backend.entity.CategoriaPlato;

import java.util.List;
import java.util.Optional;

public interface CategoriaPlatoService {
    List<CategoriaPlato> findAll();

    Optional<CategoriaPlato> findById(Integer id);

    CategoriaPlato save(CategoriaPlato categoriaPlato);

    void deleteById(Integer id);
}
