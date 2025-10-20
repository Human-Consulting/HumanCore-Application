package com.humanconsulting.humancore_api.web.controllers;

import com.humanconsulting.humancore_api.application.usecases.tarefa.*;
import com.humanconsulting.humancore_api.web.dtos.atualizar.tarefa.AtualizarGeralRequestDto;
import com.humanconsulting.humancore_api.web.dtos.atualizar.tarefa.AtualizarStatusRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.TarefaRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;
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
@RequestMapping("tarefas")
@CrossOrigin("*")
public class TarefaController {

    @Autowired private CadastrarTarefaUseCase cadastrarTarefaUseCase;
    @Autowired private ListarTarefasUseCase listarTarefasUseCase;
    @Autowired private ListarTarefasPorSprintUseCase listarTarefasPorSprintUseCase;
    @Autowired private BuscarTarefaPorIdUseCase buscarTarefaPorIdUseCase;
    @Autowired private AtualizarTarefaUseCase atualizarTarefaUseCase;
    @Autowired private AtualizarImpedimentoTarefaUseCase atualizarImpedimentoTarefaUseCase;
    @Autowired private DeletarTarefaUseCase deletarTarefaUseCase;

    @Operation(
            summary = "Cadastrar uma nova tarefa",
            description = "Esse endpoint cria uma nova tarefa.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entrega cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para o cadastro")
    })
    @PostMapping
    public ResponseEntity<TarefaResponseDto> cadastrarEntrega(@Valid @RequestBody TarefaRequestDto entregaRequestDto) {
        TarefaResponseDto response = cadastrarTarefaUseCase.execute(entregaRequestDto);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(
            summary = "Listar todas as tarefas",
            description = "Esse endpoint retorna uma lista com todas as tarefas cadastradas.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<TarefaResponseDto>> listar() {
        List<TarefaResponseDto> response = listarTarefasUseCase.execute();
        return ResponseEntity.status(200).body(response);
    }

    @Operation(
            summary = "Listar tarefas por ID da SprintEntity",
            description = "Esse endpoint retorna uma lista de tarefas de uma sprint específica.",
            parameters = @Parameter(name = "idSprint", description = "ID da SprintEntity para buscar as tarefas."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso")
    @GetMapping("/buscarPorSprint/{idSprint}")
    public ResponseEntity<List<TarefaResponseDto>> listarPorIdSprint(@PathVariable Integer idSprint) {
        List<TarefaResponseDto> response = listarTarefasPorSprintUseCase.execute(idSprint);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(
            summary = "Buscar tarefa por ID",
            description = "Esse endpoint retorna os detalhes de uma tarefa específica a partir do seu ID.",
            parameters = @Parameter(name = "idTarefa", description = "ID da tarefa para buscar os detalhes."),
            security = @SecurityRequirement(name = "Bearer")
    )

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "TarefaEntity deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "TarefaEntity não encontrada")
    })
    @DeleteMapping("/{idTarefa}")
    public ResponseEntity<Void> deletar(@PathVariable Integer idTarefa, @RequestBody UsuarioPermissaoDto usuarioPermissaoDto) {
        deletarTarefaUseCase.execute(idTarefa, usuarioPermissaoDto);
        return ResponseEntity.status(200).build();
    }

    @Operation(
            summary = "Atualizar tarefa",
            description = "Esse endpoint atualiza os dados de uma tarefa com base no ID.",
            parameters = @Parameter(name = "idTarefa", description = "ID da tarefa a ser atualizada."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TarefaEntity atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "TarefaEntity não encontrada")
    })
    @PatchMapping("/{idTarefa}")
    public ResponseEntity<TarefaResponseDto> atualizar(@PathVariable Integer idTarefa, @Valid @RequestBody AtualizarGeralRequestDto atualizarGeralRequestDto) {
        TarefaResponseDto response = atualizarTarefaUseCase.execute(idTarefa, atualizarGeralRequestDto);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(
            summary = "Atualizar status de tarefa",
            description = "Esse endpoint atualiza o status de uma tarefa.",
            parameters = @Parameter(name = "idTarefa", description = "ID da tarefa a ser atualizada."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TarefaEntity atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "TarefaEntity não encontrada")
    })
    @PatchMapping("/status/{idTarefa}")
    public ResponseEntity<TarefaResponseDto> atualizarStatus(@PathVariable Integer idTarefa, @Valid @RequestBody AtualizarStatusRequestDto atualizarStatusRequestDto) {
        TarefaResponseDto response = atualizarImpedimentoTarefaUseCase.execute(idTarefa, atualizarStatusRequestDto);
        return ResponseEntity.status(200).body(response);
    }
}
