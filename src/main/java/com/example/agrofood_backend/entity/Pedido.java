package com.example.agrofood_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedidos")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer idPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_trabajador")
    private Trabajador trabajador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_menu_diario", nullable = false)
    private MenuDiario menuDiario;

    @Builder.Default
    @Column(name = "fecha_pedido", nullable = false, updatable = false)
    private Timestamp fechaPedido = new Timestamp(System.currentTimeMillis());

    @Column(name = "estado_pedido", columnDefinition = "enum('pendiente','confirmado','cancelado') DEFAULT 'pendiente'")
    @Builder.Default
    private String estadoPedido = "pendiente";

    @Column(name = "notas_generales", columnDefinition = "TEXT")
    private String notasGenerales;

    @Builder.Default
    @Column(name = "total_pedido", precision = 10, scale = 2)
    private BigDecimal totalPedido = BigDecimal.ZERO;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private Timestamp fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private Timestamp fechaActualizacion;
}
