package com.api.wslaboratorio.dto.response;

import com.api.wslaboratorio.dto.salida.UsuarioSalidaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    private long expiresIn;
    private UsuarioSalidaDto usuario;
}
