package com.api.wslaboratorio.services.imp;

import com.api.wslaboratorio.dto.UsuarioEntityINDto;
import com.api.wslaboratorio.dto.UsuarioEntityOUTDto;
import com.api.wslaboratorio.entities.UsuarioEntity;
import com.api.wslaboratorio.repositories.IUsuarioRepository;
import com.api.wslaboratorio.services.IEncryptingService;
import com.api.wslaboratorio.services.IUsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import java.util.Optional;

@Service
public class UsuarioServiceImp implements IUsuarioService {
    private final PasswordEncoder passwordEncoder;
    private final IEncryptingService encryptingService;
    private final IUsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    public UsuarioServiceImp(PasswordEncoder passwordEncoder, IEncryptingService encryptingService, IUsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.passwordEncoder = passwordEncoder;
        this.encryptingService = encryptingService;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<UsuarioEntityOUTDto> crearUsuario(UsuarioEntityINDto usuarioEntityINDto) {
        UsuarioEntity usuarioEntity = UsuarioEntity.builder()
                .nombreUsuario(usuarioEntityINDto.getNombreUsuario())
                .contrasena(usuarioEntityINDto.getContrasena())
                .email(usuarioEntityINDto.getEmail())
                .empleadoEntity(usuarioEntityINDto.getEmpleadoEntity())
                .pacienteEntity(usuarioEntityINDto.getPacienteEntity())
                .build();
        UsuarioEntity usuarioGuardado = usuarioRepository.save(usuarioEntity);
        URI ubicacion = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(usuarioGuardado.getUsuarioId()).toUri();

        modelMapper.map(usuarioGuardado, UsuarioEntityOUTDto.class);
        return ResponseEntity.created(ubicacion).body(modelMapper.map(usuarioGuardado, UsuarioEntityOUTDto.class));
    }

    @Override
    public ResponseEntity<UsuarioEntityOUTDto> editarUsuario(Long id, UsuarioEntityINDto usuarioEntityINDto) {
        Optional<UsuarioEntity> usuarioOptional = Optional.ofNullable(usuarioRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id)));
        if (!usuarioOptional.isPresent()) {

            return ResponseEntity.unprocessableEntity().build();
        }
        usuarioEntityINDto.setUsuarioId(usuarioOptional.get().getUsuarioId());
        UsuarioEntity usuarioEntity = UsuarioEntity.builder()
                .usuarioId(usuarioEntityINDto.getUsuarioId())
                .nombreUsuario(usuarioEntityINDto.getNombreUsuario())
                .contrasena(usuarioEntityINDto.getContrasena())
                .email(usuarioEntityINDto.getEmail())
                .empleadoEntity(usuarioEntityINDto.getEmpleadoEntity())
                .pacienteEntity(usuarioEntityINDto.getPacienteEntity())
                .build();
        usuarioRepository.save(usuarioEntity);
        return ResponseEntity.ok(modelMapper.map(usuarioEntityINDto, UsuarioEntityOUTDto.class));
    }

    @Override
    public ResponseEntity<UsuarioEntityOUTDto> eliminarUsuario(Long id) {
        Optional<UsuarioEntity> usuarioOptional = Optional.ofNullable(usuarioRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún usuario con id: " + id)));
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        usuarioRepository.delete(usuarioOptional.get());
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
    public ResponseEntity<UsuarioEntityOUTDto> obtenerUsuarioPorNombre(String nombreUsuario) {
        Optional<UsuarioEntity> usuarioOptional = Optional.ofNullable(usuarioRepository
                .findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún registro con el nombre de usuario: " + nombreUsuario)));
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(modelMapper.map(usuarioOptional.get(), UsuarioEntityOUTDto.class));
    }

    @Override
    public ResponseEntity<Page<UsuarioEntityOUTDto>> obtenerUsuarios(Pageable pageable) {
        return ResponseEntity.ok(usuarioRepository.findAll(pageable).map((element) -> modelMapper.map(element, UsuarioEntityOUTDto.class)));
    }

    @Override
    public Optional<UsuarioEntity> obtenerUsuarioLogin(String username) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Optional<UsuarioEntity> usuarioOptional = Optional.ofNullable(usuarioRepository
                .findByNombreUsuario(username)
                .orElseThrow(() -> new RuntimeException("No se encontró a ningún registro con el nombre de usuario: " + username)));
        String contrasena = encryptingService.dencriptarTexto(usuarioOptional.get().getContrasena(), usuarioOptional.get().getClaveSecretaContrasena());

        UsuarioEntity usuarioRespuesta = UsuarioEntity.builder()
                .nombreUsuario(usuarioOptional.get().getNombreUsuario())
                .contrasena(passwordEncoder.encode(contrasena))
                .email(usuarioOptional.get().getEmail())
                .build();

        return Optional.of(usuarioRespuesta);
    }

}