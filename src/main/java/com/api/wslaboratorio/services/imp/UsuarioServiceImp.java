package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.EncriptadoDto;
import com.api.wslaboratorio.dto.UsuarioEntityINDto;
import com.api.wslaboratorio.dto.UsuarioEntityOUTDto;
import com.api.wslaboratorio.dto.salida.UsuarioSalidaDto;
import com.api.wslaboratorio.entities.*;
import com.api.wslaboratorio.repositories.IUsuarioRepository;
import com.api.wslaboratorio.services.*;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImp implements IUsuarioService {
    private final PasswordEncoder passwordEncoder;
    private final IEncryptingService encryptingService;
    private final IUsuarioRepository usuarioRepository;
    private final IGeneroService generoService;
    private final ILaboratorioService laboratorioService;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UsuarioServiceImp(PasswordEncoder passwordEncoder, IEncryptingService encryptingService, IUsuarioRepository usuarioRepository, IGeneroService generoService, ILaboratorioService laboratorioService, ModelMapper modelMapper, JwtService jwtService) {
        this.passwordEncoder = passwordEncoder;
        this.encryptingService = encryptingService;
        this.usuarioRepository = usuarioRepository;
        this.generoService = generoService;
        this.laboratorioService = laboratorioService;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<UsuarioEntityOUTDto> crearUsuario(UsuarioEntityINDto usuarioEntityINDto, HttpServletRequest request) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        EncriptadoDto encriptadoDto = encryptingService.encriptarTexto(usuarioEntityINDto.getContrasena());

        AuditoriaEntity auditoriaEntity = AuditoriaEntity.builder()
                .fechaCreacion(new Date())
                .usuarioCreacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
                .registroActivo(1)
                .build();

        EmpleadoEntity empleadoEntity = null;
        PacienteEntity pacienteEntity = null;


        if (usuarioEntityINDto.getEmpleadoEntity() != null) {

            GeneroEntity generoEncontrado = getGeneroEntity(usuarioEntityINDto.getEmpleadoEntity().getGeneroId());
            LaboratorioEntity laboratorioEncontrado = getLaboratorioEntity(usuarioEntityINDto.getEmpleadoEntity().getLaboratorioId());

            empleadoEntity = EmpleadoEntity.builder()
                    .dni(usuarioEntityINDto.getEmpleadoEntity().getDni())
                    .fechaNacimiento(usuarioEntityINDto.getEmpleadoEntity().getFechaNacimiento())
                    .primerNombre(usuarioEntityINDto.getEmpleadoEntity().getPrimerNombre())
                    .segundoNombre(usuarioEntityINDto.getEmpleadoEntity().getSegundoNombre())
                    .tercerNombre(usuarioEntityINDto.getEmpleadoEntity().getTercerNombre())
                    .primerApellido(usuarioEntityINDto.getEmpleadoEntity().getPrimerApellido())
                    .segundoApellido(usuarioEntityINDto.getEmpleadoEntity().getSegundoApellido())
                    .direccion(usuarioEntityINDto.getEmpleadoEntity().getDireccion())
                    .ciudad(usuarioEntityINDto.getEmpleadoEntity().getCiudad())
                    .estadoprovincia(usuarioEntityINDto.getEmpleadoEntity().getEstadoprovincia())
                    .telefono(usuarioEntityINDto.getEmpleadoEntity().getTelefono())
                    .auditoriaEntity(auditoriaEntity)
                    .generoEntity(generoEncontrado)
                    .laboratorioEntity(laboratorioEncontrado)
                    .build();
        }

        if (usuarioEntityINDto.getPacienteEntity() != null) {

            GeneroEntity generoEncontrado = getGeneroEntity(usuarioEntityINDto.getPacienteEntity().getGeneroId());
            pacienteEntity = PacienteEntity.builder()
                    .dni(usuarioEntityINDto.getEmpleadoEntity().getDni())
                    .fechaNacimiento(usuarioEntityINDto.getEmpleadoEntity().getFechaNacimiento())
                    .primerNombre(usuarioEntityINDto.getEmpleadoEntity().getPrimerNombre())
                    .segundoNombre(usuarioEntityINDto.getEmpleadoEntity().getSegundoNombre())
                    .tercerNombre(usuarioEntityINDto.getEmpleadoEntity().getTercerNombre())
                    .primerApellido(usuarioEntityINDto.getEmpleadoEntity().getPrimerApellido())
                    .segundoApellido(usuarioEntityINDto.getEmpleadoEntity().getSegundoApellido())
                    .direccion(usuarioEntityINDto.getEmpleadoEntity().getDireccion())
                    .ciudad(usuarioEntityINDto.getEmpleadoEntity().getCiudad())
                    .estadoprovincia(usuarioEntityINDto.getEmpleadoEntity().getEstadoprovincia())
                    .telefono(usuarioEntityINDto.getEmpleadoEntity().getTelefono())
                    .auditoriaEntity(auditoriaEntity)
                    .generoEntity(generoEncontrado)
                    .build();
        }

        UsuarioEntity usuarioEntity = UsuarioEntity.builder()
                .email(usuarioEntityINDto.getEmail())
                .contrasena(encriptadoDto.getCadenEncriptada())
                .claveSecretaContrasena(encriptadoDto.getClaveSecreta())
                .permiso(usuarioEntityINDto.getPermiso())
                .auditoriaEntity(auditoriaEntity)
                .empleadoEntity(empleadoEntity)
                .pacienteEntity(pacienteEntity)
                .build();

        UsuarioEntity usuarioGuardado = usuarioRepository.save(usuarioEntity);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(usuarioGuardado.getUsuarioId()).toUri();

        modelMapper.map(usuarioGuardado, UsuarioEntityOUTDto.class);
        return ResponseEntity.created(ubicacion).body(modelMapper.map(usuarioGuardado, UsuarioEntityOUTDto.class));
    }

    @Override
    public ResponseEntity<UsuarioEntityOUTDto> editarUsuario(Long id, UsuarioEntityINDto usuarioEntityINDto, HttpServletRequest request) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        EncriptadoDto encriptadoDto = encryptingService.encriptarTexto(usuarioEntityINDto.getContrasena());
        EmpleadoEntity empleadoEntity = null;
        PacienteEntity pacienteEntity = null;

        Optional<UsuarioEntity> usuarioOptional = Optional.ofNullable(usuarioRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id)));

        if (!usuarioOptional.isPresent()) {

            return ResponseEntity.unprocessableEntity().build();
        }

        AuditoriaEntity auditoriaEntity = AuditoriaEntity.builder()
                .usuarioModificacion(jwtService.extractUsername(request.getHeader("Authorization").substring(7)))
                .fechaModificacion(new Date())
                .usuarioCreacion(usuarioOptional.get().getAuditoriaEntity().getUsuarioCreacion())
                .fechaCreacion(new Date())
                .build();

        if (usuarioEntityINDto.getEmpleadoEntity() != null) {

            GeneroEntity generoEncontrado = getGeneroEntity(usuarioEntityINDto.getEmpleadoEntity().getGeneroId());
            LaboratorioEntity laboratorioEncontrado = getLaboratorioEntity(usuarioEntityINDto.getEmpleadoEntity().getLaboratorioId());

            empleadoEntity = EmpleadoEntity.builder()
                    .dni(usuarioEntityINDto.getEmpleadoEntity().getDni())
                    .fechaNacimiento(usuarioEntityINDto.getEmpleadoEntity().getFechaNacimiento())
                    .primerNombre(usuarioEntityINDto.getEmpleadoEntity().getPrimerNombre())
                    .segundoNombre(usuarioEntityINDto.getEmpleadoEntity().getSegundoNombre())
                    .tercerNombre(usuarioEntityINDto.getEmpleadoEntity().getTercerNombre())
                    .primerApellido(usuarioEntityINDto.getEmpleadoEntity().getPrimerApellido())
                    .segundoApellido(usuarioEntityINDto.getEmpleadoEntity().getSegundoApellido())
                    .direccion(usuarioEntityINDto.getEmpleadoEntity().getDireccion())
                    .ciudad(usuarioEntityINDto.getEmpleadoEntity().getCiudad())
                    .estadoprovincia(usuarioEntityINDto.getEmpleadoEntity().getEstadoprovincia())
                    .telefono(usuarioEntityINDto.getEmpleadoEntity().getTelefono())
                    .auditoriaEntity(auditoriaEntity)
                    .generoEntity(generoEncontrado)
                    .laboratorioEntity(laboratorioEncontrado)
                    .build();
        }

        if (usuarioEntityINDto.getPacienteEntity() != null) {

            GeneroEntity generoEncontrado = getGeneroEntity(usuarioEntityINDto.getPacienteEntity().getGeneroId());
            pacienteEntity = PacienteEntity.builder()
                    .dni(usuarioEntityINDto.getEmpleadoEntity().getDni())
                    .fechaNacimiento(usuarioEntityINDto.getEmpleadoEntity().getFechaNacimiento())
                    .primerNombre(usuarioEntityINDto.getEmpleadoEntity().getPrimerNombre())
                    .segundoNombre(usuarioEntityINDto.getEmpleadoEntity().getSegundoNombre())
                    .tercerNombre(usuarioEntityINDto.getEmpleadoEntity().getTercerNombre())
                    .primerApellido(usuarioEntityINDto.getEmpleadoEntity().getPrimerApellido())
                    .segundoApellido(usuarioEntityINDto.getEmpleadoEntity().getSegundoApellido())
                    .direccion(usuarioEntityINDto.getEmpleadoEntity().getDireccion())
                    .ciudad(usuarioEntityINDto.getEmpleadoEntity().getCiudad())
                    .estadoprovincia(usuarioEntityINDto.getEmpleadoEntity().getEstadoprovincia())
                    .telefono(usuarioEntityINDto.getEmpleadoEntity().getTelefono())
                    .auditoriaEntity(auditoriaEntity)
                    .generoEntity(generoEncontrado)
                    .build();
        }

        UsuarioEntity usuarioEntity = UsuarioEntity.builder()
                .usuarioId(usuarioOptional.get().getUsuarioId())
                .email(usuarioEntityINDto.getEmail())
                .contrasena(encriptadoDto.getCadenEncriptada())
                .claveSecretaContrasena(encriptadoDto.getClaveSecreta())
                .permiso(usuarioEntityINDto.getPermiso())
                .auditoriaEntity(auditoriaEntity)
                .empleadoEntity(empleadoEntity)
                .pacienteEntity(pacienteEntity)
                .build();

        usuarioRepository.save(usuarioEntity);
        return ResponseEntity.ok(modelMapper.map(usuarioEntityINDto, UsuarioEntityOUTDto.class));
    }

    @Override
    public ResponseEntity<UsuarioEntityOUTDto> eliminarUsuario(Long id) {
        UsuarioEntity usuarioOptional = usuarioRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id));

        usuarioRepository.deleteUsuarioById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UsuarioEntityOUTDto> obtenerUsuarioPorId(Long id) {
        Optional<UsuarioEntity> usuarioOptional = Optional.ofNullable(usuarioRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id)));
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(modelMapper.map(usuarioOptional.get(), UsuarioEntityOUTDto.class));
    }

    @Override
    public ResponseEntity<UsuarioEntityOUTDto> obtenerUsuarioPorEmail(String email) {
        Optional<UsuarioEntity> usuarioOptional = Optional.ofNullable(usuarioRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún registro con el nombre de usuario: " + email)));
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(modelMapper.map(usuarioOptional.get(), UsuarioEntityOUTDto.class));
    }

    @Override
    public List<UsuarioSalidaDto> obtenerUsuarios() {
        List<UsuarioSalidaDto> listaUsuarios = new ArrayList<>();

        usuarioRepository.findAll().forEach(itemUsuairio -> {

            UsuarioSalidaDto usuarioSalidaDto = UsuarioSalidaDto.builder()
                    .usuarioId(itemUsuairio.getUsuarioId())
                    .email(itemUsuairio.getEmail())
                    .nombre(itemUsuairio.getEmpleadoEntity() == null ? itemUsuairio.getPacienteEntity().getPrimerNombre() : itemUsuairio.getEmpleadoEntity().getPrimerNombre())
                    .apellidoMaterno(itemUsuairio.getEmpleadoEntity() == null ? itemUsuairio.getPacienteEntity().getSegundoApellido() : itemUsuairio.getEmpleadoEntity().getSegundoApellido())
                    .apellidoPaterno(itemUsuairio.getEmpleadoEntity() == null ? itemUsuairio.getPacienteEntity().getPrimerApellido() : itemUsuairio.getEmpleadoEntity().getPrimerApellido())
                    .permiso(itemUsuairio.getPermiso())
                    .laboratorioId(itemUsuairio.getEmpleadoEntity() == null ? null : itemUsuairio.getEmpleadoEntity().getLaboratorioEntity().getLaboratorioId())
                    .build();

            listaUsuarios.add(usuarioSalidaDto);

        });


        return listaUsuarios;
    }

    @Override
    public Optional<UsuarioEntity> obtenerUsuarioLogin(String username) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Optional<UsuarioEntity> usuarioOptional = Optional.ofNullable(usuarioRepository
                .findByEmail(username)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún registro con el nombre de usuario: " + username)));
        String contrasena = encryptingService.dencriptarTexto(usuarioOptional.get().getContrasena(), usuarioOptional.get().getClaveSecretaContrasena());

        UsuarioEntity usuarioRespuesta = UsuarioEntity.builder()
                .usuarioId(usuarioOptional.get().getUsuarioId())
                .email(usuarioOptional.get().getEmail())
                .contrasena(passwordEncoder.encode(contrasena))
                .permiso(usuarioOptional.get().getPermiso())
                .empleadoEntity(usuarioOptional.get().getEmpleadoEntity())
                .pacienteEntity(usuarioOptional.get().getPacienteEntity())
                .build();

        return Optional.of(usuarioRespuesta);
    }

    @Override
    public UsuarioSalidaDto verificarUsuarioPorToken(HttpServletRequest request) {
        String nombreUsuario = jwtService.extractUsername(request.getHeader("Authorization").substring(7));
        Optional<UsuarioEntity> usuarioBaseDatos = usuarioRepository.findByEmail(nombreUsuario);

        UsuarioSalidaDto usuarioSalidaDto = UsuarioSalidaDto.builder()
                .usuarioId(usuarioBaseDatos.get().getUsuarioId())
                .email(usuarioBaseDatos.get().getEmail())
                .nombre(usuarioBaseDatos.get().getEmpleadoEntity() == null ? usuarioBaseDatos.get().getPacienteEntity().getPrimerNombre() : usuarioBaseDatos.get().getEmpleadoEntity().getPrimerNombre())
                .apellidoMaterno(usuarioBaseDatos.get().getEmpleadoEntity() == null ? usuarioBaseDatos.get().getPacienteEntity().getSegundoApellido() : usuarioBaseDatos.get().getEmpleadoEntity().getSegundoApellido())
                .apellidoPaterno(usuarioBaseDatos.get().getEmpleadoEntity() == null ? usuarioBaseDatos.get().getPacienteEntity().getPrimerApellido() : usuarioBaseDatos.get().getEmpleadoEntity().getPrimerApellido())
                .permiso(usuarioBaseDatos.get().getPermiso())
                .laboratorioId(usuarioBaseDatos.get().getEmpleadoEntity() == null ? null : usuarioBaseDatos.get().getEmpleadoEntity().getLaboratorioEntity().getLaboratorioId())
                .build();

        return usuarioSalidaDto;
    }

    private GeneroEntity getGeneroEntity(Long id) {
        GeneroEntity generoFind = generoService.obtenerGeneroPorId(id);
        return generoFind;
    }

    private LaboratorioEntity getLaboratorioEntity(Long id) {
        LaboratorioEntity laboratorioFind = laboratorioService.obtenerLaboratorioPorId(id);
        return laboratorioFind;
    }

}