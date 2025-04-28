package com.humanconsulting.humancore_api.controller;

import com.humanconsulting.humancore_api.controller.dto.atualizar.sprint.*;
import com.humanconsulting.humancore_api.controller.dto.request.SprintRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.SprintResponseDto;
import com.humanconsulting.humancore_api.model.Sprint;
import com.humanconsulting.humancore_api.service.SprintService;
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

    @Autowired
    private SprintService service;

    @Operation(
            summary = "Cadastrar uma nova Sprint",
            description = "Esse endpoint cria uma nova sprint.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sprint cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para o cadastro")
    })
    @PostMapping
    public ResponseEntity<SprintResponseDto> cadastrarSprint(@Valid @RequestBody SprintRequestDto sprintRequestDto) {
        return ResponseEntity.status(201).body(service.cadastrar(sprintRequestDto));
    }

    @Operation(
            summary = "Listar todas as Sprints",
            description = "Esse endpoint retorna todas as sprints cadastradas.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponse(responseCode = "200", description = "Lista de sprints retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<Sprint>> listar() {
        List<Sprint> all = service.listar();
        return ResponseEntity.status(200).body(all);
    }

    @Operation(
            summary = "Buscar Sprint por ID",
            description = "Esse endpoint retorna as informações detalhadas de uma sprint através do seu ID.",
            parameters = @Parameter(name = "id", description = "ID da sprint a ser buscada."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sprint encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sprint não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Sprint> buscarPorId(@PathVariable Integer id) {
        Sprint sprint = service.buscarPorId(id);
        return ResponseEntity.status(200).body(sprint);
    }

    @Operation(
            summary = "Buscar Sprints por Projeto",
            description = "Esse endpoint retorna as sprints associadas a um projeto específico.",
            parameters = @Parameter(name = "idProjeto", description = "ID do projeto para buscar as sprints."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de sprints por projeto retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Lista de sprints por projeto não retornada")
    })
    @GetMapping("/buscarPorProjeto/{idProjeto}")
    public ResponseEntity<List<SprintResponseDto>> buscarPorIdProjeto(@PathVariable Integer idProjeto) {
        return ResponseEntity.status(200).body(service.buscarPorIdProjeto(idProjeto));
    }

    @Operation(
            summary = "Deletar Sprint",
            description = "Esse endpoint deleta uma sprint baseada no seu ID.",
            parameters = @Parameter(name = "id", description = "ID da sprint a ser deletada."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sprint deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sprint não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(
            summary = "Atualizar uma Sprint",
            description = "Esse endpoint atualiza as informações de uma sprint.",
            parameters = @Parameter(name = "idSprint", description = "ID da sprint a ser atualizada."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sprint atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Sprint não encontrada")
    })
    @PutMapping("/{idSprint}")
    public ResponseEntity<SprintResponseDto> atualizar(@PathVariable Integer idSprint, @Valid @RequestBody SprintAtualizarRequestDto request) {
        return ResponseEntity.status(200).body(service.atualizar(idSprint, request));
    }
}
