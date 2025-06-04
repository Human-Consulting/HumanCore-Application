package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.repository.InvestimentoRepository;
import com.humanconsulting.humancore_api.repository.ProjetoRepository;
import com.humanconsulting.humancore_api.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class InvestimentoServiceTest {

    private InvestimentoService investimentoService;
    private InvestimentoRepository investimentoRepository;
    private ProjetoRepository projetoRepository;
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        investimentoRepository = mock(InvestimentoRepository.class);
        projetoRepository = mock(ProjetoRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        investimentoService = new InvestimentoService();
        investimentoService.investimentoRepository = investimentoRepository;
        investimentoService.projetoRepository = projetoRepository;
        investimentoService.usuarioRepository = usuarioRepository;
    }

    @Test
    void cadastrar() {
    }

    @Test
    void buscarPorId() {
    }

    @Test
    void listar() {
    }

    @Test
    void listarPorProjeto() {
    }

    @Test
    void deletar() {
    }

    @Test
    void atualizar() {
    }
}