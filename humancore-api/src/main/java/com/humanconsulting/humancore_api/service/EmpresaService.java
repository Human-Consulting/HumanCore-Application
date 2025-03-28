package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.empresa.EmpresaAtualizarRequestDto;
import com.humanconsulting.humancore_api.exception.EntidadeSemPermissaoException;
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

    public Empresa atualizar(Integer idEmpresa, @Valid EmpresaAtualizarRequestDto empresa) {
        Empresa empresaAtualizada = repository.selectWhereId(idEmpresa);

        Boolean temPermissao = repository.validarPermissao(empresa.getIdEditor(), empresa.getPermissaoEditor());

        if (!temPermissao) throw new EntidadeSemPermissaoException("Você não tem permissão para fazer essa edição");

        if((empresaAtualizada != null) && (empresaAtualizada.getIdEmpresa() == idEmpresa)) {
            empresaAtualizada.setIdEmpresa(idEmpresa);

            Empresa e = new Empresa(idEmpresa, empresa.getCnpj(), empresa.getNome());

            repository.insert(e);

            return e;
        }

        throw new EntidadeSemRetornoException("Empresa não encontrada");
    }
}
