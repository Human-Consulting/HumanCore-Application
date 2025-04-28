package com.humanconsulting.humancore_api.controller;

import com.humanconsulting.humancore_api.controller.dto.atualizar.investimento.AtualizarInvestimentoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.InvestimentoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.service.InvestimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @PostMapping
    public ResponseEntity<InvestimentoResponseDto> cadastrarFinanceiro(@Valid @RequestBody InvestimentoRequestDto financeiroRequestDto) {
        return ResponseEntity.status(201).body(service.cadastrar(financeiroRequestDto));
    }

    @Operation(
            summary = "Listar todos os registros financeiros",
            description = "Esse endpoint retorna todos os registros financeiros cadastrados.",
            security = @SecurityRequirement(name = "Bearer")
    )
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
    @GetMapping("/{id}")
    public ResponseEntity<InvestimentoResponseDto> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @Operation(
            summary = "Deletar um registro financeiro",
            description = "Esse endpoint deleta um registro financeiro a partir do seu ID.",
            parameters = @Parameter(name = "id", description = "ID do registro financeiro a ser deletado."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(
            summary = "Atualizar um registro financeiro",
            description = "Esse endpoint atualiza um registro financeiro com base no ID.",
            parameters = @Parameter(name = "idFinanceiro", description = "ID do registro financeiro a ser atualizado."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @PutMapping("/{idFinanceiro}")
    public ResponseEntity<InvestimentoResponseDto> atualizar(@PathVariable Integer idFinanceiro, @Valid @RequestBody AtualizarInvestimentoRequestDto request) {
        return ResponseEntity.status(200).body(service.atualizar(idFinanceiro, request));
    }
}
