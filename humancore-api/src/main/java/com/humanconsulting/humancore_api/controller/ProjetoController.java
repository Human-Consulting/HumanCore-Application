package com.humanconsulting.humancore_api.controller;

import com.humanconsulting.humancore_api.controller.dto.atualizar.projeto.ProjetoAtualizarRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.ProjetoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.projeto.DashboardProjetoResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.projeto.ProjetoResponseDto;
import com.humanconsulting.humancore_api.model.Projeto;
import com.humanconsulting.humancore_api.service.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projetos")
@CrossOrigin("*")
public class ProjetoController {

    @Autowired
    private ProjetoService service;

    @Operation(
            summary = "Cadastrar um novo projeto",
            description = "Esse endpoint cria um novo projeto.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @PostMapping
    public ResponseEntity<ProjetoResponseDto> cadastrarProjeto(@Valid @RequestBody ProjetoRequestDto projetoRequestDto) {
        return ResponseEntity.status(201).body(service.cadastrar(projetoRequestDto));
    }

    @Operation(
            summary = "Listar todos os projetos",
            description = "Esse endpoint retorna todos os projetos cadastrados.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @GetMapping
    public ResponseEntity<List<ProjetoResponseDto>> listar() {
        return ResponseEntity.status(200).body(service.listar());
    }

    @Operation(
            summary = "Buscar projeto por ID",
            description = "Esse endpoint retorna as informações detalhadas do projeto através do seu ID.",
            parameters = @Parameter(name = "idProjeto", description = "ID do projeto para buscar as informações."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @GetMapping("/{idProjeto}")
    public ResponseEntity<DashboardProjetoResponseDto> buscarPorId(@PathVariable Integer idProjeto) {
        return ResponseEntity.status(200).body(service.criarDashboardResponse(service.buscarPorId(idProjeto)));
    }

    @Operation(
            summary = "Buscar projetos por ID da empresa",
            description = "Esse endpoint retorna os projetos de uma empresa específica.",
            parameters = @Parameter(name = "idEmpresa", description = "ID da empresa para listar os projetos."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @GetMapping("/buscarPorEmpresa/{idEmpresa}")
    public ResponseEntity<List<ProjetoResponseDto>> buscarPorIdEmpresa(@PathVariable Integer idEmpresa) {
        return ResponseEntity.status(200).body(service.buscarPorIdEmpresa(idEmpresa));
    }

    @Operation(
            summary = "Deletar projeto",
            description = "Esse endpoint deleta um projeto baseado no seu ID.",
            parameters = @Parameter(name = "idProjeto", description = "ID do projeto a ser deletado."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @DeleteMapping("/{idProjeto}")
    public ResponseEntity<Void> deletar(@PathVariable Integer idProjeto) {
        service.deletar(idProjeto);
        return ResponseEntity.status(204).build();
    }

    @Operation(
            summary = "Atualizar um projeto",
            description = "Esse endpoint atualiza as informações de um projeto.",
            parameters = @Parameter(name = "idProjeto", description = "ID do projeto a ser atualizado."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @PutMapping("/{idProjeto}")
    public ResponseEntity<ProjetoResponseDto> atualizar(@PathVariable Integer idProjeto, @Valid @RequestBody ProjetoAtualizarRequestDto request) {
        return ResponseEntity.status(200).body(service.atualizar(idProjeto, request));
    }
}
