package com.humanconsulting.humancore_api.controller;

import com.humanconsulting.humancore_api.controller.dto.atualizar.tarefa.AtualizarGeralRequestDto;
import com.humanconsulting.humancore_api.controller.dto.atualizar.tarefa.AtualizarStatusRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.TarefaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.TarefaResponseDto;
import com.humanconsulting.humancore_api.service.TarefaService;
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

    @Autowired
    private TarefaService service;

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
        return ResponseEntity.status(201).body(service.cadastrar(entregaRequestDto));
    }

    @Operation(
            summary = "Listar todas as tarefas",
            description = "Esse endpoint retorna uma lista com todas as tarefas cadastradas.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<TarefaResponseDto>> listar() {
        return ResponseEntity.status(200).body(service.listar());
    }

    @Operation(
            summary = "Listar tarefas por ID da Sprint",
            description = "Esse endpoint retorna uma lista de tarefas de uma sprint específica.",
            parameters = @Parameter(name = "idSprint", description = "ID da Sprint para buscar as tarefas."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso")
    @GetMapping("/buscarPorSprint/{idSprint}")
    public ResponseEntity<List<TarefaResponseDto>> listarPorIdSprint(@PathVariable Integer idSprint) {
        return ResponseEntity.status(200).body(service.listarPorSprint(idSprint));
    }

    @Operation(
            summary = "Buscar tarefa por ID",
            description = "Esse endpoint retorna os detalhes de uma tarefa específica a partir do seu ID.",
            parameters = @Parameter(name = "idTarefa", description = "ID da tarefa para buscar os detalhes."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @GetMapping("/{idTarefa}")
    public ResponseEntity<TarefaResponseDto> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @Operation(
            summary = "Deletar uma tarefa",
            description = "Esse endpoint deleta uma tarefa a partir do seu ID.",
            parameters = @Parameter(name = "idTarefa", description = "ID da tarefa que será deletada."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @DeleteMapping("/{idTarefa}")
    public ResponseEntity<Void> deletar(@PathVariable Integer idTarefa) {
        service.deletar(idTarefa);
        return ResponseEntity.status(204).build();
    }

    @Operation(
            summary = "Atualizar tarefa",
            description = "Esse endpoint atualiza os dados de uma tarefa com base no ID.",
            parameters = @Parameter(name = "idTarefa", description = "ID da tarefa a ser atualizada."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @PatchMapping("/{idTarefa}")
    public ResponseEntity<TarefaResponseDto> atualizar(@PathVariable Integer idEditor, @PathVariable Integer idTarefa, @Valid @RequestBody AtualizarGeralRequestDto entrega) {
        return ResponseEntity.status(200).body(service.atualizar(idEditor, idTarefa, entrega));
    }

    @Operation(
            summary = "Atualizar impedimento em tarefa",
            description = "Esse endpoint atualiza o status de impedimento de uma tarefa.",
            parameters = @Parameter(name = "idTarefa", description = "ID da tarefa a ser atualizada."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @PatchMapping("/impedimento/{idTarefa}")
    public ResponseEntity<TarefaResponseDto> atualizarImpedimento(@PathVariable Integer idTarefa, @RequestBody AtualizarStatusRequestDto request) {
        return ResponseEntity.status(200).body(service.atualizarImpedimento(idTarefa, request));
    }
}
