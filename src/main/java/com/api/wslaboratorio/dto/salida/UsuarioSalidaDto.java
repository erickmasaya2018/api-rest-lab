package com.api.wslaboratorio.dto.salida;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioSalidaDto {

    private Long usuarioId;
    private Long laboratorioId;
    private String email;
    private String nombre;
    private String apellidoMaterno;
    private String apellidoPaterno;
    private String permiso;

}
