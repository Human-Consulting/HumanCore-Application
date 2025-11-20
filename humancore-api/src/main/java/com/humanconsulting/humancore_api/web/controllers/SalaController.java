package com.humanconsulting.humancore_api.web.controllers;

import com.humanconsulting.humancore_api.application.usecases.sala.*;
import com.humanconsulting.humancore_api.web.dtos.request.SalaRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.web.dtos.response.chat.ChatResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.sala.SalaResponseDto;
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
    @Autowired private CadastrarSalaUseCase cadastrarSalaUseCase;
    @Autowired private ListarSalasUseCase listarSalasUseCase;
    @Autowired private ListarSalasPorUsuarioUseCase listarSalasPorUsuarioUseCase;
    @Autowired private BuscarSalaPorIdUseCase buscarSalaPorIdUseCase;
    @Autowired private AtualizarSalaUseCase atualizarSalaUseCase;
    @Autowired private DeletarSalaUseCase deletarSalaUseCase;

    @Operation(summary = "Cadastrar uma nova sala",
            description = "Esse endpoint cria uma nova sala no sistema",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "SalaEntity cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para o cadastro")
    })
    @PostMapping
    public ResponseEntity<SalaResponseDto> cadastrarSala(@Valid @RequestBody SalaRequestDto salaRequestDto) {
        SalaResponseDto response = cadastrarSalaUseCase.execute(salaRequestDto);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Listar todas as salas",
            description = "Esse endpoint retorna uma lista com todas as salas cadastradas",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponse(responseCode = "200", description = "Lista de salas retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<SalaResponseDto>> listar() {
        List<SalaResponseDto> response = listarSalasUseCase.execute();
        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "Listar todas as salas a qual um usuário pertence",
            description = "Esse endpoint retorna uma lista com todas as salas cadastradas contendo o id do usuário",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salas encontradas com sucesso"),
            @ApiResponse(responseCode = "404", description = "SalaEntity não encontrada")
    })
    @GetMapping("/porUsuario/{idUsuario}")
    public ResponseEntity<List<ChatResponseDto>> listarPorIdUsuario(@PathVariable Integer idUsuario) {
        List<ChatResponseDto> response = listarSalasPorUsuarioUseCase.execute(idUsuario);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "Buscar sala por ID",
            description = "Esse endpoint retorna os detalhes de uma sala específica com base no ID fornecido",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SalaEntity encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "SalaEntity não encontrada")
    })
    @GetMapping("/{idSala}")
    public ResponseEntity<SalaResponseDto> buscarPorId(@PathVariable Integer idSala) {
        SalaResponseDto response = buscarSalaPorIdUseCase.execute(idSala);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "Deletar uma sala",
            description = "Esse endpoint remove uma sala do sistema com base no ID fornecido",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "SalaEntity deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "SalaEntity não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id, @RequestBody UsuarioPermissaoDto usuarioPermissaoDto) {
        deletarSalaUseCase.execute(id, usuarioPermissaoDto);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Atualizar dados de uma sala",
            description = "Esse endpoint atualiza os dados de uma sala existente",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SalaEntity atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "SalaEntity não encontrada")
    })
    @PatchMapping("/{idSala}")
    public ResponseEntity<SalaResponseDto> atualizar(@PathVariable Integer idSala, @Valid @RequestBody SalaRequestDto salaRequestDto) {
        SalaResponseDto response = atualizarSalaUseCase.execute(idSala, salaRequestDto);
        return ResponseEntity.status(200).body(response);
    }
}
