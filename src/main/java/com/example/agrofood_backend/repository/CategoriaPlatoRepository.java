package com.example.agrofood_backend.repository;

import com.example.agrofood_backend.entity.CategoriaPlato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaPlatoRepository extends JpaRepository<CategoriaPlato, Integer> {
    Optional<CategoriaPlato> findByNombreCategoria(String nombreCategoria);
}
