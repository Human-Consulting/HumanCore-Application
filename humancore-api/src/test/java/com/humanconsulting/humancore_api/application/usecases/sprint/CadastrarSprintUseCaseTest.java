package com.humanconsulting.humancore_api.application.usecases.sprint;

import com.humanconsulting.humancore_api.application.usecases.sprint.mappers.SprintResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.domain.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.domain.repositories.SprintRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.request.SprintRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CadastrarSprintUseCaseTest {

    private ProjetoRepository projetoRepository;
    private SprintRepository sprintRepository;
    private SprintResponseMapper sprintResponseMapper;
    private CadastrarSprintUseCase cadastrarSprintUseCase;

    @BeforeEach
    void setUp() {
        projetoRepository = mock(ProjetoRepository.class);
        sprintRepository = mock(SprintRepository.class);
        sprintResponseMapper = mock(SprintResponseMapper.class);
        cadastrarSprintUseCase = new CadastrarSprintUseCase(projetoRepository, sprintRepository, sprintResponseMapper);
    }

    @Test
    void execute_ShouldCadastrarSprint_WhenDadosValidos() {
        // Arrange
        SprintRequestDto request = new SprintRequestDto();
        request.setFkProjeto(1);
        request.setPermissaoEditor("ADICIONAR_SPRINT");
        request.setDtInicio(LocalDate.of(2025, 1, 1));
        request.setDtFim(LocalDate.of(2025, 1, 10));

        Projeto projeto = new Projeto();
        projeto.setIdProjeto(1);

        Sprint sprintSalva = new Sprint();
        sprintSalva.setIdSprint(100);

        SprintResponseDto responseDto = new SprintResponseDto();
        responseDto.setIdSprint(100);

        when(projetoRepository.findById(1)).thenReturn(Optional.of(projeto));
        when(sprintRepository.save(any(Sprint.class))).thenReturn(sprintSalva);
        when(sprintResponseMapper.toResponse(sprintSalva)).thenReturn(responseDto);

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("ADICIONAR_SPRINT", "ADICIONAR_SPRINT"))
                    .thenAnswer(inv -> null);

            // Act
            SprintResponseDto result = cadastrarSprintUseCase.execute(request);

            // Assert
            assertNotNull(result);
            assertEquals(100, result.getIdSprint());
            verify(sprintRepository, times(1)).save(any(Sprint.class));
            verify(sprintResponseMapper, times(1)).toResponse(sprintSalva);
        }
    }

    @Test
    void execute_ShouldThrowException_WhenDatasInvalidas() {
        SprintRequestDto request = new SprintRequestDto();
        request.setFkProjeto(1);
        request.setPermissaoEditor("ADICIONAR_SPRINT");
        request.setDtInicio(LocalDate.of(2025, 1, 10));
        request.setDtFim(LocalDate.of(2025, 1, 10));

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("ADICIONAR_SPRINT", "ADICIONAR_SPRINT"))
                    .thenAnswer(inv -> null);

            assertThrows(EntidadeConflitanteException.class,
                    () -> cadastrarSprintUseCase.execute(request));
        }
    }

    @Test
    void execute_ShouldThrowException_WhenPermissaoInvalida() {
        SprintRequestDto request = new SprintRequestDto();
        request.setFkProjeto(1);
        request.setPermissaoEditor("SEM_PERMISSAO");
        request.setDtInicio(LocalDate.of(2025, 1, 1));
        request.setDtFim(LocalDate.of(2025, 1, 10));

        Projeto projeto = new Projeto();
        projeto.setIdProjeto(1);

        when(projetoRepository.findById(1)).thenReturn(Optional.of(projeto));

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("SEM_PERMISSAO", "ADICIONAR_SPRINT"))
                    .thenThrow(new SecurityException("Permissão inválida"));

            assertThrows(SecurityException.class,
                    () -> cadastrarSprintUseCase.execute(request));

            verify(sprintRepository, never()).save(any());
        }
    }

    @Test
    void execute_ShouldThrowException_WhenProjetoNotFound() {
        SprintRequestDto request = new SprintRequestDto();
        request.setFkProjeto(99);
        request.setPermissaoEditor("ADICIONAR_SPRINT");
        request.setDtInicio(LocalDate.of(2025, 1, 1));
        request.setDtFim(LocalDate.of(2025, 1, 10));

        when(projetoRepository.findById(99)).thenReturn(Optional.empty());

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("ADICIONAR_SPRINT", "ADICIONAR_SPRINT"))
                    .thenAnswer(inv -> null);

            assertThrows(java.util.NoSuchElementException.class,
                    () -> cadastrarSprintUseCase.execute(request));
        }
    }
}
