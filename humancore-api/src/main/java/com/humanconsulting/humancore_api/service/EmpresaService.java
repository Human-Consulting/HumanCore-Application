package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.model.Empresa;
import com.humanconsulting.humancore_api.repository.EmpresaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository repository;

    public Empresa cadastrar(Empresa empresa) {
        repository.existsByCnpj(empresa.getCnpj());
        empresa.setIdEmpresa(null);
        return repository.insert(empresa);
    }

    public Empresa buscarPorId(Integer id) {
        return repository.selectWhereId(id);
    }

    public List<Empresa> listar() {
        List<Empresa> all = repository.selectAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma empresa registrada");
        return all;
    }

    public void deletar(Integer id) {
        repository.deleteWhere(id);
    }

    public Empresa atualizar(Integer idEmpresa, @Valid Empresa empresa) {
        Empresa empresaAtualizada = repository.selectWhereId(idEmpresa);

        if((empresaAtualizada != null) && (empresaAtualizada.getIdEmpresa() == idEmpresa)) {
            empresaAtualizada.setIdEmpresa(idEmpresa);

            repository.insert(empresa);

            return empresa;
        }

        throw new EntidadeSemRetornoException("Empresa não encontrada");
    }
}
