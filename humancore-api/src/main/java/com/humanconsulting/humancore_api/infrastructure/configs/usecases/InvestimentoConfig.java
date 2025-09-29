package com.humanconsulting.humancore_api.infrastructure.configs.usecases;

import com.humanconsulting.humancore_api.application.usecases.investimento.*;
import com.humanconsulting.humancore_api.domain.repositories.InvestimentoRepository;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.infrastructure.repositories.adapters.InvestimentoRepositoryAdapter;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaInvestimentoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvestimentoConfig {
    @Bean
    public AtualizarInvestimentoUseCase atualizarInvestimentoUseCase(
            InvestimentoRepository investimentoRepository,
            UsuarioRepository usuarioRepository,
            BuscarInvestimentoPorIdUseCase buscarInvestimentoPorIdUseCase
    ) {
        return new AtualizarInvestimentoUseCase(
                investimentoRepository,
                usuarioRepository,
                buscarInvestimentoPorIdUseCase
        );
    }

    @Bean
    public BuscarInvestimentoPorIdUseCase buscarInvestimentoPorIdUseCase(InvestimentoRepository investimentoRepository) {
        return new BuscarInvestimentoPorIdUseCase(investimentoRepository);
    }

    @Bean
    public CadastrarInvestimentoUseCase cadastrarInvestimentoUseCase(InvestimentoRepository investimentoRepository, ProjetoRepository projetoRepository) {
        return new CadastrarInvestimentoUseCase(investimentoRepository, projetoRepository);
    }

    @Bean
    public DeletarInvestimentoUseCase deletarInvestimentoUseCase(InvestimentoRepository investimentoRepository) {
        return new DeletarInvestimentoUseCase(investimentoRepository);
    }

    @Bean
    public ListarInvestimentosPorProjetoUseCase listarInvestimentosPorProjetoUseCase(InvestimentoRepository investimentoRepository) {
        return new ListarInvestimentosPorProjetoUseCase(investimentoRepository);
    }

    @Bean
    public ListarInvestimentosUseCase listarInvestimentosUseCase(InvestimentoRepository investimentoRepository) {
        return new ListarInvestimentosUseCase(investimentoRepository);
    }

    @Bean
    public InvestimentoRepository investimentoRepository(JpaInvestimentoRepository jpaInvestimentoRepository) {
        return new InvestimentoRepositoryAdapter(jpaInvestimentoRepository);
    }
}
