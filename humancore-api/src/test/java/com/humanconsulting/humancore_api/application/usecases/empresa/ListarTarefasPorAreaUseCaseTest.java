package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.domain.repositories.DashboardEmpresaRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ListarTarefasPorAreaUseCaseTest {
    @Mock
    private DashboardEmpresaRepository dashRepository;

    @InjectMocks
    private ListarTarefasPorAreaUseCase useCase;

}