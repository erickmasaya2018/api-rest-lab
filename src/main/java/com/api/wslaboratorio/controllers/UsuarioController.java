package com.api.wslaboratorio.controllers;

import com.api.wslaboratorio.dto.LoginUserDto;
import com.api.wslaboratorio.dto.UsuarioEntityINDto;
import com.api.wslaboratorio.dto.UsuarioEntityOUTDto;
import com.api.wslaboratorio.dto.response.LoginResponse;
import com.api.wslaboratorio.entities.UsuarioEntity;
import com.api.wslaboratorio.services.AuthenticationService;
import com.api.wslaboratorio.services.JwtService;
import com.api.wslaboratorio.services.imp.UsuarioServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Tag(name = "usuario", description = "Controlador para gestionar los usuarios de la aplicación.")
@RestController
@RequestMapping("/${api.path}/usuario")
public class UsuarioController {

    private final UsuarioServiceImp usuarioServiceImp;
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public UsuarioController(UsuarioServiceImp usuarioServiceImp, JwtService jwtService, AuthenticationService authenticationService) {
        this.usuarioServiceImp = usuarioServiceImp;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @GetMapping(value = "/login", produces = "application/json")
    @Operation(summary = "Servicio para autenticar al usuario en la aplicación.")
    public ResponseEntity<LoginResponse> authenticate(@Parameter
                                                              (
                                                                      description = "Campo que contiene el nombre del usuario.",
                                                                      required = true
                                                              ) String userName,
                                                      @Parameter(
                                                              description = "Campo que contiene la contraseña del usuario.",
                                                              required = true
                                                      ) String password) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        LoginUserDto inputLogin = LoginUserDto.builder()
                .nombreUsuario(userName)
                .contrasena(password)
                .build();

        UsuarioEntity authenticatedUser = authenticationService.authenticate(inputLogin);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Servicio para las obtener las entidades de usuario.")
    public ResponseEntity<Page<UsuarioEntityOUTDto>> obtenerUsuarios(Pageable pageable) {
        return this.usuarioServiceImp.obtenerUsuarios(pageable);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Servicio para obtener la entidad usuario por el id.")
    public ResponseEntity<UsuarioEntityOUTDto> obtenerUsuarioPorId(
            @Parameter
                    (
                            description = "Campo que contiene el id del usuario a buscar.",
                            required = true
                    ) Long id) {
        return this.usuarioServiceImp.obtenerUsuarioPorId(id);
    }

    @PostMapping(produces = "application/json")
    @Operation(summary = "Servicio para creación de usuario.")
    public ResponseEntity<UsuarioEntityOUTDto> crearUsuario(@RequestBody @Valid UsuarioEntityINDto usuarioEntityINDto, HttpServletRequest request) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return this.usuarioServiceImp.crearUsuario(usuarioEntityINDto, request);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Servicio para editar un usuario.")
    public ResponseEntity<UsuarioEntityOUTDto> editarUsuario(@Parameter
                                                                     (
                                                                             description = "Campo contiene el id para actualizar la información del usuario.",
                                                                             required = true

                                                                     ) Long id, @RequestBody @Valid UsuarioEntityINDto usuarioEntityINDto,
                                                             HttpServletRequest request) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return this.usuarioServiceImp.editarUsuario(id, usuarioEntityINDto, request);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @Operation(summary = "Servicio para eliminar la entidad usuario.")
    public ResponseEntity<UsuarioEntityOUTDto> eliminarUser(@Parameter
                                                                    (
                                                                            description = "Campo que contiene el campo id para eliminar el usuario.",
                                                                            required = true

                                                                    ) Long id) {
        return this.usuarioServiceImp.eliminarUsuario(id);
    }

}
