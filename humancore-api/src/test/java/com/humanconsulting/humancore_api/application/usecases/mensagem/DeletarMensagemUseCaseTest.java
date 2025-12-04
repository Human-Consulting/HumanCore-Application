package com.humanconsulting.humancore_api.application.usecases.mensagem;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.humanconsulting.humancore_api.domain.repositories.MensagemRepository;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.web.dtos.response.mensagem.MensagemResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DeletarMensagemUseCaseTest {

    @Mock
    private MensagemRepository mensagemRepository;

    @Mock
    private BuscarMensagemPorIdUseCase buscarMensagemPorIdUseCase;

    @InjectMocks
    private DeletarMensagemUseCase deletarMensagemUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
    }

    @Test
    void testExecute_SuccessfulDeletion() {
        // Arrange
        Integer id = 1;
        UsuarioPermissaoDto usuarioPermissaoDto = new UsuarioPermissaoDto();

        // Configura o mock para não lançar exceção
        when(buscarMensagemPorIdUseCase.execute(id)).thenReturn(mock(MensagemResponseDto.class)
        );

        // Act
        deletarMensagemUseCase.execute(id, usuarioPermissaoDto);

        // Assert
        verify(buscarMensagemPorIdUseCase, times(1)).execute(id);
        verify(mensagemRepository, times(1)).deleteById(id);
    }

    @Test
    void testExecute_MessageNotFound() {
        // Arrange
        Integer id = 1;
        UsuarioPermissaoDto usuarioPermissaoDto = new UsuarioPermissaoDto();

        // Configura o mock para lançar exceção
        doThrow(new RuntimeException("Mensagem não encontrada")).when(buscarMensagemPorIdUseCase).execute(id);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> deletarMensagemUseCase.execute(id, usuarioPermissaoDto));
    }
}
