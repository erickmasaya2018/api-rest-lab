package com.api.wslaboratorio.controllers;

import com.api.wslaboratorio.dto.LoginUserDto;
import com.api.wslaboratorio.dto.UsuarioEntityINDto;
import com.api.wslaboratorio.dto.UsuarioEntityOUTDto;
import com.api.wslaboratorio.dto.response.LoginResponse;
import com.api.wslaboratorio.dto.salida.UsuarioSalidaDto;
import com.api.wslaboratorio.entities.UsuarioEntity;
import com.api.wslaboratorio.services.AuthenticationService;
import com.api.wslaboratorio.services.JwtService;
import com.api.wslaboratorio.services.imp.UsuarioServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Tag(name = "usuario", description = "Controlador para gestionar los usuarios de la aplicación.")
@RestController
@RequestMapping("/usuario")
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
                                                              ) String email,
                                                      @Parameter(
                                                              description = "Campo que contiene la contraseña del usuario.",
                                                              required = true
                                                      ) String password) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        LoginUserDto inputLogin = LoginUserDto.builder()
                .nombreUsuario(email)
                .contrasena(password)
                .build();

        UsuarioEntity authenticatedUser = authenticationService.authenticate(inputLogin);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        UsuarioSalidaDto usuarioSalidaDto = UsuarioSalidaDto.builder()
                .usuarioId(authenticatedUser.getUsuarioId())
                .laboratorioId(authenticatedUser.getEmpleadoEntity() == null ? null : authenticatedUser.getEmpleadoEntity().getLaboratorioEntity().getLaboratorioId())
                .email(authenticatedUser.getEmail())
                .nombre(authenticatedUser.getEmpleadoEntity() == null ? authenticatedUser.getPacienteEntity().getPrimerNombre() : authenticatedUser.getEmpleadoEntity().getPrimerNombre())
                .apellidoMaterno(authenticatedUser.getEmpleadoEntity() == null ? authenticatedUser.getPacienteEntity().getSegundoApellido() : authenticatedUser.getEmpleadoEntity().getSegundoApellido())
                .apellidoPaterno(authenticatedUser.getEmpleadoEntity() == null ? authenticatedUser.getPacienteEntity().getPrimerApellido() : authenticatedUser.getEmpleadoEntity().getPrimerApellido())
                .permiso(authenticatedUser.getPermiso())
                .build();

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .usuario(usuarioSalidaDto)
                .build();

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "Servicio para las obtener las entidades de usuario.")
    public ResponseEntity<List<UsuarioSalidaDto>> obtenerUsuarios() {
        return new ResponseEntity<>(this.usuarioServiceImp.obtenerUsuarios(), HttpStatus.OK);
    }

    @GetMapping(value = "/{unidadId}", produces = "application/json")
    @Operation(summary = "Servicio para obtener la entidad usuario por el id.")
    public ResponseEntity<UsuarioEntityOUTDto> obtenerUsuarioPorId(
            @Parameter
                    (
                            description = "Campo que contiene el id del usuario a buscar.",
                            required = true
                    ) @PathVariable("unidadId") Long unidadId) {
        return this.usuarioServiceImp.obtenerUsuarioPorId(unidadId);
    }

    @PostMapping(produces = "application/json")
    @Operation(summary = "Servicio para creación de usuario.")
    public ResponseEntity<UsuarioEntityOUTDto> crearUsuario(@RequestBody @Valid UsuarioEntityINDto usuarioEntityINDto, HttpServletRequest request) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String fd = "dfd";
        return this.usuarioServiceImp.crearUsuario(usuarioEntityINDto, request);
    }

    @PutMapping(value = "/{unidadId}", produces = "application/json")
    @Operation(summary = "Servicio para editar un usuario.")
    public ResponseEntity<UsuarioEntityOUTDto> editarUsuario(@Parameter
                                                                     (
                                                                             description = "Campo contiene el id para actualizar la información del usuario.",
                                                                             required = true

                                                                     ) @PathVariable("unidadId") Long unidadId, @RequestBody @Valid UsuarioEntityINDto usuarioEntityINDto,
                                                             HttpServletRequest request) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return this.usuarioServiceImp.editarUsuario(unidadId, usuarioEntityINDto, request);
    }

    @DeleteMapping(value = "/{unidadId}", produces = "application/json")
    @Operation(summary = "Servicio para eliminar la entidad usuario.")
    public ResponseEntity<UsuarioEntityOUTDto> eliminarUser(@Parameter
                                                                    (
                                                                            description = "Campo que contiene el campo id para eliminar el usuario.",
                                                                            required = true

                                                                    ) @PathVariable("unidadId") Long unidadId) {
        return this.usuarioServiceImp.eliminarUsuario(unidadId);
    }

    @GetMapping(value = "/verify", produces = "application/json")
    @Operation(summary = "Servicio para las obtener los datos del usuario con credenciales validas.")
    public UsuarioSalidaDto verificarUsuarioPorToken(HttpServletRequest request) {
        return this.usuarioServiceImp.verificarUsuarioPorToken(request);
    }


}
