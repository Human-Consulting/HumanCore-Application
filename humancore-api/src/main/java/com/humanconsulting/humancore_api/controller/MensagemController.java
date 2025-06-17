package com.humanconsulting.humancore_api.controller;

import com.humanconsulting.humancore_api.controller.dto.atualizar.mensagem.MensagemAtualizarRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.MensagemRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.controller.dto.response.chat.ChatMensagemUnificadaDto;
import com.humanconsulting.humancore_api.controller.dto.response.mensagem.MensagemResponseDto;
import com.humanconsulting.humancore_api.observer.SalaNotifier;
import com.humanconsulting.humancore_api.service.MensagemService;
import com.humanconsulting.humancore_api.service.WebSocketMensagemPublisher;
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
@RequestMapping("mensagens")
@CrossOrigin("*")
public class MensagemController {
    @Autowired private MensagemService service;
    @Autowired private SalaNotifier salaNotifier;

    @Operation(summary = "Cadastrar uma nova mensagem",
            description = "Esse endpoint cria uma nova mensagem no sistema",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Mensagem cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para o cadastro")
    })
    @PostMapping
    public ResponseEntity<ChatMensagemUnificadaDto> cadastrarMensagem(@Valid @RequestBody MensagemRequestDto mensagemRequestDto) {
        return ResponseEntity.status(201).body(salaNotifier.enviarMensagem(mensagemRequestDto));
    }

    @Operation(summary = "Listar todas as mensagens",
            description = "Esse endpoint retorna uma lista com todas as mensagens cadastradas",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponse(responseCode = "200", description = "Lista de mensagens retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<MensagemResponseDto>> listar() {
        return ResponseEntity.status(200).body(service.listar());
    }

    @Operation(summary = "Buscar mensagem por ID",
            description = "Esse endpoint retorna os detalhes de uma mensagem específica com base no ID fornecido",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mensagem encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Mensagem não encontrada")
    })
    @GetMapping("/{idMensagem}")
    public ResponseEntity<MensagemResponseDto> buscarPorId(@PathVariable Integer idMensagem) {
        return ResponseEntity.status(200).body(service.passarParaResponse(service.buscarPorId(idMensagem)));
    }

    @Operation(summary = "Deletar uma mensagem",
            description = "Esse endpoint remove uma mensagem do sistema com base no ID fornecido",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Mensagem deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Mensagem não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id, @RequestBody UsuarioPermissaoDto usuarioPermissaoDto) {
        service.deletar(id, usuarioPermissaoDto);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Atualizar dados de uma mensagem",
            description = "Esse endpoint atualiza os dados de uma mensagem existente",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mensagem atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Mensagem não encontrada")
    })
    @PatchMapping("/{idMensagem}")
    public ResponseEntity<MensagemResponseDto> atualizar(@PathVariable Integer idMensagem, @Valid @RequestBody MensagemAtualizarRequestDto request) {
        return ResponseEntity.status(200).body(service.atualizar(idMensagem, request));
    }
}
