package com.api.wslaboratorio.dto.salida;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaSalidaDto {
    private Long empresaId;
    private String nombre;
    private String email;
    private String direccion;
    private String sitioWeb;
    private String ciudad;
    private String estado;
    private String codigoPostal;
    private String telefono;
}
