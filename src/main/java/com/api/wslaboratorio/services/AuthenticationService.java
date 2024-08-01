package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.LoginUserDto;
import com.api.wslaboratorio.entities.UsuarioEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class AuthenticationService {

    private final IUsuarioService usuarioService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(@Lazy IUsuarioService usuarioService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }
    public UsuarioEntity authenticate(LoginUserDto input) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getNombreUsuario(),
                        input.getContrasena()
                )
        );

        return usuarioService.obtenerUsuarioLogin(input.getNombreUsuario())
                .orElseThrow();
    }


}
