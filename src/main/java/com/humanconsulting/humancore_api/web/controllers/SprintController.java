package com.humanconsulting.humancore_api.web.controllers;

import com.humanconsulting.humancore_api.application.usecases.sprint.*;
import com.humanconsulting.humancore_api.web.dtos.atualizar.sprint.SprintAtualizarRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.SprintRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseDto;
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
@RequestMapping("sprints")
@CrossOrigin("*")
public class SprintController {

    @Autowired private CadastrarSprintUseCase cadastrarSprintUseCase;
    @Autowired private BuscarSprintPorIdUseCase buscarSprintPorIdUseCase;
    @Autowired private BuscarSprintsPorProjetoUseCase buscarSprintsPorProjetoUseCase;
    @Autowired private AtualizarSprintUseCase atualizarSprintUseCase;
    @Autowired private DeletarSprintUseCase deletarSprintUseCase;

    @Operation(
            summary = "Cadastrar uma nova SprintEntity",
            description = "Esse endpoint cria uma nova sprint.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "SprintEntity cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para o cadastro")
    })
    @PostMapping
    public ResponseEntity<SprintResponseDto> cadastrarSprint(@Valid @RequestBody SprintRequestDto sprintRequestDto) {
        SprintResponseDto response = cadastrarSprintUseCase.execute(sprintRequestDto);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(
            summary = "Buscar SprintEntity por ID",
            description = "Esse endpoint retorna as informações detalhadas de uma sprint através do seu ID.",
            parameters = @Parameter(name = "id", description = "ID da sprint a ser buscada."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SprintEntity encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "SprintEntity não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SprintResponseDto> buscarPorId(@PathVariable Integer id) {
        SprintResponseDto response = buscarSprintPorIdUseCase.execute(id);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(
            summary = "Buscar Sprints por ProjetoEntity",
            description = "Esse endpoint retorna as sprints associadas a um projeto específico.",
            parameters = @Parameter(name = "idProjeto", description = "ID do projeto para buscar as sprints."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de sprints por projeto retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Lista de sprints por projeto não retornada")
    })
    @GetMapping("/buscarPorProjeto/{idProjeto}")
    public ResponseEntity<List<SprintResponseDto>> buscarPorProjeto(@PathVariable Integer idProjeto) {
        List<SprintResponseDto> response = buscarSprintsPorProjetoUseCase.execute(idProjeto);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(
            summary = "Deletar SprintEntity",
            description = "Esse endpoint deleta uma sprint baseada no seu ID.",
            parameters = @Parameter(name = "id", description = "ID da sprint a ser deletada."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "SprintEntity deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "SprintEntity não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id, @RequestBody UsuarioPermissaoDto usuarioPermissaoDto) {
        deletarSprintUseCase.execute(id, usuarioPermissaoDto);
        return ResponseEntity.status(204).build();
    }

    @Operation(
            summary = "Atualizar uma SprintEntity",
            description = "Esse endpoint atualiza as informações de uma sprint.",
            parameters = @Parameter(name = "idSprint", description = "ID da sprint a ser atualizada."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SprintEntity atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "SprintEntity não encontrada")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<SprintResponseDto> atualizar(@PathVariable Integer id, @Valid @RequestBody SprintAtualizarRequestDto sprintAtualizarRequestDto) {
        SprintResponseDto response = atualizarSprintUseCase.execute(id, sprintAtualizarRequestDto);
        return ResponseEntity.status(200).body(response);
    }
}
