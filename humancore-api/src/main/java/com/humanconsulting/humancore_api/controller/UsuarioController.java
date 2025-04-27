package com.humanconsulting.humancore_api.controller;

import com.humanconsulting.humancore_api.controller.dto.atualizar.usuario.UsuarioAtualizarDto;
import com.humanconsulting.humancore_api.controller.dto.request.LoginRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.UsuarioRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.usuario.UsuarioResponseDto;
import com.humanconsulting.humancore_api.controller.dto.token.UsuarioTokenDto;
import com.humanconsulting.humancore_api.controller.dto.token.UsuarioTokenMapper;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDto usuarioRequestDto) {
        final Usuario usuario = UsuarioTokenMapper.of(usuarioRequestDto);
        Usuario novoUsuario = this.service.cadastrar(usuario);

        return ResponseEntity.status(201).body(novoUsuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listar() {
        return ResponseEntity.status(200).body(service.listar());
    }

    @GetMapping("/buscarPorEmpresa/{idEmpresa}")
    public ResponseEntity<List<UsuarioResponseDto>> listar(@PathVariable Integer idEmpresa) {
        return ResponseEntity.status(200).body(service.listarPorEmpresa(idEmpresa));
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<LoginResponseDto> buscarPorId(@PathVariable Integer idUsuario) {
        return ResponseEntity.status(200).body(service.buscarPorId(idUsuario));
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> deletar(@PathVariable Integer idUsuario) {
        service.deletar(idUsuario);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponseDto> atualizar(@PathVariable Integer idUsuario, @Valid @RequestBody UsuarioAtualizarDto usuarioAtualizar) {
        return ResponseEntity.status(200).body(service.atualizarPorId(idUsuario, usuarioAtualizar));
    }

    @PostMapping("/autenticar")
    public ResponseEntity<UsuarioTokenDto> autenticar(@RequestBody LoginRequestDto usuarioAutenticar) {
        final Usuario usuario = UsuarioTokenMapper.of(usuarioAutenticar);
        UsuarioTokenDto usuarioTokenDto = this.service.autenticar(usuario);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }
}
