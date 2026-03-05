package com.example.agrofood_backend.repository;

import com.example.agrofood_backend.entity.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitanteRepository extends JpaRepository<Visitante, Integer> {
    Optional<Visitante> findByNumeroDocumento(String numeroDocumento);
}
