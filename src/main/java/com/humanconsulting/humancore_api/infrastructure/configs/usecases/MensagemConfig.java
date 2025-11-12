package com.humanconsulting.humancore_api.infrastructure.configs.usecases;

import com.humanconsulting.humancore_api.application.usecases.mensagem.*;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.MensagemRepository;
import com.humanconsulting.humancore_api.domain.repositories.MensagemInfoRepository;
import com.humanconsulting.humancore_api.application.usecases.mensagem.mappers.MensagemResponseMapper;
import com.humanconsulting.humancore_api.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.infrastructure.repositories.adapters.MensagemInfoRepositoryAdapter;
import com.humanconsulting.humancore_api.infrastructure.repositories.adapters.MensagemRepositoryAdapter;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaMensagemInfoRepository;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaMensagemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MensagemConfig {
    @Bean
    public AtualizarMensagemUseCase atualizarMensagemUseCase(
            MensagemRepository mensagemRepository,
            UsuarioRepository usuarioRepository,
            SalaRepository salaRepository,
            BuscarMensagemPorIdUseCase buscarMensagemPorIdUseCase
    ) {
        return new AtualizarMensagemUseCase(
                mensagemRepository,
                usuarioRepository,
                salaRepository,
                buscarMensagemPorIdUseCase
        );
    }

    @Bean
    public BuscarMensagemPorIdUseCase buscarMensagemPorIdUseCase(MensagemRepository mensagemRepository) {
        return new BuscarMensagemPorIdUseCase(mensagemRepository);
    }

    @Bean
    public CadastrarMensagemInfoUseCase cadastrarMensagemInfoUseCase(MensagemInfoRepository mensagemInfoRepository, SalaRepository salaRepository) {
        return new CadastrarMensagemInfoUseCase(mensagemInfoRepository, salaRepository);
    }

    @Bean
    public CadastrarMensagemUseCase cadastrarMensagemUseCase(
            MensagemRepository mensagemRepository,
            UsuarioRepository usuarioRepository,
            SalaRepository salaRepository,
            SalaNotifier salaNotifier
    ) {
        return new CadastrarMensagemUseCase(
                mensagemRepository,
                usuarioRepository,
                salaRepository,
                salaNotifier
        );
    }

    @Bean
    public DeletarMensagemUseCase deletarMensagemUseCase(MensagemRepository mensagemRepository, BuscarMensagemPorIdUseCase buscarMensagemPorIdUseCase) {
        return new DeletarMensagemUseCase(mensagemRepository, buscarMensagemPorIdUseCase);
    }

    @Bean
    public ListarMensagensUseCase listarMensagensUseCase(MensagemRepository mensagemRepository) {
        return new ListarMensagensUseCase(mensagemRepository);
    }

    @Bean
    public MensagemRepository mensagemRepository(JpaMensagemRepository jpaMensagemRepository) {
        return new MensagemRepositoryAdapter(jpaMensagemRepository);
    }

    @Bean
    public MensagemInfoRepository mensagemInfoRepository(JpaMensagemInfoRepository jpaMensagemInfoRepository) {
        return new MensagemInfoRepositoryAdapter(jpaMensagemInfoRepository);
    }
}
