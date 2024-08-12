package com.api.wslaboratorio.services;

import com.api.wslaboratorio.dto.UsuarioEntityINDto;
import com.api.wslaboratorio.dto.UsuarioEntityOUTDto;
import com.api.wslaboratorio.dto.salida.UsuarioSalidaDto;
import com.api.wslaboratorio.entities.UsuarioEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    ResponseEntity<UsuarioEntityOUTDto> crearUsuario(UsuarioEntityINDto usuarioEntityINDto, HttpServletRequest request) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    ResponseEntity<UsuarioEntityOUTDto> editarUsuario(Long id, UsuarioEntityINDto usuarioEntityINDto, HttpServletRequest request) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    ResponseEntity<UsuarioEntityOUTDto> eliminarUsuario(Long id);

    ResponseEntity<UsuarioEntityOUTDto> obtenerUsuarioPorId(Long id);
    ResponseEntity<UsuarioEntityOUTDto> obtenerUsuarioPorEmail(String nombreUsuario);

   List<UsuarioSalidaDto> obtenerUsuarios();

    Optional<UsuarioEntity> obtenerUsuarioLogin(String username) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;
    UsuarioSalidaDto verificarUsuarioPorToken(HttpServletRequest request);

}
