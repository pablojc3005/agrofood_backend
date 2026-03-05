package com.example.agrofood_backend.service;

import com.example.agrofood_backend.entity.Rol;

import java.util.List;
import java.util.Optional;

public interface RolService {
    List<Rol> findAll();

    Optional<Rol> findById(Integer id);

    Rol save(Rol rol);

    void deleteById(Integer id);
}
