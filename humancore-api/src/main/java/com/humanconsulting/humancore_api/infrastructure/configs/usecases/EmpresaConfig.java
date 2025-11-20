package com.humanconsulting.humancore_api.infrastructure.configs.usecases;

import com.humanconsulting.humancore_api.application.usecases.empresa.*;
import com.humanconsulting.humancore_api.application.usecases.empresa.mappers.EmpresaResponseMapper;
import com.humanconsulting.humancore_api.application.usecases.mensagem.CadastrarMensagemInfoUseCase;
import com.humanconsulting.humancore_api.application.usecases.sala.AtualizarSalaUseCase;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.*;
import com.humanconsulting.humancore_api.infrastructure.notifiers.SalaNotifierAdapter;
import com.humanconsulting.humancore_api.infrastructure.repositories.adapters.DashboardEmpresaRepositoryAdapter;
import com.humanconsulting.humancore_api.infrastructure.repositories.adapters.EmpresaRepositoryAdapter;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaDashboardEmpresaRepository;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaEmpresaRepository;
import com.humanconsulting.humancore_api.web.mappers.EmpresaMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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
            SalaRepository salaRepository,
            EmpresaResponseMapper empresaResponseMapper,
            AtualizarSalaUseCase atualizarSalaUseCase
    ) {
        return new AtualizarEmpresaUseCase(empresaRepository, usuarioRepository, salaRepository, empresaResponseMapper, atualizarSalaUseCase);
    }

    @Bean
    public BuscarEmpresaPorIdUseCase buscarEmpresaPorIdUseCase(EmpresaRepository empresaRepository) {
        return new BuscarEmpresaPorIdUseCase(empresaRepository);
    }

    @Bean
    public CriarDashboardEmpresaUseCase criarDashboardEmpresaUseCase(EmpresaResponseMapper empresaResponseMapper, BuscarEmpresaPorIdUseCase buscarEmpresaPorIdUseCase) {
        return new CriarDashboardEmpresaUseCase(
                empresaResponseMapper,
                buscarEmpresaPorIdUseCase
        );
    }

    @Bean
    public EmpresaMapper empresaMapper() {
        return new EmpresaMapper();
    }

    @Bean
    public EmpresaResponseMapper empresaResponseMapper(
            UsuarioRepository usuarioRepository,
            DashboardEmpresaRepository dashRepository,
            CheckpointRepository checkpointRepository,
            ListarTarefasPorAreaUseCase listarTarefasPorAreaUseCase,
            ListarFinanceiroPorEmpresaUseCase listarFinanceiroPorEmpresaUseCase,
            ListarTarefasPorEmpresaUsuarioUseCase listarTarefasPorEmpresaUsuarioUseCase
    ) {
        return new EmpresaResponseMapper(
                usuarioRepository,
                dashRepository,
                checkpointRepository,
                listarTarefasPorAreaUseCase,
                listarFinanceiroPorEmpresaUseCase,
                listarTarefasPorEmpresaUsuarioUseCase
        );
    }

    @Bean
    public DeletarEmpresaUseCase deletarEmpresaUseCase(EmpresaRepository empresaRepository) {
        return new DeletarEmpresaUseCase(empresaRepository);
    }

    @Bean
    public ListarTarefasPorEmpresaUsuarioUseCase listarTarefasPorEmpresaUsuarioUseCase(DashboardEmpresaRepository dashboardEmpresaRepository) {
        return new ListarTarefasPorEmpresaUsuarioUseCase(dashboardEmpresaRepository);
    }

    @Bean
    public ListarEmpresasUseCase listarEmpresasUseCase(EmpresaRepository empresaRepository, EmpresaResponseMapper empresaResponseMapper) {
        return new ListarEmpresasUseCase(empresaRepository, empresaResponseMapper);
    }

    @Bean
    public ListarEmpresasMenuRapidoUseCase listarEmpresasMenuRapidoUseCase(EmpresaRepository empresaRepository, EmpresaResponseMapper empresaResponseMapper) {
        return new ListarEmpresasMenuRapidoUseCase(empresaRepository, empresaResponseMapper);
    }

    @Bean
    public ListarEmpresasKpisUseCase listarEmpresasKpisUseCase(EmpresaRepository empresaRepository, EmpresaResponseMapper empresaResponseMapper, EmpresaMapper empresaMapper) {
        return new ListarEmpresasKpisUseCase(empresaRepository, empresaResponseMapper, empresaMapper);
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
            CadastrarMensagemInfoUseCase cadastrarMensagemInfoUseCase,
            RestTemplate restTemplate
    ) {
        return new SalaNotifierAdapter(salaRepository, cadastrarMensagemInfoUseCase, restTemplate) {
        };
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    };
}
