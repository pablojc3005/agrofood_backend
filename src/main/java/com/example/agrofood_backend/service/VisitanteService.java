package com.example.agrofood_backend.service;

import com.example.agrofood_backend.entity.Visitante;

import java.util.List;
import java.util.Optional;

public interface VisitanteService {
    List<Visitante> findAll();

    Optional<Visitante> findById(Integer id);

    Visitante save(Visitante visitante);

    void deleteById(Integer id);
}
