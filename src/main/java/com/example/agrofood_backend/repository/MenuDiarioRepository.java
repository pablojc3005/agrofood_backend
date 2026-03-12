package com.example.agrofood_backend.repository;

import com.example.agrofood_backend.entity.MenuDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuDiarioRepository extends JpaRepository<MenuDiario, Integer> {
    List<MenuDiario> findByFechaMenu(LocalDate fechaMenu);

    Optional<MenuDiario> findByFechaMenuAndPlatoIdPlato(LocalDate fechaMenu, Integer idPlato);
    
    @org.springframework.data.jpa.repository.Modifying
    @org.springframework.data.jpa.repository.Query("UPDATE MenuDiario m SET m.horaLimite = :horaLimite WHERE m.fechaMenu = :fechaMenu")
    void updateHoraLimiteByFecha(
            @org.springframework.data.repository.query.Param("fechaMenu") java.time.LocalDate fechaMenu, 
            @org.springframework.data.repository.query.Param("horaLimite") java.time.LocalTime horaLimite);
}
