package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.LoginUserDto;
import com.api.wslaboratorio.entities.UsuarioEntity;

public interface IAuthenticationService {
    UsuarioEntity authenticate(LoginUserDto input);
}
