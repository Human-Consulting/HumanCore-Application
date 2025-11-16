package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.application.usecases.projeto.mappers.ProjetoResponseMapper;
import com.humanconsulting.humancore_api.application.usecases.sala.AtualizarSalaUseCase;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.atualizar.projeto.ProjetoAtualizarRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.SalaRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseDto;
import com.humanconsulting.humancore_api.web.mappers.ProjetoMapper;

import java.util.List;

public class AtualizarProjetoUseCase {
    private final ProjetoRepository projetoRepository;
    private final UsuarioRepository usuarioRepository;
    private final SalaRepository salaRepository;
    private final BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase;
    private final ProjetoResponseMapper projetoResponseMapper;
    private final AtualizarSalaUseCase atualizarSalaUseCase;

    public AtualizarProjetoUseCase(ProjetoRepository projetoRepository, UsuarioRepository usuarioRepository, SalaRepository salaRepository, BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase, ProjetoResponseMapper projetoResponseMapper, AtualizarSalaUseCase atualizarSalaUseCase) {
        this.projetoRepository = projetoRepository;
        this.usuarioRepository = usuarioRepository;
        this.salaRepository = salaRepository;
        this.buscarProjetoPorIdUseCase = buscarProjetoPorIdUseCase;
        this.projetoResponseMapper = projetoResponseMapper;
        this.atualizarSalaUseCase = atualizarSalaUseCase;
    }

    public ProjetoResponseDto execute(Integer idProjeto, ProjetoAtualizarRequestDto projetoAtualizarRequestDto) {
        Projeto projeto = buscarProjetoPorIdUseCase.execute(idProjeto);
        String urlImagemOriginal = projetoRepository.findUrlImagemById(idProjeto);
        if (projetoAtualizarRequestDto.getUrlImagem().isEmpty())
            projetoAtualizarRequestDto.setUrlImagem(urlImagemOriginal);
        usuarioRepository.findById(projetoAtualizarRequestDto.getIdEditor()).orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado."));
        ValidarPermissao.execute(projetoAtualizarRequestDto.getPermissaoEditor(), "MODIFICAR_PROJETO");
        Usuario usuarioResponsavel = usuarioRepository.findById(projetoAtualizarRequestDto.getFkResponsavel()).get();
        Projeto projetoAtualizado = projetoRepository.save(ProjetoMapper.toEntity(projetoAtualizarRequestDto, idProjeto, usuarioResponsavel, projeto.getEmpresa()));
        Sala salaVinculada = salaRepository.findByProjeto(projeto);
        if (salaVinculada != null) {
            List<Integer> usuarios = salaVinculada.getUsuarios()
                    .stream()
                    .map(usuario -> usuario.getIdUsuario())
                    .toList();
            atualizarSalaUseCase.execute(salaVinculada.getIdSala(), new SalaRequestDto(projetoAtualizado.getTitulo(), projetoAtualizado.getUrlImagem(), projetoAtualizado.getEmpresa().getIdEmpresa(), idProjeto, usuarios, projetoAtualizarRequestDto.getIdEditor()));
        }
        return projetoResponseMapper.toResponse(projetoAtualizado);
    }
}

