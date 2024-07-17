package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.UsuarioEntityINDto;
import com.api.wslaboratorio.dto.UsuarioEntityOUTDto;
import com.api.wslaboratorio.entities.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface IUsuarioService {
    ResponseEntity<UsuarioEntityOUTDto> crearUsuario(UsuarioEntityINDto usuarioEntityINDto);

    ResponseEntity<UsuarioEntityOUTDto> editarUsuario(Long id, UsuarioEntityINDto usuarioEntityINDto);

    ResponseEntity<UsuarioEntityOUTDto> eliminarUsuario(Long id);

    ResponseEntity<UsuarioEntityOUTDto> obtenerUsuarioPorId(Long id);
    ResponseEntity<UsuarioEntityOUTDto> obtenerUsuarioPorNombre(String nombreUsuario);

    ResponseEntity<Page<UsuarioEntityOUTDto>> obtenerUsuarios(Pageable pageable);

    Optional<UsuarioEntity> obtenerUsuarioLogin(String username) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;
}
