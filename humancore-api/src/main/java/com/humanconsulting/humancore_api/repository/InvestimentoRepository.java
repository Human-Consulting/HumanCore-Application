package com.humanconsulting.humancore_api.repository;

import com.humanconsulting.humancore_api.controller.dto.atualizar.investimento.AtualizarInvestimentoRequestDto;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeRequisicaoFalhaException;
import com.humanconsulting.humancore_api.model.Investimento;
import com.humanconsulting.humancore_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestimentoRepository extends JpaRepository<Investimento, Integer> {

    List<Investimento> findAllByProjeto_IdProjeto(Integer idProjeto);
}
