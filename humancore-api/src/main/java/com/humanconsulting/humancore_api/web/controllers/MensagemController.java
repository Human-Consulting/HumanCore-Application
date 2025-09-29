package com.humanconsulting.humancore_api.web.controllers;

import com.humanconsulting.humancore_api.application.usecases.mensagem.AtualizarMensagemUseCase;
import com.humanconsulting.humancore_api.application.usecases.mensagem.BuscarMensagemPorIdUseCase;
import com.humanconsulting.humancore_api.application.usecases.mensagem.CadastrarMensagemUseCase;
import com.humanconsulting.humancore_api.application.usecases.mensagem.DeletarMensagemUseCase;
import com.humanconsulting.humancore_api.application.usecases.mensagem.ListarMensagensUseCase;
import com.humanconsulting.humancore_api.web.dtos.atualizar.mensagem.MensagemAtualizarRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.MensagemRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.web.dtos.response.chat.ChatMensagemUnificadaDto;
import com.humanconsulting.humancore_api.web.dtos.response.mensagem.MensagemResponseDto;
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
    @Autowired private CadastrarMensagemUseCase cadastrarMensagemUseCase;
    @Autowired private ListarMensagensUseCase listarMensagensUseCase;
    @Autowired private BuscarMensagemPorIdUseCase buscarMensagemPorIdUseCase;
    @Autowired private AtualizarMensagemUseCase atualizarMensagemUseCase;
    @Autowired private DeletarMensagemUseCase deletarMensagemUseCase;

    @Operation(summary = "Cadastrar uma nova mensagem",
            description = "Esse endpoint cria uma nova mensagem no sistema",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "MensagemEntity cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para o cadastro")
    })
    @PostMapping
    public ResponseEntity<ChatMensagemUnificadaDto> cadastrarMensagem(@Valid @RequestBody MensagemRequestDto mensagemRequestDto) {
        ChatMensagemUnificadaDto response = cadastrarMensagemUseCase.execute(mensagemRequestDto);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Listar todas as mensagens",
            description = "Esse endpoint retorna uma lista com todas as mensagens cadastradas",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponse(responseCode = "200", description = "Lista de mensagens retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<ChatMensagemUnificadaDto>> listar() {
        List<ChatMensagemUnificadaDto> response = listarMensagensUseCase.execute();
        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "Buscar mensagem por ID",
            description = "Esse endpoint retorna os detalhes de uma mensagem específica com base no ID fornecido",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "MensagemEntity encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "MensagemEntity não encontrada")
    })
    @GetMapping("/{idMensagem}")
    public ResponseEntity<MensagemResponseDto> buscarPorId(@PathVariable Integer idMensagem) {
        MensagemResponseDto response = buscarMensagemPorIdUseCase.execute(idMensagem);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "Deletar uma mensagem",
            description = "Esse endpoint remove uma mensagem do sistema com base no ID fornecido",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "MensagemEntity deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "MensagemEntity não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id, @RequestBody UsuarioPermissaoDto usuarioPermissaoDto) {
        deletarMensagemUseCase.execute(id, usuarioPermissaoDto);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Atualizar dados de uma mensagem",
            description = "Esse endpoint atualiza os dados de uma mensagem existente",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "MensagemEntity atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "MensagemEntity não encontrada")
    })
    @PatchMapping("/{idMensagem}")
    public ResponseEntity<ChatMensagemUnificadaDto> atualizar(@PathVariable Integer idMensagem, @Valid @RequestBody MensagemAtualizarRequestDto mensagemAtualizarRequestDto) {
        ChatMensagemUnificadaDto response = atualizarMensagemUseCase.execute(idMensagem, mensagemAtualizarRequestDto);
        return ResponseEntity.status(200).body(response);
    }
}
