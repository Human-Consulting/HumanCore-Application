package com.humanconsulting.humancore_api.application.usecases.investimento;

import com.humanconsulting.humancore_api.domain.entities.Investimento;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemPermissaoException;
import com.humanconsulting.humancore_api.domain.repositories.InvestimentoRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.atualizar.investimento.AtualizarInvestimentoRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.web.mappers.InvestimentoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AtualizarInvestimentoUseCaseTest {

    private InvestimentoRepository investimentoRepository;
    private UsuarioRepository usuarioRepository;
    private BuscarInvestimentoPorIdUseCase buscarInvestimentoPorIdUseCase;
    private AtualizarInvestimentoUseCase useCase;

    @BeforeEach
    void setUp() {
        investimentoRepository = mock(InvestimentoRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        buscarInvestimentoPorIdUseCase = mock(BuscarInvestimentoPorIdUseCase.class);

        useCase = new AtualizarInvestimentoUseCase(
                investimentoRepository,
                usuarioRepository,
                buscarInvestimentoPorIdUseCase
        );
    }

    @Test
    void deveAtualizarInvestimentoComSucesso() {
        // Arrange
        Integer idInvestimento = 1;
        Integer idEditor = 101;

        AtualizarInvestimentoRequestDto requestDto = new AtualizarInvestimentoRequestDto();
        requestDto.setIdEditor(idEditor);
        requestDto.setPermissaoEditor("MODIFICAR_INVESTIMENTO");
        requestDto.setValor(5000.0);

        InvestimentoResponseDto investimentoResponse = new InvestimentoResponseDto();
        investimentoResponse.setIdInvestimento(idInvestimento);
        investimentoResponse.setProjeto(mock(Projeto.class));

        when(buscarInvestimentoPorIdUseCase.execute(idInvestimento)).thenReturn(investimentoResponse);

        Usuario usuarioEditor = new Usuario();
        usuarioEditor.setIdUsuario(idEditor);
        when(usuarioRepository.findById(idEditor)).thenReturn(Optional.of(usuarioEditor));

        Investimento investimentoAtualizado = new Investimento();
        investimentoAtualizado.setIdInvestimento(idInvestimento);
        investimentoAtualizado.setProjeto(mock(Projeto.class));
        investimentoAtualizado.setValor(5000.0);

        when(investimentoRepository.save(any(Investimento.class))).thenReturn(investimentoAtualizado);

        InvestimentoResponseDto expectedResponse = InvestimentoMapper.toDto(investimentoAtualizado);

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("MODIFICAR_INVESTIMENTO", "MODIFICAR_INVESTIMENTO"))
                    .thenAnswer(inv -> null);


            // Act
            InvestimentoResponseDto result = useCase.execute(idInvestimento, requestDto);

            // Assert
            assertNotNull(result);
            assertEquals(expectedResponse.getIdInvestimento(), result.getIdInvestimento());
            assertEquals(expectedResponse.getProjeto(), result.getProjeto());
            assertEquals(expectedResponse.getValor(), result.getValor());
            verify(usuarioRepository).findById(idEditor);
            verify(investimentoRepository).save(any(Investimento.class));
        }
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioEditorNaoEncontrado() {
        // Arrange
        Integer idInvestimento = 1;
        Integer idEditor = 999;

        AtualizarInvestimentoRequestDto requestDto = new AtualizarInvestimentoRequestDto();
        requestDto.setIdEditor(idEditor);
        requestDto.setPermissaoEditor("ADMIN");

        InvestimentoResponseDto investimentoResponse = new InvestimentoResponseDto();
        investimentoResponse.setIdInvestimento(idInvestimento);
        investimentoResponse.setProjeto(mock(Projeto.class));

        when(buscarInvestimentoPorIdUseCase.execute(idInvestimento)).thenReturn(investimentoResponse);
        when(usuarioRepository.findById(idEditor)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntidadeNaoEncontradaException.class,
                () -> useCase.execute(idInvestimento, requestDto));

        verify(usuarioRepository).findById(idEditor);
        verify(investimentoRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoQuandoPermissaoInvalida() {
        // Arrange
        Integer idInvestimento = 1;
        Integer idEditor = 101;

        AtualizarInvestimentoRequestDto requestDto = new AtualizarInvestimentoRequestDto();
        requestDto.setIdEditor(idEditor);
        requestDto.setPermissaoEditor("CONSULTOR"); // sem permissão

        InvestimentoResponseDto investimentoResponse = new InvestimentoResponseDto();
        investimentoResponse.setIdInvestimento(idInvestimento);
        investimentoResponse.setProjeto(mock(Projeto.class));

        when(buscarInvestimentoPorIdUseCase.execute(idInvestimento)).thenReturn(investimentoResponse);

        Usuario usuarioEditor = new Usuario();
        usuarioEditor.setIdUsuario(idEditor);
        when(usuarioRepository.findById(idEditor)).thenReturn(Optional.of(usuarioEditor));

        assertThrows(EntidadeSemPermissaoException.class,
                () -> useCase.execute(idInvestimento, requestDto));
    }
}
