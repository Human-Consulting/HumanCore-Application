package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.request.MensagemInfoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.SalaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.chat.ChatMensagemUnificadaDto;
import com.humanconsulting.humancore_api.controller.dto.response.chat.ChatResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.chat.ChatUsuarioDto;
import com.humanconsulting.humancore_api.controller.dto.response.sala.SalaResponseDto;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.SalaMapper;
import com.humanconsulting.humancore_api.model.Empresa;
import com.humanconsulting.humancore_api.model.Projeto;
import com.humanconsulting.humancore_api.model.Sala;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.observer.SalaNotifier;
import com.humanconsulting.humancore_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SalaService {
    @Autowired private SalaRepository salaRepository;
    @Autowired private MensagemService mensagemService;
    @Autowired private MensagemRepository mensagemRepository;
    @Autowired private MensagemInfoRepository mensagemInfoRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ProjetoRepository projetoRepository;
    @Autowired private EmpresaRepository empresaRepository;
    @Autowired private SalaNotifier salaNotifier;

    public SalaResponseDto cadastrar(SalaRequestDto salaRequestDto) {
        Set<Usuario> participantesIniciais = new HashSet<>();
        for (Integer participante : salaRequestDto.getParticipantes()) {
            participantesIniciais.add(usuarioRepository.findById(participante).get());
        }
        participantesIniciais.add(usuarioRepository.findById(salaRequestDto.getIdEditor()).get());
        Sala salaCadastrada = salaRepository.save(SalaMapper.toEntity(salaRequestDto, participantesIniciais));
        MensagemInfoRequestDto mensagemInfoRequest = new MensagemInfoRequestDto("Conversa criada.", LocalDateTime.now(), salaCadastrada.getIdSala());
        mensagemService.cadastrarMensagemInfo(mensagemInfoRequest);
        return passarParaResponse(salaCadastrada);
    }

    public Sala buscarPorId(Integer id) {
        return salaRepository.findById(id).get();
    }

    public List<SalaResponseDto> listar() {
        List<Sala> all = salaRepository.findAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma sala registrada");
        List<SalaResponseDto> allResponses = new ArrayList<>();
        for (Sala sala : all) {
            allResponses.add(passarParaResponse(sala));
        }
        return allResponses;
    }

    public List<ChatResponseDto> getChatsDoUsuario(Integer idUsuario) {
        List<Sala> salas = salaRepository.findSalasComUsuariosPorUsuario(idUsuario);

        return salas.stream().map(sala -> {
            List<ChatUsuarioDto> participantes = sala.getUsuarios().stream()
                    .map(u -> new ChatUsuarioDto(u.getIdUsuario(), u.getNome()))
                    .toList();

            List<ChatMensagemUnificadaDto> mensagens = mensagemRepository.findBySalaOrderByHorarioAsc(sala)
                    .stream()
                    .map(m -> new ChatMensagemUnificadaDto(
                            m.getIdMensagem(),
                            m.getUsuario().getIdUsuario(),
                            m.getConteudo(),
                            m.getHorario(),
                            false
                    )).collect(Collectors.toList());

            List<ChatMensagemUnificadaDto> mensagensInfo = mensagemInfoRepository.findBySalaOrderByHorarioAsc(sala)
                    .stream()
                    .map(m -> new ChatMensagemUnificadaDto(
                            m.getIdMensagemInfo(),
                            null,
                            m.getConteudo(),
                            m.getHorario(),
                            true
                    )).collect(Collectors.toList());

            List<ChatMensagemUnificadaDto> todasMensagens = Stream.concat(mensagens.stream(), mensagensInfo.stream())
                    .sorted(Comparator.comparing(ChatMensagemUnificadaDto::getHorario))
                    .toList();

            Optional<Integer> fkProjeto = sala.getProjeto() == null ? Optional.empty() : Optional.of(sala.getProjeto().getIdProjeto());
            Optional<Integer> fkEmpresa = sala.getEmpresa() == null ? Optional.empty() : Optional.of(sala.getEmpresa().getIdEmpresa());

            return new ChatResponseDto(
                    sala.getIdSala(),
                    sala.getNome(),
                    sala.getUrlImagem(),
                    fkProjeto,
                    fkEmpresa,
                    participantes,
                    todasMensagens
            );
        }).toList();
    }

    public void deletar(Integer id) {
        Optional<Sala> optSala = salaRepository.findById(id);
        if (optSala.isEmpty()) throw new EntidadeNaoEncontradaException("Sala não encontrada.");
        salaRepository.deleteById(id);
    }

    public SalaResponseDto atualizar(Integer idSala, SalaRequestDto request) {
        Sala salaOriginal = salaRepository.buscarComUsuarios(idSala)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Sala não encontrada."));

        Set<Usuario> novosParticipantes = new HashSet<>();
        for (Integer participanteId : request.getParticipantes()) {
            System.out.println("id de usuário atual: " + participanteId);
            novosParticipantes.add(usuarioRepository.findById(participanteId).get());
        }

        if (request.getUrlImagem().isEmpty()) request.setUrlImagem(salaOriginal.getUrlImagem());

        Projeto projeto = request.getFkProjeto() != null
                ? projetoRepository.findById(request.getFkProjeto()).get()
                : null;

        Empresa empresa = request.getFkEmpresa() != null
                ? empresaRepository.findById(request.getFkEmpresa()).get()
                : null;

        Sala salaAtualizada = SalaMapper.toEntity(request, idSala, novosParticipantes, empresa, projeto);

        salaRepository.save(salaAtualizada);

        salaNotifier.notificarAtualizacoesSala(salaOriginal, salaAtualizada, usuarioRepository.findById(request.getIdEditor()).get());

        return passarParaResponse(salaAtualizada);
    }


    public SalaResponseDto passarParaResponse(Sala sala) {
        return SalaMapper.toDto(sala);
    }
}
