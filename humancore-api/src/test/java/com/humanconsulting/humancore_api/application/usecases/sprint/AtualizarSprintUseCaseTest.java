package com.humanconsulting.humancore_api.application.usecases.sprint;

import com.humanconsulting.humancore_api.application.usecases.sprint.mappers.SprintResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.SprintRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.atualizar.sprint.SprintAtualizarRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AtualizarSprintUseCaseTest {

    private SprintRepository sprintRepository;
    private UsuarioRepository usuarioRepository;
    private SprintResponseMapper sprintResponseMapper;
    private AtualizarSprintUseCase atualizarSprintUseCase;

    @BeforeEach
    void setUp() {
        sprintRepository = mock(SprintRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        sprintResponseMapper = mock(SprintResponseMapper.class);
        atualizarSprintUseCase = new AtualizarSprintUseCase(sprintRepository, usuarioRepository, sprintResponseMapper);
    }

    @Test
    void execute_ShouldUpdateSprint_WhenDadosValidos() {
        // Arrange
        Integer idSprint = 1;
        Sprint sprint = new Sprint();
        sprint.setIdSprint(idSprint);
        sprint.setProjeto(new Projeto());

        Usuario editor = new Usuario();
        editor.setIdUsuario(10);

        SprintAtualizarRequestDto request = new SprintAtualizarRequestDto();
        request.setIdEditor(10);
        request.setPermissaoEditor("MODIFICAR_SPRINT");

        Sprint sprintAtualizada = new Sprint();
        sprintAtualizada.setIdSprint(idSprint);

        SprintResponseDto responseDto = new SprintResponseDto();
        responseDto.setIdSprint(idSprint);

        when(sprintRepository.findById(idSprint)).thenReturn(Optional.of(sprint));
        when(usuarioRepository.findById(10)).thenReturn(Optional.of(editor));
        when(sprintRepository.save(any(Sprint.class))).thenReturn(sprintAtualizada);
        when(sprintResponseMapper.toResponse(sprintAtualizada)).thenReturn(responseDto);

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("MODIFICAR_SPRINT", "MODIFICAR_SPRINT"))
                    .thenAnswer(inv -> null);

            // Act
            SprintResponseDto result = atualizarSprintUseCase.execute(idSprint, request);

            // Assert
            assertNotNull(result);
            assertEquals(idSprint, result.getIdSprint());
            verify(sprintRepository, times(1)).save(any(Sprint.class));
            verify(sprintResponseMapper, times(1)).toResponse(sprintAtualizada);
        }
    }

    @Test
    void execute_ShouldThrowException_WhenSprintNotFound() {
        when(sprintRepository.findById(99)).thenReturn(Optional.empty());
        SprintAtualizarRequestDto request = new SprintAtualizarRequestDto();
        request.setIdEditor(10);
        request.setPermissaoEditor("MODIFICAR_SPRINT");

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> atualizarSprintUseCase.execute(99, request));
    }

    @Test
    void execute_ShouldThrowException_WhenUsuarioEditorNotFound() {
        Integer idSprint = 1;
        Sprint sprint = new Sprint();
        sprint.setIdSprint(idSprint);
        sprint.setProjeto(new Projeto());

        when(sprintRepository.findById(idSprint)).thenReturn(Optional.of(sprint));
        when(usuarioRepository.findById(10)).thenReturn(Optional.empty());

        SprintAtualizarRequestDto request = new SprintAtualizarRequestDto();
        request.setIdEditor(10);
        request.setPermissaoEditor("MODIFICAR_SPRINT");

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> atualizarSprintUseCase.execute(idSprint, request));
    }

    @Test
    void execute_ShouldThrowException_WhenPermissaoInvalida() {
        Integer idSprint = 1;
        Sprint sprint = new Sprint();
        sprint.setIdSprint(idSprint);
        sprint.setProjeto(new Projeto());

        Usuario editor = new Usuario();
        editor.setIdUsuario(10);

        when(sprintRepository.findById(idSprint)).thenReturn(Optional.of(sprint));
        when(usuarioRepository.findById(10)).thenReturn(Optional.of(editor));

        SprintAtualizarRequestDto request = new SprintAtualizarRequestDto();
        request.setIdEditor(10);
        request.setPermissaoEditor("SEM_PERMISSAO");

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("SEM_PERMISSAO", "MODIFICAR_SPRINT"))
                    .thenThrow(new SecurityException("Permissão inválida"));

            assertThrows(SecurityException.class,
                    () -> atualizarSprintUseCase.execute(idSprint, request));

            verify(sprintRepository, never()).save(any());
        }
    }
}
