package com.example.agrofood_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    private long platosRegistrados;
    private long trabajadores;
    private long pedidosHoy;
    private long areas;
}
