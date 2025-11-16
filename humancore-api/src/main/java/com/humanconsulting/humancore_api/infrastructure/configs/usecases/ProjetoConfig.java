package com.humanconsulting.humancore_api.infrastructure.configs.usecases;

import com.humanconsulting.humancore_api.application.usecases.projeto.*;
import com.humanconsulting.humancore_api.application.usecases.projeto.mappers.ProjetoResponseMapper;
import com.humanconsulting.humancore_api.application.usecases.sala.AtualizarSalaUseCase;
import com.humanconsulting.humancore_api.application.usecases.sprint.BuscarSprintPorIdUseCase;
import com.humanconsulting.humancore_api.application.usecases.sprint.BuscarSprintsPorProjetoUseCase;
import com.humanconsulting.humancore_api.application.usecases.sprint.CriarBurndownSprintUseCase;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.*;
import com.humanconsulting.humancore_api.infrastructure.repositories.adapters.DashboardProjetoRepositoryAdapter;
import com.humanconsulting.humancore_api.infrastructure.repositories.adapters.ProjetoRepositoryAdapter;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaDashboardProjetoRepository;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaProjetoRepository;
import com.humanconsulting.humancore_api.web.mappers.ProjetoMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjetoConfig {
    @Bean
    public AtualizarProjetoUseCase atualizarProjetoUseCase(
            ProjetoRepository projetoRepository,
            UsuarioRepository usuarioRepository,
            SalaRepository salaRepository,
            BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase,
            ProjetoResponseMapper projetoResponseMapper,
            AtualizarSalaUseCase atualizarSalaUseCase
    ) {
        return new AtualizarProjetoUseCase(
                projetoRepository,
                usuarioRepository,
                salaRepository,
                buscarProjetoPorIdUseCase,
                projetoResponseMapper,
                atualizarSalaUseCase
        );
    }

    @Bean
    public BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase(ProjetoRepository projetoRepository) {
        return new BuscarProjetoPorIdUseCase(projetoRepository);
    }

    @Bean
    public ListarProjetosPorEmpresaUseCase buscarProjetosPorEmpresaUseCase(ProjetoRepository projetoRepository, ProjetoResponseMapper projetoResponseMapper) {
        return new ListarProjetosPorEmpresaUseCase(projetoRepository, projetoResponseMapper);
    }

    @Bean
    public ListarProjetosMenuRapidoUseCase buscarProjetosMenuRapidoUseCase(ProjetoRepository projetoRepository, ProjetoResponseMapper projetoResponseMapper) {
        return new ListarProjetosMenuRapidoUseCase(projetoRepository, projetoResponseMapper);
    }

    @Bean
    public ListarProjetosKpisUseCase buscarProjetosKpisUseCase(ProjetoRepository projetoRepository, ProjetoResponseMapper projetoResponseMapper, ProjetoMapper projetoMapper) {
        return new ListarProjetosKpisUseCase(projetoRepository, projetoResponseMapper, projetoMapper);
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
    public CriarDashboardProjetoUseCase criarDashboardProjetoUseCase(ProjetoResponseMapper projetoResponseMapper, BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase) {
        return new CriarDashboardProjetoUseCase(projetoResponseMapper, buscarProjetoPorIdUseCase);
    }

    @Bean
    public CriarBurndownProjetoUseCase criarBurndownProjetoUseCase(CriarBurndownSprintUseCase criarBurndownSprintUseCase, BuscarSprintsPorProjetoUseCase buscarSprintsPorProjetoUseCase) {
        return new CriarBurndownProjetoUseCase(criarBurndownSprintUseCase, buscarSprintsPorProjetoUseCase);
    }

    @Bean
    public CriarBurndownSprintUseCase criarBurndownSprintUseCase(BuscarSprintPorIdUseCase buscarSprintPorIdUseCase) {
        return new CriarBurndownSprintUseCase(buscarSprintPorIdUseCase);
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
            UsuarioRepository usuarioRepository,
            ListarTarefasPorAreaUseCase listarTarefasPorAreaUseCase,
            ListarTarefasPorProjetoUsuarioUseCase listarTarefasPorProjetoUsuarioUseCase,
            DashboardProjetoRepository dashboardProjetoRepository,
            ListarFinanceiroPorProjetoUseCase listarFinanceiroPorProjetoUseCase
    ) {
        return new ProjetoResponseMapper(
                tarefaRepository,
                checkpointRepository,
                usuarioRepository,
                listarTarefasPorAreaUseCase,
                dashboardProjetoRepository,
                listarFinanceiroPorProjetoUseCase,
                listarTarefasPorProjetoUsuarioUseCase
        );
    }

    @Bean
    public ProjetoMapper projetoMapper() {
        return new ProjetoMapper();
    }

    @Bean
    public ListarTarefasPorAreaUseCase listarTarefasPorAreaProjetoUseCase(DashboardProjetoRepository dashboardProjetoRepository) {
        return new ListarTarefasPorAreaUseCase(dashboardProjetoRepository);
    }

    @Bean
    public ListarTarefasPorProjetoUsuarioUseCase listarTarefasPorProjetoUsuarioUseCase(DashboardProjetoRepository dashboardProjetoRepository) {
        return new ListarTarefasPorProjetoUsuarioUseCase(dashboardProjetoRepository);
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
