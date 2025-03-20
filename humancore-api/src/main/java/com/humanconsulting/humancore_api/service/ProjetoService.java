package com.humanconsulting.humancore_api.service;

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
}
