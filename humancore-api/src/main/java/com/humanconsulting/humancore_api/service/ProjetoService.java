package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.ProjetoAtualizarRequestDto;
import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
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

    public Projeto atualizar(Integer idProjeto, ProjetoAtualizarRequestDto projeto) {
        Projeto projetoAtualizado = repository.selectWhereId(idProjeto);

        if((projetoAtualizado != null) && (projetoAtualizado.getIdProjeto() == idProjeto)) {
            projetoAtualizado.setIdProjeto(idProjeto);

            Projeto p = new Projeto(projeto.getDescricao(), projeto.getNome(), projeto.getOrcamento(), projeto.getFkResponsavel());

            repository.insert(p);

            return p;
        }

        throw new EntidadeSemRetornoException("Projeto não encontrado");
    }

    public Projeto atualizarProgresso(Integer id, Double progresso) {
        Projeto projetoAtualizado = repository.selectWhereId(id);

        if(projetoAtualizado != null && (projetoAtualizado.getIdProjeto() == id)) {
            projetoAtualizado.setProgresso(progresso);

            repository.insert(projetoAtualizado);

            return projetoAtualizado;
        }

        throw new EntidadeSemRetornoException("Projeto não encontrado");
    }

    public Projeto atualizarImpedimento(Integer id, Boolean impedimento) {
        Projeto projetoAtualizado = repository.selectWhereId(id);

        if(projetoAtualizado != null && (projetoAtualizado.getIdProjeto() == id)) {
            projetoAtualizado.setCom_impedimento(impedimento);

            repository.insert(projetoAtualizado);

            return projetoAtualizado;
        }

        throw new EntidadeSemRetornoException("Projeto não encontrado");
    }
}
