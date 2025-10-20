package com.humanconsulting.humancore_api.infrastructure.configs.usecases;

import com.humanconsulting.humancore_api.application.usecases.projeto.*;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.*;
import com.humanconsulting.humancore_api.application.usecases.projeto.mappers.ProjetoResponseMapper;
import com.humanconsulting.humancore_api.infrastructure.repositories.adapters.DashboardEmpresaRepositoryAdapter;
import com.humanconsulting.humancore_api.infrastructure.repositories.adapters.DashboardProjetoRepositoryAdapter;
import com.humanconsulting.humancore_api.infrastructure.repositories.adapters.ProjetoRepositoryAdapter;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaDashboardEmpresaRepository;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaDashboardProjetoRepository;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaProjetoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjetoConfig {
    @Bean
    public AtualizarProjetoUseCase atualizarProjetoUseCase(
            ProjetoRepository projetoRepository,
            UsuarioRepository usuarioRepository,
            BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase,
            ProjetoResponseMapper projetoResponseMapper
    ) {
        return new AtualizarProjetoUseCase(
                projetoRepository,
                usuarioRepository,
                buscarProjetoPorIdUseCase,
                projetoResponseMapper
        );
    }

    @Bean
    public BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase(ProjetoRepository projetoRepository) {
        return new BuscarProjetoPorIdUseCase(projetoRepository);
    }

    @Bean
    public BuscarProjetosPorEmpresaUseCase buscarProjetosPorEmpresaUseCase(ProjetoRepository projetoRepository, ProjetoResponseMapper projetoResponseMapper) {
        return new BuscarProjetosPorEmpresaUseCase(projetoRepository, projetoResponseMapper);
    }

    @Bean
    public CadastrarProjetoUseCase cadastrarProjetoUseCase(
            ProjetoRepository projetoRepository,
            EmpresaRepository empresaRepository,
            UsuarioRepository usuarioRepository,
            SalaNotifier salaNotifier,
            ProjetoResponseMapper projetoResponseMapper
    ) {
        return new CadastrarProjetoUseCase(
                projetoRepository,
                empresaRepository,
                usuarioRepository,
                salaNotifier,
                projetoResponseMapper
        );
    }

    @Bean
    public CriarDashboardProjetoUseCase criarDashboardProjetoUseCase(
            UsuarioRepository usuarioRepository,
            DashboardProjetoRepository dashboardProjetoRepository,
            CheckpointRepository checkpointRepository,
            ListarTarefasPorAreaUseCase listarTarefasPorAreaUseCase,
            ListarFinanceiroPorProjetoUseCase listarFinanceiroPorProjetoUseCase
    ) {
        return new CriarDashboardProjetoUseCase(
                usuarioRepository,
                dashboardProjetoRepository,
                checkpointRepository,
                listarTarefasPorAreaUseCase,
                listarFinanceiroPorProjetoUseCase
        );
    }

    @Bean
    public DeletarProjetoUseCase deletarProjetoUseCase(ProjetoRepository projetoRepository, BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase) {
        return new DeletarProjetoUseCase(projetoRepository, buscarProjetoPorIdUseCase);
    }

    @Bean
    public ListarFinanceiroPorProjetoUseCase listarFinanceiroPorProjetoUseCase(DashboardProjetoRepository dashboardProjetoRepository) {
        return new ListarFinanceiroPorProjetoUseCase(dashboardProjetoRepository);
    }

    @Bean
    public ListarProjetosUseCase listarProjetosUseCase(ProjetoRepository projetoRepository, ProjetoResponseMapper projetoResponseMapper) {
        return new ListarProjetosUseCase(projetoRepository, projetoResponseMapper);
    }

    @Bean
    public ProjetoResponseMapper projetoResponseMapper(
            TarefaRepository tarefaRepository,
            CheckpointRepository checkpointRepository,
            SprintRepository sprintRepository
    ) {
        return new ProjetoResponseMapper(
                tarefaRepository,
                checkpointRepository,
                sprintRepository
        );
    }

    @Bean
    public ListarTarefasPorAreaUseCase listarTarefasPorAreaProjetoUseCase(DashboardProjetoRepository dashboardProjetoRepository) {
        return new ListarTarefasPorAreaUseCase(dashboardProjetoRepository);
    }

    @Bean
    public DashboardProjetoRepository dashboardProjetoRepository(JpaDashboardProjetoRepository jpaDashboardProjetoRepository) {
        return new DashboardProjetoRepositoryAdapter(jpaDashboardProjetoRepository);
    }

    @Bean
    public ProjetoRepository projetoRepository(JpaProjetoRepository jpaProjetoRepository) {
        return new ProjetoRepositoryAdapter(jpaProjetoRepository);
    }
}
