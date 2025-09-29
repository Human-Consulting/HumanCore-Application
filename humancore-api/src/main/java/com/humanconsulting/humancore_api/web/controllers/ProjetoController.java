package com.humanconsulting.humancore_api.web.controllers;

import com.humanconsulting.humancore_api.application.usecases.projeto.*;
import com.humanconsulting.humancore_api.web.dtos.atualizar.projeto.ProjetoAtualizarRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.ProjetoRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.DashboardProjetoResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseDto;
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
@RequestMapping("projetos")
@CrossOrigin("*")
public class ProjetoController {

    @Autowired private CadastrarProjetoUseCase cadastrarProjetoUseCase;
    @Autowired private ListarProjetosUseCase listarProjetosUseCase;
    @Autowired private BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase;
    @Autowired private BuscarProjetosPorEmpresaUseCase buscarProjetosPorEmpresaUseCase;
    @Autowired private AtualizarProjetoUseCase atualizarProjetoUseCase;
    @Autowired private DeletarProjetoUseCase deletarProjetoUseCase;
    @Autowired private CriarDashboardProjetoUseCase criarDashboardProjetoUseCase;

    @Operation(
            summary = "Cadastrar um novo projeto",
            description = "Esse endpoint cria um novo projeto.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "ProjetoEntity cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para o cadastro")
    })
    @PostMapping
    public ResponseEntity<ProjetoResponseDto> cadastrarProjeto(@Valid @RequestBody ProjetoRequestDto projetoRequestDto) {
        ProjetoResponseDto response = cadastrarProjetoUseCase.execute(projetoRequestDto);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(
            summary = "Listar todos os projetos",
            description = "Esse endpoint retorna todos os projetos cadastrados.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponse(responseCode = "200", description = "Lista de projetos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<ProjetoResponseDto>> listar() {
        List<ProjetoResponseDto> response = listarProjetosUseCase.execute();
        return ResponseEntity.status(200).body(response);
    }

    @Operation(
            summary = "Buscar projeto por ID",
            description = "Esse endpoint retorna as informações detalhadas do projeto através do seu ID.",
            parameters = @Parameter(name = "idProjeto", description = "ID do projeto para buscar as informações."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ProjetoEntity encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "ProjetoEntity não encontrado")
    })
    @GetMapping("/{idProjeto}")
    public ResponseEntity<DashboardProjetoResponseDto> buscarPorId(@PathVariable Integer idProjeto) {
        var projeto = buscarProjetoPorIdUseCase.execute(idProjeto);
        DashboardProjetoResponseDto dashboard = criarDashboardProjetoUseCase.execute(projeto);
        return ResponseEntity.status(200).body(dashboard);
    }

    @Operation(
            summary = "Buscar projetos por ID da empresa",
            description = "Esse endpoint retorna os projetos de uma empresa específica.",
            parameters = @Parameter(name = "idEmpresa", description = "ID da empresa para listar os projetos."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de projetos por empresa retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Lista de projetos por empresa não retornada")
    })
    @GetMapping("/buscarPorEmpresa/{idEmpresa}")
    public ResponseEntity<List<ProjetoResponseDto>> buscarPorIdEmpresa(@PathVariable Integer idEmpresa) {
        List<ProjetoResponseDto> response = buscarProjetosPorEmpresaUseCase.execute(idEmpresa);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(
            summary = "Deletar projeto",
            description = "Esse endpoint deleta um projeto baseado no seu ID.",
            parameters = @Parameter(name = "idProjeto", description = "ID do projeto a ser deletado."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "ProjetoEntity deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "ProjetoEntity não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id, @RequestBody UsuarioPermissaoDto usuarioPermissaoDto) {
        deletarProjetoUseCase.execute(id, usuarioPermissaoDto);
        return ResponseEntity.status(204).build();
    }

    @Operation(
            summary = "Atualizar um projeto",
            description = "Esse endpoint atualiza as informações de um projeto.",
            parameters = @Parameter(name = "idProjeto", description = "ID do projeto a ser atualizado."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ProjetoEntity atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "ProjetoEntity não encontrado")
    })
    @PatchMapping("/{idProjeto}")
    public ResponseEntity<ProjetoResponseDto> atualizar(@PathVariable Integer idProjeto, @Valid @RequestBody ProjetoAtualizarRequestDto projetoAtualizarRequestDto) {
        ProjetoResponseDto response = atualizarProjetoUseCase.execute(idProjeto, projetoAtualizarRequestDto);
        return ResponseEntity.status(200).body(response);
    }
}
