package com.example.agrofood_backend.repository;

import com.example.agrofood_backend.dto.ReporteConsumoDTO;
import com.example.agrofood_backend.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Pedido, Integer> {

  @Query(value = """
      SELECT
          DATE_FORMAT(p.fecha_pedido, '%Y-%m-%d') AS fecha,
          CONCAT(t.nombres, ' ', IFNULL(t.apellidos, '')) AS empleado,
          c.nombre_categoria AS entrada,
          pl.nombre_plato AS segundo,
          1 AS raciones,
          a.nombre_area AS area,
          0 AS esVisita,
          NULL AS solicitadoPor
      FROM pedidos p
      JOIN trabajadores t ON p.id_trabajador = t.id_trabajador
      JOIN areas a ON t.id_area = a.id_area
      JOIN menus_diarios m ON p.id_menu_diario = m.id_menu_diario
      JOIN platos pl ON m.id_plato = pl.id_plato
      JOIN categorias_plato c ON pl.id_categoria = c.id_categoria
      WHERE DATE(p.fecha_pedido) BETWEEN :fechaInicio AND :fechaFin
        AND (:idArea IS NULL OR a.id_area = :idArea)
        AND (:idTrabajador IS NULL OR t.id_trabajador = :idTrabajador)

      UNION ALL

      SELECT
          DATE_FORMAT(pd.fecha_creacion, '%Y-%m-%d') AS fecha,
          v.nombre_completo AS empleado,
          c.nombre_categoria AS entrada,
          pl.nombre_plato AS segundo,
          pd.cantidad AS raciones,
          a.nombre_area AS area,
          1 AS esVisita,
          CONCAT(t.nombres, ' ', IFNULL(t.apellidos, '')) AS solicitadoPor
      FROM pedido_detalles pd
      JOIN pedidos p ON pd.id_pedido = p.id_pedido
      JOIN trabajadores t ON p.id_trabajador = t.id_trabajador
      JOIN areas a ON t.id_area = a.id_area
      JOIN visitantes v ON pd.id_visita = v.id_visitante
      JOIN platos pl ON pd.id_plato = pl.id_plato
      JOIN categorias_plato c ON pl.id_categoria = c.id_categoria
      WHERE DATE(pd.fecha_creacion) BETWEEN :fechaInicio AND :fechaFin
        AND (:idArea IS NULL OR a.id_area = :idArea)
        AND (:idTrabajador IS NULL OR t.id_trabajador = :idTrabajador)

      ORDER BY fecha DESC, empleado ASC
      """, nativeQuery = true)
  List<ReporteConsumoDTO> obtenerReporteConsumo(
      @Param("fechaInicio") LocalDate fechaInicio,
      @Param("fechaFin") LocalDate fechaFin,
      @Param("idArea") Integer idArea,
      @Param("idTrabajador") Integer idTrabajador);

  @Query(value = """
      SELECT
          DATE(p.fecha_pedido) as fecha,
          (SELECT pl_e.nombre_plato FROM pedido_detalles pd_e
           JOIN platos pl_e ON pd_e.id_plato = pl_e.id_plato
           JOIN categorias_plato cat_e ON pl_e.id_categoria = cat_e.id_categoria
           WHERE pd_e.id_pedido = p.id_pedido AND pd_e.id_visita IS NULL AND cat_e.nombre_categoria LIKE '%Entrada%' LIMIT 1) as entrada,
          (SELECT pl_s.nombre_plato FROM pedido_detalles pd_s
           JOIN platos pl_s ON pd_s.id_plato = pl_s.id_plato
           JOIN categorias_plato cat_s ON pl_s.id_categoria = cat_s.id_categoria
           WHERE pd_s.id_pedido = p.id_pedido AND pd_s.id_visita IS NULL AND cat_s.nombre_categoria LIKE '%Segundo%' LIMIT 1) as segundo,
          (SELECT COALESCE(SUM(pd_t.cantidad), 0) FROM pedido_detalles pd_t WHERE pd_t.id_pedido = p.id_pedido) as totalRaciones,
          (SELECT COALESCE(SUM(pd_v.cantidad) / 2, 0) FROM pedido_detalles pd_v WHERE pd_v.id_pedido = p.id_pedido AND pd_v.id_visita IS NOT NULL) as racionesExtra,
          p.total_pedido as costoTotal
      FROM pedidos p
      WHERE p.id_usuario = :idUsuario
        AND DATE(p.fecha_pedido) BETWEEN :fechaInicio AND :fechaFin
      ORDER BY p.fecha_pedido DESC
      """, nativeQuery = true)
  List<com.example.agrofood_backend.dto.HistorialUsuarioDTO> obtenerHistorialPorUsuario(
      @Param("idUsuario") Integer idUsuario,
      @Param("fechaInicio") LocalDate fechaInicio,
      @Param("fechaFin") LocalDate fechaFin);
}
