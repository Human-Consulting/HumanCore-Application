package com.humanconsulting.humancore_api.velho.controller;

import com.humanconsulting.humancore_api.velho.controller.dto.atualizar.investimento.AtualizarInvestimentoRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.InvestimentoRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.velho.service.InvestimentoService;
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
@RequestMapping("investimentos")
@CrossOrigin("*")
public class InvestimentoController {

    @Autowired
    private InvestimentoService service;

    @Operation(
            summary = "Cadastrar um novo financeiro",
            description = "Esse endpoint cria um novo registro financeiro.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Investimento cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para o cadastro")
    })
    @PostMapping
    public ResponseEntity<InvestimentoResponseDto> cadastrarFinanceiro(@Valid @RequestBody InvestimentoRequestDto financeiroRequestDto) {
        return ResponseEntity.status(201).body(service.cadastrar(financeiroRequestDto));
    }

    @Operation(
            summary = "Listar todos os registros financeiros",
            description = "Esse endpoint retorna todos os registros financeiros cadastrados.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponse(responseCode = "200", description = "Lista de investimentos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<InvestimentoResponseDto>> listar() {
        return ResponseEntity.status(200).body(service.listar());
    }

    @Operation(
            summary = "Listar registros financeiros por ID do projeto",
            description = "Esse endpoint retorna os registros financeiros de um projeto específico.",
            parameters = @Parameter(name = "idProjeto", description = "ID do projeto para buscar os registros financeiros."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Investimentos encontrados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Investimentos não encontrados")
    })
    @GetMapping("/buscarPorProjeto/{idProjeto}")
    public ResponseEntity<List<InvestimentoResponseDto>> listarPorId(@PathVariable Integer idProjeto) {
        return ResponseEntity.status(200).body(service.listarPorProjeto(idProjeto));
    }

    @Operation(
            summary = "Buscar registro financeiro por ID",
            description = "Esse endpoint retorna os detalhes de um registro financeiro específico a partir do seu ID.",
            parameters = @Parameter(name = "id", description = "ID do registro financeiro."),
            security = @SecurityRequirement(name = "Bearer")
    )

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Investimento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Investimento não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id, @RequestBody UsuarioPermissaoDto usuarioPermissaoDto) {
        service.deletar(id, usuarioPermissaoDto);
        return ResponseEntity.status(204).build();
    }

    @Operation(
            summary = "Atualizar um registro financeiro",
            description = "Esse endpoint atualiza um registro financeiro com base no ID.",
            parameters = @Parameter(name = "idFinanceiro", description = "ID do registro financeiro a ser atualizado."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Investimento atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Investimento não encontrado")
    })
    @PatchMapping("/{idFinanceiro}")
    public ResponseEntity<InvestimentoResponseDto> atualizar(@PathVariable Integer idFinanceiro, @Valid @RequestBody AtualizarInvestimentoRequestDto request) {
        return ResponseEntity.status(200).body(service.atualizar(idFinanceiro, request));
    }
}
