package com.example.agrofood_backend.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface HistorialUsuarioDTO {
    Integer getIdPedido();

    LocalDate getFecha();

    String getEntrada();

    String getSegundo();

    Integer getTotalRaciones();

    Integer getRacionesExtra();

    BigDecimal getCostoTotal();

    java.time.LocalTime getHoraLimite();
}
