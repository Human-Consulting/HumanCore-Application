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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(
            summary = "Cadastrar um novo usuário",
            description = "Esse endpoint cria um novo usuário no sistema.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDto usuarioRequestDto) {
        final Usuario usuario = UsuarioTokenMapper.of(usuarioRequestDto);
        Usuario novoUsuario = this.service.cadastrar(usuario);

        return ResponseEntity.status(201).body(novoUsuario);
    }

    @Operation(
            summary = "Listar todos os usuários",
            description = "Esse endpoint retorna todos os usuários cadastrados no sistema.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listar() {
        return ResponseEntity.status(200).body(service.listar());
    }

    @Operation(
            summary = "Listar usuários por empresa",
            description = "Esse endpoint retorna todos os usuários associados a uma empresa específica.",
            parameters = @Parameter(name = "idEmpresa", description = "ID da empresa para buscar os usuários."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @GetMapping("/buscarPorEmpresa/{idEmpresa}")
    public ResponseEntity<List<UsuarioResponseDto>> listar(@PathVariable Integer idEmpresa) {
        return ResponseEntity.status(200).body(service.listarPorEmpresa(idEmpresa));
    }

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Esse endpoint retorna um usuário específico pelo seu ID.",
            parameters = @Parameter(name = "idUsuario", description = "ID do usuário a ser buscado."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @GetMapping("/{idUsuario}")
    public ResponseEntity<LoginResponseDto> buscarPorId(@PathVariable Integer idUsuario) {
        return ResponseEntity.status(200).body(service.buscarPorId(idUsuario));
    }

    @Operation(
            summary = "Deletar um usuário",
            description = "Esse endpoint deleta um usuário baseado no seu ID.",
            parameters = @Parameter(name = "idUsuario", description = "ID do usuário a ser deletado."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> deletar(@PathVariable Integer idUsuario) {
        service.deletar(idUsuario);
        return ResponseEntity.status(204).build();
    }

    @Operation(
            summary = "Atualizar dados de um usuário",
            description = "Esse endpoint atualiza os dados de um usuário existente.",
            parameters = @Parameter(name = "idUsuario", description = "ID do usuário a ser atualizado."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponseDto> atualizar(@PathVariable Integer idUsuario, @Valid @RequestBody UsuarioAtualizarDto usuarioAtualizar) {
        return ResponseEntity.status(200).body(service.atualizarPorId(idUsuario, usuarioAtualizar));
    }

    @Operation(
            summary = "Autenticar usuário",
            description = "Esse endpoint autentica um usuário e retorna um token de acesso.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @PostMapping("/autenticar")
    public ResponseEntity<UsuarioTokenDto> autenticar(@RequestBody LoginRequestDto usuarioAutenticar) {
        final Usuario usuario = UsuarioTokenMapper.of(usuarioAutenticar);
        UsuarioTokenDto usuarioTokenDto = this.service.autenticar(usuario);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }
}
