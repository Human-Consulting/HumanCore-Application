package com.humanconsulting.humancore_api.infrastructure.configs.usecases;

import com.humanconsulting.humancore_api.application.usecases.mensagem.CadastrarMensagemInfoUseCase;
import com.humanconsulting.humancore_api.application.usecases.sala.*;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.*;
import com.humanconsulting.humancore_api.application.usecases.sala.mappers.SalaResponseMapper;
import com.humanconsulting.humancore_api.infrastructure.repositories.adapters.SalaRepositoryAdapter;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaSalaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SalaConfig {
    @Bean
    public AtualizarSalaUseCase atualizarSalaUseCase(
            SalaRepository salaRepository,
            UsuarioRepository usuarioRepository,
            ProjetoRepository projetoRepository,
            EmpresaRepository empresaRepository,
            SalaNotifier salaNotifier,
            SalaResponseMapper salaResponseMapper
    ) {
        return new AtualizarSalaUseCase(
                salaRepository,
                usuarioRepository,
                projetoRepository,
                empresaRepository,
                salaNotifier,
                salaResponseMapper
        );
    }

    @Bean
    public BuscarChatsDoUsuarioUseCase buscarChatsDoUsuarioUseCase(
            SalaRepository salaRepository,
            MensagemRepository mensagemRepository,
            MensagemInfoRepository mensagemInfoRepository
    ) {
        return new BuscarChatsDoUsuarioUseCase(salaRepository, mensagemRepository, mensagemInfoRepository);
    }

    @Bean
    public BuscarSalaPorIdUseCase buscarSalaPorIdUseCase(SalaRepository salaRepository) {
        return new BuscarSalaPorIdUseCase(salaRepository);
    }

    @Bean
    public CadastrarSalaUseCase cadastrarSalaUseCase(
            SalaRepository salaRepository,
            UsuarioRepository usuarioRepository,
            CadastrarMensagemInfoUseCase cadastrarMensagemInfoUseCase,
            SalaResponseMapper salaResponseMapper
    ) {
        return new CadastrarSalaUseCase(
                salaRepository,
                usuarioRepository,
                cadastrarMensagemInfoUseCase,
                salaResponseMapper
        );
    }

    @Bean
    public DeletarSalaUseCase deletarSalaUseCase(SalaRepository salaRepository) {
        return new DeletarSalaUseCase(salaRepository);
    }

    @Bean
    public ListarSalasPorUsuarioUseCase listarSalasPorUsuarioUseCase(
            SalaRepository salaRepository,
            MensagemRepository mensagemRepository,
            MensagemInfoRepository mensagemInfoRepository
    ) {
        return new ListarSalasPorUsuarioUseCase(
                salaRepository,
                mensagemRepository,
                mensagemInfoRepository
        );
    }

    @Bean
    public SalaResponseMapper salaResponseMapper() {
        return new SalaResponseMapper();
    }

    @Bean
    public ListarSalasUseCase listarSalasUseCase(SalaRepository salaRepository, SalaResponseMapper salaResponseMapper) {
        return new ListarSalasUseCase(salaRepository, salaResponseMapper);
    }

    @Bean
    public SalaRepository salaRepository(JpaSalaRepository jpaSalaRepository) {
        return new SalaRepositoryAdapter(jpaSalaRepository);
    }
}
