package com.example.agrofood_backend.service.impl;

import com.example.agrofood_backend.entity.Visitante;
import com.example.agrofood_backend.repository.VisitanteRepository;
import com.example.agrofood_backend.service.VisitanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitanteServiceImpl implements VisitanteService {

    private final VisitanteRepository visitanteRepository;

    @Override
    public List<Visitante> findAll() {
        return visitanteRepository.findAll();
    }

    @Override
    public Optional<Visitante> findById(Integer id) {
        return visitanteRepository.findById(id);
    }

    @Override
    public Visitante save(Visitante visitante) {
        return visitanteRepository.save(visitante);
    }

    @Override
    public void deleteById(Integer id) {
        visitanteRepository.deleteById(id);
    }
}
