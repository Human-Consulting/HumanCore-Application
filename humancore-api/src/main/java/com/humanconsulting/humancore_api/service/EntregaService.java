package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.entrega.AtualizarFinalizadaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.atualizar.entrega.AtualizarImpedimentoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.atualizar.entrega.AtualizarProgressoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.atualizar.entrega.EntregaAtualizarRequestDto;
import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeSemPermissaoException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.model.Entrega;
import com.humanconsulting.humancore_api.repository.EntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository repository;

    public Entrega cadastrar(Entrega entrega) {
        if (entrega.getDtInicio().isAfter(entrega.getDtFim()) || entrega.getDtInicio().isEqual(entrega.getDtFim())) throw new EntidadeConflitanteException("Datas de início e fim conflitantes.");
        entrega.setIdSprint(null);
        return repository.insert(entrega);
    }

    public Entrega buscarPorId(Integer id) {
        return repository.selectWhereId(id);
    }

    public List<Entrega> listar() {
        List<Entrega> all = repository.selectAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma entrega registrada");
        return all;
    }

    public void deletar(Integer id) {
        repository.deleteWhere(id);
    }

    public Entrega atualizar(Integer idEntrega, EntregaAtualizarRequestDto entrega) {
        Boolean temPermissao = repository.validarPermissao(entrega.getIdEditor(), entrega.getPermissaoEditor());

        if (!temPermissao) throw new EntidadeSemPermissaoException("Você não tem permissão para fazer essa edição");

        Entrega entregaAtualizada = repository.selectWhereId(idEntrega);

        if((entregaAtualizada != null) && (entregaAtualizada.getIdSprint() == idEntrega)) {
            entregaAtualizada.setIdSprint(idEntrega);

            Entrega e = new Entrega(entrega.getDescricao(), entrega.getDtInicio(), entrega.getDtFim(), entrega.getFkResponsavel());

            repository.insert(e);

            return e;
        }

        throw new EntidadeSemRetornoException("Entrega não encontrada");
    }

    public Entrega atualizarFinalizada(Integer id, AtualizarFinalizadaRequestDto request) {
        Boolean temPermissao = repository.validarPermissao(request.getIdEditor(), request.getPermissaoEditor());
        if (!temPermissao) throw new EntidadeSemPermissaoException("Você não tem permissão para fazer essa edição");

        Entrega entregaAtualizada = repository.selectWhereId(id);
        if(entregaAtualizada != null && (entregaAtualizada.getIdSprint() == id)) {
            entregaAtualizada.setFinalizada(request.getNovoFinalizada());

            repository.insert(entregaAtualizada);

            return entregaAtualizada;
        }

        throw new EntidadeSemRetornoException("Entrega não encontrada");
    }

    public Entrega atualizarImpedimento(Integer id, AtualizarImpedimentoRequestDto request) {
        Boolean temPermissao = repository.validarPermissao(request.getIdEditor(), request.getPermissaoEditor());
        if (!temPermissao) throw new EntidadeSemPermissaoException("Você não tem permissão para fazer essa edição");

        Entrega entregaAtualizada = repository.selectWhereId(id);

        if(entregaAtualizada != null && (entregaAtualizada.getIdSprint() == id)) {
            entregaAtualizada.setComImpedimento(request.getNovoImpedimento());

            repository.insert(entregaAtualizada);

            return entregaAtualizada;
        }

        throw new EntidadeSemRetornoException("Entrega não encontrada");
    }

    public Entrega atualizarProgresso(Integer id, AtualizarProgressoRequestDto request) {
        Boolean temPermissao = repository.validarPermissao(request.getIdEditor(), request.getPermissaoEditor());
        if (!temPermissao) throw new EntidadeSemPermissaoException("Você não tem permissão para fazer essa edição");

        Entrega entregaAtualizada = repository.selectWhereId(id);

        if(entregaAtualizada != null && (entregaAtualizada.getIdSprint() == id)) {
            entregaAtualizada.setProgresso(request.getNovoProgresso());

            repository.insert(entregaAtualizada);

            return entregaAtualizada;
        }

        throw new EntidadeSemRetornoException("Entrega não encontrada");
    }
}
