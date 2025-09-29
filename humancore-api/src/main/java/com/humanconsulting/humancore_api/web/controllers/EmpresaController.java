package com.humanconsulting.humancore_api.web.controllers;

import com.humanconsulting.humancore_api.application.usecases.empresa.*;
import com.humanconsulting.humancore_api.web.dtos.atualizar.empresa.EmpresaAtualizarRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.EmpresaRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.DashboardEmpresaResponseDto;
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
@RequestMapping("empresas")
@CrossOrigin("*")
public class EmpresaController {

    @Autowired
    private CadastrarEmpresaUseCase cadastrarEmpresaUseCase;
    @Autowired
    private ListarEmpresasUseCase listarEmpresasUseCase;
    @Autowired
    private BuscarEmpresaPorIdUseCase buscarEmpresaPorIdUseCase;
    @Autowired
    private CriarDashboardEmpresaUseCase criarDashboardEmpresaUseCase;
    @Autowired
    private DeletarEmpresaUseCase deletarEmpresaUseCase;
    @Autowired
    private AtualizarEmpresaUseCase atualizarEmpresaUseCase;

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
    public ResponseEntity<List<EmpresaResponseDto>> listar() {
        List<EmpresaResponseDto> response = listarEmpresasUseCase.execute();
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
        var empresa = buscarEmpresaPorIdUseCase.execute(idEmpresa);
        DashboardEmpresaResponseDto dashboard = criarDashboardEmpresaUseCase.execute(empresa);
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
