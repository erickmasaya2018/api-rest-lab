package com.api.wslaboratorio.controllers;

import com.api.wslaboratorio.dto.UsuarioEntityINDto;
import com.api.wslaboratorio.dto.UsuarioEntityOUTDto;
import com.api.wslaboratorio.entities.UsuarioEntity;
import com.api.wslaboratorio.services.imp.UsuarioServiceImp;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/${api.version}/usuario")
public class UsuarioController {

    private final UsuarioServiceImp usuarioServiceImp;

    public UsuarioController(UsuarioServiceImp usuarioServiceImp) {
        this.usuarioServiceImp = usuarioServiceImp;
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioEntityOUTDto>> obtenerUsuarios(Pageable pageable) {
        return this.usuarioServiceImp.obtenerUsuarios(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntityOUTDto> obtenerUsuarioPorId(@PathVariable Long id) {
        return this.usuarioServiceImp.obtenerUsuarioPorId(id);
    }

    @PostMapping
    public ResponseEntity<UsuarioEntityOUTDto> crearUsuario(@RequestBody @Valid UsuarioEntityINDto usuarioEntityINDto) {
        return this.usuarioServiceImp.crearUsuario(usuarioEntityINDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioEntityOUTDto> editarUsuario(@PathVariable Long id, @RequestBody @Valid UsuarioEntityINDto usuarioEntityINDto) {
        return this.usuarioServiceImp.editarUsuario(id, usuarioEntityINDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioEntityOUTDto> eliminarUser(@PathVariable Long id) {
        return this.usuarioServiceImp.eliminarUsuario(id);
    }
}
