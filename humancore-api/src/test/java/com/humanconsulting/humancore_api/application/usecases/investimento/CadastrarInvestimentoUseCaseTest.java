package com.humanconsulting.humancore_api.application.usecases.investimento;

import com.humanconsulting.humancore_api.domain.entities.Investimento;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.repositories.InvestimentoRepository;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.request.InvestimentoRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.investimento.InvestimentoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CadastrarInvestimentoUseCaseTest {

    private InvestimentoRepository investimentoRepository;
    private ProjetoRepository projetoRepository;
    private CadastrarInvestimentoUseCase useCase;

    @BeforeEach
    void setUp() {
        investimentoRepository = mock(InvestimentoRepository.class);
        projetoRepository = mock(ProjetoRepository.class);
        useCase = new CadastrarInvestimentoUseCase(investimentoRepository, projetoRepository);
    }

    @Test
    void deveCadastrarInvestimentoComSucesso() {
        // Arrange
        InvestimentoRequestDto requestDto = new InvestimentoRequestDto();
        requestDto.setFkProjeto(1);
        requestDto.setPermissaoEditor("ADICIONAR_INVESTIMENTO");
        requestDto.setValor(1000.0);

        Projeto projeto = mock(Projeto.class);

        Investimento investimento = new Investimento();
        investimento.setIdInvestimento(10);
        investimento.setProjeto(projeto);
        investimento.setValor(1000.0);

        when(projetoRepository.findById(1)).thenReturn(Optional.of(projeto));
        when(investimentoRepository.save(any(Investimento.class))).thenReturn(investimento);

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("ADICIONAR_INVESTIMENTO", "ADICIONAR_INVESTIMENTO"))
                    .thenAnswer(inv -> null);

            // Act
            InvestimentoResponseDto result = useCase.execute(requestDto);

            // Assert
            assertNotNull(result);
            assertEquals(10, result.getIdInvestimento());
            assertEquals(1000.0, result.getValor());
            verify(projetoRepository).findById(1);
            verify(investimentoRepository).save(any(Investimento.class));
        }
    }

    @Test
    void deveLancarExcecaoQuandoPermissaoInvalida() {
        // Arrange
        InvestimentoRequestDto requestDto = new InvestimentoRequestDto();
        requestDto.setFkProjeto(1);
        requestDto.setPermissaoEditor("CONSULTOR"); // sem permissão

        Projeto projeto = new Projeto();
        projeto.setIdProjeto(1);

        when(projetoRepository.findById(1)).thenReturn(Optional.of(projeto));

        // Act & Assert
        assertThrows(NullPointerException.class, () -> useCase.execute(requestDto));
    }

    @Test
    void deveCadastrarInvestimentoMesmoSemProjetoEncontrado() {
        // Arrange
        InvestimentoRequestDto requestDto = new InvestimentoRequestDto();
        requestDto.setFkProjeto(99);
        requestDto.setPermissaoEditor("ADMIN");
        requestDto.setValor(2000.0);

        when(projetoRepository.findById(99)).thenReturn(Optional.empty());

        Investimento investimento = new Investimento();
        investimento.setIdInvestimento(20);
        investimento.setProjeto(null);
        investimento.setValor(2000.0);

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("ADICIONAR_INVESTIMENTO", "ADICIONAR_INVESTIMENTO"))
                    .thenAnswer(inv -> null);

            when(investimentoRepository.save(any(Investimento.class))).thenReturn(investimento);

            // Act
            InvestimentoResponseDto result = useCase.execute(requestDto);

            // Assert
            assertNotNull(result);
            assertEquals(20, result.getIdInvestimento());
            assertNull(investimento.getProjeto());
            assertEquals(2000.0, result.getValor());
            verify(projetoRepository).findById(99);
            verify(investimentoRepository).save(any(Investimento.class));
        }
    }
}
