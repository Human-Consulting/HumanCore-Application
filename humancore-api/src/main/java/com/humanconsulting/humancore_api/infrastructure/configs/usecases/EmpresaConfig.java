package com.humanconsulting.humancore_api.infrastructure.configs.usecases;

import com.humanconsulting.humancore_api.application.usecases.empresa.*;
import com.humanconsulting.humancore_api.application.usecases.empresa.mappers.DashboardEmpresaMapper;
import com.humanconsulting.humancore_api.application.usecases.mensagem.CadastrarMensagemInfoUseCase;
import com.humanconsulting.humancore_api.domain.repositories.*;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.application.usecases.empresa.mappers.EmpresaResponseMapper;
import com.humanconsulting.humancore_api.infrastructure.notifiers.SalaNotifierAdapter;
import com.humanconsulting.humancore_api.infrastructure.repositories.adapters.DashboardEmpresaRepositoryAdapter;
import com.humanconsulting.humancore_api.infrastructure.repositories.adapters.EmpresaRepositoryAdapter;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaDashboardEmpresaRepository;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaEmpresaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmpresaConfig {
    @Bean
    public CadastrarEmpresaUseCase cadastrarEmpresaUseCase(
            EmpresaRepository empresaRepository,
            UsuarioRepository usuarioRepository,
            SalaNotifier salaNotifier,
            EmpresaResponseMapper empresaResponseMapper
    ) {
        return new CadastrarEmpresaUseCase(
                empresaRepository,
                usuarioRepository,
                salaNotifier,
                empresaResponseMapper
        );
    }

    @Bean
    public AtualizarEmpresaUseCase atualizarEmpresaUseCase(
            EmpresaRepository empresaRepository,
            UsuarioRepository usuarioRepository,
            EmpresaResponseMapper empresaResponseMapper
    ) {
        return new AtualizarEmpresaUseCase(empresaRepository, usuarioRepository, empresaResponseMapper);
    }

    @Bean
    public BuscarEmpresaPorIdUseCase buscarEmpresaPorIdUseCase(EmpresaRepository empresaRepository) {
        return new BuscarEmpresaPorIdUseCase(empresaRepository);
    }

    @Bean
    public CriarDashboardEmpresaUseCase criarDashboardEmpresaUseCase(
            UsuarioRepository usuarioRepository,
            com.humanconsulting.humancore_api.domain.repositories.DashboardEmpresaRepository dashRepository,
            com.humanconsulting.humancore_api.domain.repositories.CheckpointRepository checkpointRepository,
            ListarTarefasPorAreaUseCase listarTarefasPorAreaUseCase,
            ListarFinanceiroPorEmpresaUseCase listarFinanceiroPorEmpresaUseCase,
            com.humanconsulting.humancore_api.application.usecases.empresa.mappers.DashboardEmpresaMapper dashboardEmpresaMapper
    ) {
        return new CriarDashboardEmpresaUseCase(
                usuarioRepository,
                dashRepository,
                checkpointRepository,
                listarTarefasPorAreaUseCase,
                listarFinanceiroPorEmpresaUseCase,
                dashboardEmpresaMapper
        );
    }

    @Bean
    public EmpresaResponseMapper empresaResponseMapper(
            UsuarioRepository usuarioRepository,
            DashboardEmpresaRepository dashRepository,
            CheckpointRepository checkpointRepository
    ) {
        return new EmpresaResponseMapper(
                usuarioRepository,
                dashRepository,
                checkpointRepository
        );
    }

    @Bean
    public DeletarEmpresaUseCase deletarEmpresaUseCase(EmpresaRepository empresaRepository) {
        return new DeletarEmpresaUseCase(empresaRepository);
    }

    @Bean
    public ListarEmpresasUseCase listarEmpresasUseCase(EmpresaRepository empresaRepository, EmpresaResponseMapper empresaResponseMapper) {
        return new ListarEmpresasUseCase(empresaRepository, empresaResponseMapper);
    }

    @Bean
    public ListarFinanceiroPorEmpresaUseCase listarFinanceiroPorEmpresaUseCase(DashboardEmpresaRepository dashRepository) {
        return new ListarFinanceiroPorEmpresaUseCase(dashRepository);
    }

    @Bean
    public ListarTarefasPorAreaUseCase listarTarefasPorAreaEmpresaUseCase(DashboardEmpresaRepository dashRepository) {
        return new ListarTarefasPorAreaUseCase(dashRepository);
    }

    @Bean
    public DashboardEmpresaMapper dashboardEmpresaMapper() {
        return new DashboardEmpresaMapper();
    }

    @Bean
    public EmpresaRepository empresaRepository(JpaEmpresaRepository jpaEmpresaRepository) {
        return new EmpresaRepositoryAdapter(jpaEmpresaRepository);
    }

    @Bean
    public DashboardEmpresaRepository dashboardEmpresaRepository(JpaDashboardEmpresaRepository jpaDashboardEmpresaRepository) {
        return new DashboardEmpresaRepositoryAdapter(jpaDashboardEmpresaRepository);
    }

    @Bean
    public SalaNotifier salaNotifier(
            SalaRepository salaRepository,
            CadastrarMensagemInfoUseCase cadastrarMensagemInfoUseCase
    ) {
        return new SalaNotifierAdapter(salaRepository, cadastrarMensagemInfoUseCase) {
        };
    }
}
