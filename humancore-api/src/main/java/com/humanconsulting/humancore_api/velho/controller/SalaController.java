package com.humanconsulting.humancore_api.velho.controller;

import com.humanconsulting.humancore_api.velho.controller.dto.request.SalaRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.chat.ChatResponseDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.sala.SalaResponseDto;
import com.humanconsulting.humancore_api.velho.service.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("salas")
@CrossOrigin("*")
public class SalaController {
    @Autowired
    private SalaService service;

    @Operation(summary = "Cadastrar uma nova sala",
            description = "Esse endpoint cria uma nova sala no sistema",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sala cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para o cadastro")
    })
    @PostMapping
    public ResponseEntity<SalaResponseDto> cadastrarSala(@Valid @RequestBody SalaRequestDto salaRequestDto) {
        return ResponseEntity.status(201).body(service.cadastrar(salaRequestDto));
    }

    @Operation(summary = "Listar todas as salas",
            description = "Esse endpoint retorna uma lista com todas as salas cadastradas",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponse(responseCode = "200", description = "Lista de salas retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<SalaResponseDto>> listar() {
        return ResponseEntity.status(200).body(service.listar());
    }

    @Operation(summary = "Listar todas as salas a qual um usuário pertence",
            description = "Esse endpoint retorna uma lista com todas as salas cadastradas contendo o id do usuário",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salas encontradas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sala não encontrada")
    })
    @GetMapping("/porUsuario/{idUsuario}")
    public ResponseEntity<List<ChatResponseDto>> listarPorIdUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.status(200).body(service.getChatsDoUsuario(idUsuario));
    }

    @Operation(summary = "Buscar sala por ID",
            description = "Esse endpoint retorna os detalhes de uma sala específica com base no ID fornecido",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sala não encontrada")
    })
    @GetMapping("/{idSala}")
    public ResponseEntity<SalaResponseDto> buscarPorId(@PathVariable Integer idSala) {
        return ResponseEntity.status(200).body(service.passarParaResponse(service.buscarPorId(idSala)));
    }

    @Operation(summary = "Deletar uma sala",
            description = "Esse endpoint remove uma sala do sistema com base no ID fornecido",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sala deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sala não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Atualizar dados de uma sala",
            description = "Esse endpoint atualiza os dados de uma sala existente",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Sala não encontrada")
    })
    @PatchMapping("/{idSala}")
    public ResponseEntity<SalaResponseDto> atualizar(@PathVariable Integer idSala, @Valid @RequestBody SalaRequestDto request) {
        return ResponseEntity.status(200).body(service.atualizar(idSala, request));
    }
}
