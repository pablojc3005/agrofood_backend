package com.example.agrofood_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrabajadorRequestDTO {

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 10, message = "El código no puede exceder los 10 caracteres")
    private String codigo;

    @NotNull(message = "El ID del área es obligatorio")
    private Integer idArea;

    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 150, message = "Los nombres no pueden exceder los 150 caracteres")
    private String nombres;

    @Size(max = 100, message = "Los apellidos no pueden exceder los 100 caracteres")
    private String apellidos;

    @Size(max = 1, message = "El sexo debe ser M o F")
    private String sexo;

    @Email(message = "Debe ser un correo electrónico válido")
    @Size(max = 100, message = "El email no puede exceder los 100 caracteres")
    private String email;

    @Size(max = 20, message = "El teléfono no puede exceder los 20 caracteres")
    private String telefono;

    @Builder.Default
    private Boolean activo = true;
}
