package com.humanconsulting.humancore_api.web.controllers;

import com.humanconsulting.humancore_api.application.usecases.usuario.*;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.web.dtos.atualizar.usuario.UsuarioAtualizarCoresDto;
import com.humanconsulting.humancore_api.web.dtos.atualizar.usuario.UsuarioAtualizarDto;
import com.humanconsulting.humancore_api.web.dtos.atualizar.usuario.UsuarioAtualizarSenhaDto;
import com.humanconsulting.humancore_api.web.dtos.request.LoginRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioEnviarCodigoRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioResponseDto;
import com.humanconsulting.humancore_api.web.dtos.token.UsuarioTokenMapper;
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

    @Autowired private CadastrarUsuarioUseCase cadastrarUsuarioUseCase;
    @Autowired private ListarUsuariosUseCase listarUsuariosUseCase;
    @Autowired private ListarUsuariosPorEmpresaUseCase listarUsuariosPorEmpresaUseCase;
    @Autowired private BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    @Autowired private BuscarUsuarioPorEmailUseCase buscarUsuarioPorEmailUseCase;
    @Autowired private AtualizarUsuarioUseCase atualizarUsuarioUseCase;
    @Autowired private AtualizarCoresPorIdUseCase atualizarCoresPorIdUseCase;
    @Autowired private AutenticarUsuarioUseCase autenticarUsuarioUseCase;
    @Autowired private AtualizarSenhaUseCase atualizarSenhaUseCase;
    @Autowired private DeletarUsuarioUseCase deletarUsuarioUseCase;
    @Autowired private EnviarCodigoUseCase enviarCodigoUseCase;

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
    public ResponseEntity<UsuarioResponseDto> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDto usuarioRequestDto) {
        UsuarioResponseDto response = cadastrarUsuarioUseCase.execute(usuarioRequestDto);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(
            summary = "Listar todos os usuários",
            description = "Esse endpoint retorna todos os usuários cadastrados no sistema.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listar() {
        List<UsuarioResponseDto> response = listarUsuariosUseCase.execute();
        return ResponseEntity.status(200).body(response);
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
    public ResponseEntity<List<UsuarioResponseDto>> listarPorEmpresa(@PathVariable Integer idEmpresa) {
        List<UsuarioResponseDto> response = listarUsuariosPorEmpresaUseCase.execute(idEmpresa);
        return ResponseEntity.status(200).body(response);
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
    public ResponseEntity<Void> deletar(@PathVariable Integer idUsuario, @RequestBody UsuarioPermissaoDto usuarioPermissaoDto) {
        deletarUsuarioUseCase.execute(idUsuario, usuarioPermissaoDto);
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
    public ResponseEntity<UsuarioResponseDto> atualizar(@PathVariable Integer idUsuario, @Valid @RequestBody UsuarioAtualizarDto usuarioAtualizarDto) {
        UsuarioResponseDto response = atualizarUsuarioUseCase.execute(idUsuario, usuarioAtualizarDto);
        return ResponseEntity.status(200).body(response);
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
    @PatchMapping("/cores/{idUsuario}")
    public ResponseEntity<Boolean> atualizarCores(@PathVariable Integer idUsuario, @Valid @RequestBody UsuarioAtualizarCoresDto usuarioAtualizarCoresDto) {
        Boolean response = atualizarCoresPorIdUseCase.execute(idUsuario, usuarioAtualizarCoresDto);
        return ResponseEntity.status(200).body(response);
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
    @PatchMapping("/senha/{idUsuario}")
    public ResponseEntity<UsuarioResponseDto> atualizarSenha(@PathVariable Integer idUsuario, @Valid @RequestBody UsuarioAtualizarSenhaDto usuarioAtualizarSenhaDto) {
        UsuarioResponseDto response = atualizarSenhaUseCase.execute(idUsuario, usuarioAtualizarSenhaDto);
        return ResponseEntity.status(200).body(response);
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
        return ResponseEntity.status(200).body(buscarUsuarioPorEmailUseCase.execute(email));
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
        LoginResponseDto response = buscarUsuarioPorIdUseCase.execute(idUsuario);
        return ResponseEntity.status(200).body(response);
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
            return ResponseEntity.status(200).body(autenticarUsuarioUseCase.execute(usuario));
        }

    @PostMapping("/codigoEsqueciASenha")
    public ResponseEntity<Void> enviarCodigoEsqueciASenha(@RequestBody UsuarioEnviarCodigoRequestDto usuarioEnviarCodigoRequestDto) {
        enviarCodigoUseCase.execute(usuarioEnviarCodigoRequestDto);
        return ResponseEntity.status(204).build();
    }
}
