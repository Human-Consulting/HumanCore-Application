package com.humanconsulting.humancore_api.controller;

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
    public ResponseEntity<Usuario> cadastrarUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario usuarioCadastrado = service.cadastrar(usuario);
        return ResponseEntity.status(201).body(usuarioCadastrado);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> all = service.listar();
        return ResponseEntity.status(200).body(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {
        Usuario usuario = service.buscarPorId(id);
        return ResponseEntity.status(200).body(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }
}
