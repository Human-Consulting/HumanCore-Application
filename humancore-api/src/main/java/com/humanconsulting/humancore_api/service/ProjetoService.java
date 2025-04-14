package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.projeto.ProjetoAtualizarRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.ProjetoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.ProjetoResponseDto;
import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.ProjetoMapper;
import com.humanconsulting.humancore_api.model.Projeto;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.repository.ProjetoRepository;
import com.humanconsulting.humancore_api.repository.EntregaRepository;
import com.humanconsulting.humancore_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjetoService {

    @Autowired private ProjetoRepository projetoRepository;

    @Autowired private UsuarioRepository usuarioRepository;

    @Autowired private EntregaRepository entregaRepository;

    public ProjetoResponseDto cadastrar(ProjetoRequestDto projetoRequestDto) {
        if (projetoRepository.existsByNome(projetoRequestDto.getFkEmpresa(), projetoRequestDto.getDescricao())) throw new EntidadeConflitanteException("Projeto já cadastrado");

        Projeto projeto = projetoRepository.insert(ProjetoMapper.toEntity(projetoRequestDto));
        return passarParaResponse(projeto, projeto.getFkResponsavel(), projeto.getIdProjeto());
    }

    public Projeto buscarPorId(Integer id) {
        return projetoRepository.selectWhereId(id);
    }

    public List<ProjetoResponseDto> listar() {
        List<Projeto> all = projetoRepository.selectAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma projeto registrada");

        List<ProjetoResponseDto> allResponse = new ArrayList<>();
        for (Projeto projeto : all) {
            allResponse.add(passarParaResponse(projeto, projeto.getFkResponsavel(), projeto.getIdProjeto()));
        }
        return allResponse;
    }

    public void deletar(Integer id) {
        projetoRepository.deleteWhere(id);
    }

    public ProjetoResponseDto atualizar(Integer idProjeto, ProjetoAtualizarRequestDto projetoAtualizarRequestDto) {
        String urlImagemOriginal = buscarPorId(idProjeto).getUrlImagem();
        if (projetoAtualizarRequestDto.getUrlImagem().isEmpty()) projetoAtualizarRequestDto.setUrlImagem(urlImagemOriginal);
        Projeto projetoAtualizado = projetoRepository.update(idProjeto, projetoAtualizarRequestDto);
        return passarParaResponse(projetoAtualizado, projetoAtualizado.getFkResponsavel(), projetoAtualizado.getIdProjeto());
    }

    public List<ProjetoResponseDto> buscarPorIdEmpresa(Integer idEmpresa) {
     List<Projeto> all = projetoRepository.selectWhereIdEmpresa(idEmpresa);
     if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhum projeto encontrado");
     List<ProjetoResponseDto> allResponse = new ArrayList<>();
        for (Projeto projeto : all) {
            allResponse.add(passarParaResponse(projeto, projeto.getFkResponsavel(), projeto.getIdProjeto()));
        }
        return allResponse;
    }

    public ProjetoResponseDto passarParaResponse(Projeto projeto, Integer fkResponsavel, Integer idProjeto) {
        Usuario usuario = usuarioRepository.selectWhereId(fkResponsavel);
        double progresso = entregaRepository.mediaProgressoProjeto(idProjeto);
        boolean comImpedimento = entregaRepository.projetoComImpedimento(idProjeto);
        return ProjetoMapper.toDto(projeto, usuario.getIdUsuario(), usuario.getNome(), progresso, comImpedimento);
    }
}
