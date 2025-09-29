package com.humanconsulting.humancore_api.web.controllers;

import com.humanconsulting.humancore_api.application.usecases.investimento.CadastrarInvestimentoUseCase;
import com.humanconsulting.humancore_api.application.usecases.investimento.ListarInvestimentosUseCase;
import com.humanconsulting.humancore_api.application.usecases.investimento.ListarInvestimentosPorProjetoUseCase;
import com.humanconsulting.humancore_api.application.usecases.investimento.BuscarInvestimentoPorIdUseCase;
import com.humanconsulting.humancore_api.application.usecases.investimento.AtualizarInvestimentoUseCase;
import com.humanconsulting.humancore_api.application.usecases.investimento.DeletarInvestimentoUseCase;
import com.humanconsulting.humancore_api.web.dtos.atualizar.investimento.AtualizarInvestimentoRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.InvestimentoRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.web.dtos.response.investimento.InvestimentoResponseDto;
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
    private CadastrarInvestimentoUseCase cadastrarInvestimentoUseCase;
    @Autowired
    private ListarInvestimentosUseCase listarInvestimentosUseCase;
    @Autowired
    private ListarInvestimentosPorProjetoUseCase listarInvestimentosPorProjetoUseCase;
    @Autowired
    private BuscarInvestimentoPorIdUseCase buscarInvestimentoPorIdUseCase;
    @Autowired
    private AtualizarInvestimentoUseCase atualizarInvestimentoUseCase;
    @Autowired
    private DeletarInvestimentoUseCase deletarInvestimentoUseCase;

    @Operation(
            summary = "Cadastrar um novo financeiro",
            description = "Esse endpoint cria um novo registro financeiro.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "InvestimentoEntity cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para o cadastro")
    })
    @PostMapping
    public ResponseEntity<InvestimentoResponseDto> cadastrarFinanceiro(@Valid @RequestBody InvestimentoRequestDto financeiroRequestDto) {
        InvestimentoResponseDto response = cadastrarInvestimentoUseCase.execute(financeiroRequestDto);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(
            summary = "Listar todos os registros financeiros",
            description = "Esse endpoint retorna todos os registros financeiros cadastrados.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponse(responseCode = "200", description = "Lista de investimentos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<InvestimentoResponseDto>> listar() {
        List<InvestimentoResponseDto> response = listarInvestimentosUseCase.execute();
        return ResponseEntity.status(200).body(response);
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
        List<InvestimentoResponseDto> response = listarInvestimentosPorProjetoUseCase.execute(idProjeto);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(
            summary = "Buscar registro financeiro por ID",
            description = "Esse endpoint retorna os detalhes de um registro financeiro específico a partir do seu ID.",
            parameters = @Parameter(name = "id", description = "ID do registro financeiro."),
            security = @SecurityRequirement(name = "Bearer")
    )

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "InvestimentoEntity deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "InvestimentoEntity não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<InvestimentoResponseDto> buscarPorId(@PathVariable Integer id) {
        InvestimentoResponseDto response = buscarInvestimentoPorIdUseCase.execute(id);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(
            summary = "Atualizar um registro financeiro",
            description = "Esse endpoint atualiza um registro financeiro com base no ID.",
            parameters = @Parameter(name = "idFinanceiro", description = "ID do registro financeiro a ser atualizado."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "InvestimentoEntity atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "InvestimentoEntity não encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<InvestimentoResponseDto> atualizar(@PathVariable Integer id, @Valid @RequestBody AtualizarInvestimentoRequestDto requestDto) {
        InvestimentoResponseDto response = atualizarInvestimentoUseCase.execute(id, requestDto);
        return ResponseEntity.status(200).body(response);
    }

    @Operation(
            summary = "Deletar um registro financeiro",
            description = "Esse endpoint remove um registro financeiro com base no ID.",
            parameters = @Parameter(name = "id", description = "ID do registro financeiro a ser deletado."),
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "InvestimentoEntity deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "InvestimentoEntity não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id, @RequestBody UsuarioPermissaoDto usuarioPermissaoDto) {
        deletarInvestimentoUseCase.execute(id, usuarioPermissaoDto);
        return ResponseEntity.status(204).build();
    }
}
