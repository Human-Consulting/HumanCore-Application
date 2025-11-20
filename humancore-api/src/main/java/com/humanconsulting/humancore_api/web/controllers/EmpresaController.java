package com.humanconsulting.humancore_api.web.controllers;

import com.humanconsulting.humancore_api.application.usecases.empresa.*;
import com.humanconsulting.humancore_api.domain.utils.PageResult;
import com.humanconsulting.humancore_api.web.dtos.atualizar.empresa.EmpresaAtualizarRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.EmpresaRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.DashboardEmpresaResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.KpiEmpresaResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("empresas")
@CrossOrigin("*")
public class EmpresaController {

    @Autowired private CadastrarEmpresaUseCase cadastrarEmpresaUseCase;
    @Autowired private ListarEmpresasUseCase listarEmpresasUseCase;
    @Autowired private ListarEmpresasMenuRapidoUseCase listarEmpresasMenuRapidoUseCase;
    @Autowired private ListarEmpresasKpisUseCase listarEmpresasKpisUseCase;
    @Autowired private CriarDashboardEmpresaUseCase criarDashboardEmpresaUseCase;
    @Autowired private DeletarEmpresaUseCase deletarEmpresaUseCase;
    @Autowired private AtualizarEmpresaUseCase atualizarEmpresaUseCase;

    @Operation(summary = "Cadastrar uma nova empresa",
            description = "Esse endpoint cria uma nova empresa no sistema",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "EmpresaEntity cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para o cadastro")
    })
    @PostMapping
    public ResponseEntity<EmpresaResponseDto> cadastrarEmpresa(@Valid @RequestBody EmpresaRequestDto empresaRequestDto) {
        EmpresaResponseDto response = cadastrarEmpresaUseCase.execute(empresaRequestDto);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Listar todas as empresas",
            description = "Esse endpoint retorna uma lista com todas as empresas cadastradas",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponse(responseCode = "200", description = "Lista de empresas retornada com sucesso")
    @GetMapping
    public ResponseEntity<PageResult<EmpresaResponseDto>> listar(@RequestParam(name = "page", defaultValue = "0") int page,
                                                           @RequestParam(name = "size", defaultValue = "10") int size, @RequestParam(name = "nome", required = false) String nome) {
        PageResult<EmpresaResponseDto> response = listarEmpresasUseCase.execute(page, size, nome);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(
            summary = "Listar empresas com menos informações para lateralBar",
            description = "Esse endpoint retorna as empresas contendo apenas nome, progresso, comImpedimento, urlImagem e idEmpresa.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de projetos por empresa retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Lista de projetos por empresa não retornada")
    })
    @GetMapping("/menuRapido")
    public ResponseEntity<PageResult<EmpresaResponseDto>> listarMenuRapido(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                                     @RequestParam(name = "size", defaultValue = "10") int size,
                                                                           @RequestParam(name = "nome", required = false) String nome,
                                                                           @RequestParam(name = "impedidos", required = false) Boolean impedidos,
                                                                           @RequestParam(name = "concluidos", required = false) Boolean concluidos) {
        PageResult<EmpresaResponseDto> response = listarEmpresasMenuRapidoUseCase.execute(page, size, nome, impedidos, concluidos);
        System.out.println("Entrei no menuRapido!");
        return ResponseEntity.status(200).body(response);
    }

    @Operation(
            summary = "Listar projetos por ID da empresa com menos informações para kpis",
            description = "Esse endpoint retorna os projetos de uma empresa específica, contendo apenas nome, progresso, comImpedimento, urlImagem, nomeDiretor, idEmpresa.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de projetos por empresa retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Lista de projetos por empresa não retornada")
    })
    @GetMapping("/kpis")
    public ResponseEntity<KpiEmpresaResponseDto> listarKpis() {
        KpiEmpresaResponseDto response = listarEmpresasKpisUseCase.execute();
        System.out.println("Entrei no kpis!");
        return ResponseEntity.status(200).body(response);
    }

    @Operation(summary = "Buscar empresa por ID",
            description = "Esse endpoint retorna os detalhes de uma empresa específica com base no ID fornecido",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "EmpresaEntity encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "EmpresaEntity não encontrada")
    })
    @GetMapping("/{idEmpresa}")
    public ResponseEntity<DashboardEmpresaResponseDto> buscarPorId(@PathVariable Integer idEmpresa) {
        DashboardEmpresaResponseDto dashboard = criarDashboardEmpresaUseCase.execute(idEmpresa);
        return ResponseEntity.status(200).body(dashboard);
    }

    @Operation(summary = "Deletar uma empresa",
            description = "Esse endpoint remove uma empresa do sistema com base no ID fornecido",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "EmpresaEntity deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "EmpresaEntity não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id, @RequestBody UsuarioPermissaoDto usuarioPermissaoDto) {
        deletarEmpresaUseCase.execute(id, usuarioPermissaoDto);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Atualizar dados de uma empresa",
            description = "Esse endpoint atualiza os dados de uma empresa existente",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "EmpresaEntity atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "EmpresaEntity não encontrada")
    })
    @PatchMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaResponseDto> atualizar(@PathVariable Integer idEmpresa, @Valid @RequestBody EmpresaAtualizarRequestDto request) {
        EmpresaResponseDto response = atualizarEmpresaUseCase.execute(idEmpresa, request);
        return ResponseEntity.status(200).body(response);
    }
}
