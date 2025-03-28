package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.projeto.AtualizarFinalizadaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.atualizar.projeto.AtualizarProgressoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.atualizar.projeto.ProjetoAtualizarRequestDto;
import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeSemPermissaoException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.model.Projeto;
import com.humanconsulting.humancore_api.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository repository;

    public Projeto cadastrar(Projeto projeto) {
        List<Projeto> projetos = listar();

        for (Projeto projetoAtual : projetos) {
            if(projetoAtual.getNome().equals(projeto.getNome())) {
                throw new EntidadeConflitanteException("Já existe um projeto com esse nome!");
            }
        }

        projeto.setIdProjeto(null);

        return repository.insert(projeto);
    }

    public Projeto buscarPorId(Integer id) {
        return repository.selectWhereId(id);
    }

    public List<Projeto> listar() {
        List<Projeto> all = repository.selectAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma projeto registrada");
        return all;
    }

    public void deletar(Integer id) {
        repository.deleteWhere(id);
    }

    public Projeto atualizar(Integer idProjeto, ProjetoAtualizarRequestDto request) {
        Boolean temPermissao = repository.validarPermissao(request.getIdEditor(), request.getPermissaoEditor());
        if (!temPermissao) throw new EntidadeSemPermissaoException("Você não tem permissão para fazer essa edição");

        Projeto projetoAtualizado = repository.selectWhereId(idProjeto);

        if((projetoAtualizado != null) && (projetoAtualizado.getIdProjeto() == idProjeto)) {
            projetoAtualizado.setIdProjeto(idProjeto);

            Projeto p = new Projeto(request.getDescricao(), request.getNome(), request.getOrcamento(), request.getFkResponsavel());

            repository.insert(p);

            return p;
        }

        throw new EntidadeSemRetornoException("Projeto não encontrado");
    }

    public Projeto atualizarProgresso(Integer id, AtualizarProgressoRequestDto request) {
        Boolean temPermissao = repository.validarPermissao(request.getIdEditor(), request.getPermissaoEditor());
        if (!temPermissao) throw new EntidadeSemPermissaoException("Você não tem permissão para fazer essa edição");

        Projeto projetoAtualizado = repository.selectWhereId(id);

        if(projetoAtualizado != null && (projetoAtualizado.getIdProjeto() == id)) {
            projetoAtualizado.setProgresso(request.getNovoProgresso());

            repository.insert(projetoAtualizado);

            return projetoAtualizado;
        }

        throw new EntidadeSemRetornoException("Projeto não encontrado");
    }

    public Projeto atualizarImpedimento(Integer id, AtualizarFinalizadaRequestDto request) {
        Boolean temPermissao = repository.validarPermissao(request.getIdEditor(), request.getPermissaoEditor());
        if (!temPermissao) throw new EntidadeSemPermissaoException("Você não tem permissão para fazer essa edição");

        Projeto projetoAtualizado = repository.selectWhereId(id);

        if(projetoAtualizado != null && (projetoAtualizado.getIdProjeto() == id)) {
            projetoAtualizado.setCom_impedimento(request.getNovoFinalizada());

            repository.insert(projetoAtualizado);

            return projetoAtualizado;
        }

        throw new EntidadeSemRetornoException("Projeto não encontrado");
    }
}
