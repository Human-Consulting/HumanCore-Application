package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.velho.repository.InvestimentoRepository;
import com.humanconsulting.humancore_api.velho.repository.ProjetoRepository;
import com.humanconsulting.humancore_api.velho.repository.UsuarioRepository;
import com.humanconsulting.humancore_api.velho.service.InvestimentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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