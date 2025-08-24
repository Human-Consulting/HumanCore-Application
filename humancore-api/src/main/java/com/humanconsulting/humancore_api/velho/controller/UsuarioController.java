package com.humanconsulting.humancore_api.velho.controller;

import com.humanconsulting.humancore_api.velho.controller.dto.atualizar.usuario.UsuarioAtualizarCoresDto;
import com.humanconsulting.humancore_api.velho.controller.dto.atualizar.usuario.UsuarioAtualizarDto;
import com.humanconsulting.humancore_api.velho.controller.dto.atualizar.usuario.UsuarioAtualizarSenhaDto;
import com.humanconsulting.humancore_api.velho.controller.dto.atualizar.usuario.UsuarioEsqueciASenhaDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.LoginRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.UsuarioEnviarCodigoRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.UsuarioRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.usuario.UsuarioResponseDto;
import com.humanconsulting.humancore_api.velho.controller.dto.token.UsuarioTokenMapper;
import com.humanconsulting.humancore_api.velho.model.Usuario;
import com.humanconsulting.humancore_api.velho.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para o cadastro")
    })
    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDto usuarioRequestDto) {
        final Usuario usuario = UsuarioTokenMapper.of(usuarioRequestDto);
        Usuario novoUsuario = this.service.cadastrar(usuario, usuarioRequestDto.getFkEmpresa());

        return ResponseEntity.status(201).body(novoUsuario);
    }

    @Operation(
            summary = "Listar todos os usuários",
            description = "Esse endpoint retorna todos os usuários cadastrados no sistema.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuários não encontrados")
    })
    @GetMapping("/buscarPorEmpresa/{idEmpresa}")
    public ResponseEntity<List<UsuarioResponseDto>> listar(@PathVariable Integer idEmpresa) {
        return ResponseEntity.status(200).body(service.listarPorEmpresa(idEmpresa));
    }

    @Operation(
            summary = "Deletar um usuário",
            description = "Esse endpoint deleta um usuário baseado no seu ID.",
            parameters = @Parameter(name = "idUsuario", description = "ID do usuário a ser deletado."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PatchMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponseDto> atualizar(@PathVariable Integer idUsuario, @Valid @RequestBody UsuarioAtualizarDto usuarioAtualizar) {
        return ResponseEntity.status(200).body(service.atualizarPorId(idUsuario, usuarioAtualizar));
    }

    @Operation(
            summary = "Autenticar usuário",
            description = "Esse endpoint autentica um usuário e retorna um token de acesso.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para autenticação"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PostMapping("/autenticar")
    public ResponseEntity<LoginResponseDto> autenticar(@RequestBody LoginRequestDto usuarioAutenticar) {
        final Usuario usuario = UsuarioTokenMapper.of(usuarioAutenticar);
        return ResponseEntity.status(200).body(this.service.autenticar(usuario));
    }

    @Operation(
            summary = "Atualizar senha de um usuário",
            description = "Esse endpoint atualiza a senha de um usuário existente.",
            parameters = @Parameter(name = "idUsuario", description = "ID do usuário a ser atualizado."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PatchMapping("/atualizarSenha/{idUsuario}")
    public ResponseEntity<UsuarioResponseDto> atualizarSenha(@PathVariable Integer idUsuario, @Valid @RequestBody UsuarioAtualizarSenhaDto usuarioAtualizarSenhaDto) {
        return ResponseEntity.status(200).body(service.atualizarSenhaPorId(idUsuario, usuarioAtualizarSenhaDto));
    }

    @Operation(
            summary = "Atualizar senha de um usuário, caso tenha esquecido a senha",
            description = "Esse endpoint atualiza a senha de um usuário existente.",
            parameters = @Parameter(name = "idUsuario", description = "ID do usuário a ser atualizado."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PatchMapping("/esqueciASenha/{idUsuario}")
    public ResponseEntity<Boolean> atualizarSenha(@PathVariable Integer idUsuario, @Valid @RequestBody UsuarioEsqueciASenhaDto usuarioAtualizarSenhaDto) {
        return ResponseEntity.status(200).body(service.esqueciASenha(idUsuario, usuarioAtualizarSenhaDto));
    }

    @Operation(
            summary = "Atualizar cores do usuário",
            description = "Esse endpoint atualiza a cor de fundo da aplicação de um usuário existente.",
            parameters = @Parameter(name = "idUsuario", description = "ID do usuário a ser atualizado."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PatchMapping("/atualizarCores/{idUsuario}")
    public ResponseEntity<Boolean> atualizarCor(@PathVariable Integer idUsuario, @Valid @RequestBody UsuarioAtualizarCoresDto usuarioAtualizarCoresDto) {
        return ResponseEntity.status(200).body(service.atualizarCoresPorId(idUsuario, usuarioAtualizarCoresDto));
    }

    @Operation(
            summary = "Buscar id do usuário por email.",
            description = "Esse endpoint retorna o id de um usuário específico pelo seu email.",
            parameters = @Parameter(name = "email", description = "Email do usuário a ser buscado."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    })
    @GetMapping("/emailExistente/{email}")
    public ResponseEntity<Integer> buscarPorEmail(@PathVariable String email) {
        System.out.println(email);
        return ResponseEntity.status(200).body(service.buscarPorEmail(email));
    }

    @Operation(
            summary = "Buscar usuário por ID.",
            description = "Esse endpoint retorna um usuário específico pelo seu ID.",
            parameters = @Parameter(name = "idUsuario", description = "ID do usuário a ser buscado."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.")
    })

    @GetMapping("/{idUsuario}")
    public ResponseEntity<LoginResponseDto> buscarPorId(@PathVariable Integer idUsuario) {
        return ResponseEntity.status(200).body(service.buscarPorId(idUsuario));
    }

    @PostMapping("/codigoEsqueciASenha")
    public ResponseEntity<Void> enviarCodigoEsqueciASenha(@RequestBody UsuarioEnviarCodigoRequestDto usuarioEnviarCodigoRequestDto) {
        service.enviarCodigo(usuarioEnviarCodigoRequestDto);
        return ResponseEntity.status(204).build();
    }
}
