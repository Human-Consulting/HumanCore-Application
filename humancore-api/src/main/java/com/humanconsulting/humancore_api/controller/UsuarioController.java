package com.humanconsulting.humancore_api.controller;

import com.humanconsulting.humancore_api.controller.dto.response.UsuarioResponseDto;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> cadastrarUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario usuarioCadastrado = service.cadastrar(usuario);
        return ResponseEntity.status(201).body(UsuarioResponseDto.toResponse(usuarioCadastrado));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listar() {
        List<Usuario> all = service.listar();

        List<UsuarioResponseDto> usuariosResponse = all.stream()
                .map(u -> UsuarioResponseDto.toResponse(u))
                .toList();

        return ResponseEntity.status(200).body(usuariosResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarPorId(@PathVariable Integer id) {
        Usuario usuario = service.buscarPorId(id);
        return ResponseEntity.status(200).body(UsuarioResponseDto.toResponse(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizar(
            @PathVariable Integer id,

            @Valid
            @RequestBody Usuario usuario) {
        Usuario usuarioAtualizado = service.atualizarPorId(id, usuario);

        return ResponseEntity.status(200).body(UsuarioResponseDto.toResponse(usuarioAtualizado));
    }
}
