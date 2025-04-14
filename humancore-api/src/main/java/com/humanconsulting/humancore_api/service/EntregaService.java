package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.entrega.AtualizarStatusRequestDto;
import com.humanconsulting.humancore_api.controller.dto.atualizar.entrega.AtualizarGeralRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.EntregaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.EntregaResponseDto;
import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeSemPermissaoException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.EntregaMapper;
import com.humanconsulting.humancore_api.model.Entrega;
import com.humanconsulting.humancore_api.model.Projeto;
import com.humanconsulting.humancore_api.model.Sprint;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.observer.EmailNotifier;
import com.humanconsulting.humancore_api.observer.Observer;
import com.humanconsulting.humancore_api.repository.EntregaRepository;
import com.humanconsulting.humancore_api.repository.UsuarioRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EntregaService {

    @Autowired private EntregaRepository entregaRepository;

    @Autowired private UsuarioRepository usuarioRepository;

    @Autowired private SprintService sprintService;

    @Autowired private ProjetoService projetoService;

    @Autowired private JavaMailSender
     emailSender;

    @Autowired private EmailNotifier emailNotifier;

    public EntregaService(EntregaRepository entregaRepository,
                          UsuarioRepository usuarioRepository,
                          SprintService sprintService,
                          ProjetoService projetoService,
                          JavaMailSender emailSender,
                          EmailNotifier emailNotifier) {
        this.entregaRepository = entregaRepository;
        this.usuarioRepository = usuarioRepository;
        this.sprintService = sprintService;
        this.projetoService = projetoService;
        this.emailSender = emailSender;
        this.emailNotifier = emailNotifier;
    }

    public EntregaService() {
    }

    public EntregaResponseDto cadastrar(EntregaRequestDto entregaRequestDto) {
        if (entregaRequestDto.getDtInicio().isAfter(entregaRequestDto.getDtFim()) || entregaRequestDto.getDtInicio().isEqual(entregaRequestDto.getDtFim())) throw new EntidadeConflitanteException("Datas de início e fim conflitantes.");
        Entrega entrega = entregaRepository.insert(EntregaMapper.toEntity(entregaRequestDto));
        return passarParaResponse(entrega, entrega.getFkResponsavel());
    }

    public EntregaResponseDto buscarPorId(Integer id) {
        Entrega entrega = entregaRepository.selectWhereId(id);
        return passarParaResponse(entrega, entrega.getFkResponsavel());
    }

    public List<EntregaResponseDto> listar() {
        List<Entrega> all = entregaRepository.selectAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma entrega registrada");
        List<EntregaResponseDto> allResponse = new ArrayList<>();
        for (Entrega entrega : all) {
            allResponse.add(passarParaResponse(entrega, entrega.getFkResponsavel()));
        }
        return allResponse;
    }

    public void deletar(Integer id) {
        entregaRepository.deleteWhere(id);
    }

    public EntregaResponseDto atualizar(Integer idEntrega, AtualizarGeralRequestDto requestUpdate) {
        Boolean temPermissao = validarPermissao(idEntrega, requestUpdate.getIdEditor(), requestUpdate.getPermissaoEditor());

        if (!temPermissao) throw new EntidadeSemPermissaoException("Você não tem permissão para fazer essa edição");

       Entrega entrega = entregaRepository.update(idEntrega, requestUpdate);
       return passarParaResponse(entrega, entrega.getFkResponsavel());
    }

    public EntregaResponseDto atualizarFinalizada(Integer idEntrega, AtualizarStatusRequestDto request) {
        Boolean temPermissao = validarPermissao(idEntrega, request.getIdEditor(), request.getPermissaoEditor());
        if (!temPermissao) throw new EntidadeSemPermissaoException("Você não tem permissão para fazer essa edição");

        entregaRepository.existsById(idEntrega);
        Entrega entrega = entregaRepository.updateFinalizar(idEntrega);
        return passarParaResponse(entrega, request.getIdEditor());
    }

    public EntregaResponseDto atualizarImpedimento(Integer idEntrega, AtualizarStatusRequestDto request) {
        Boolean temPermissao = validarPermissao(idEntrega, request.getIdEditor(), request.getPermissaoEditor());
        if (!temPermissao) throw new EntidadeSemPermissaoException("Você não tem permissão para fazer essa edição");

        entregaRepository.existsById(idEntrega);
        Entrega entrega = entregaRepository.updateImpedimento(idEntrega);
        System.out.println(entrega.toString());

        if (entrega.getComImpedimento()){
            emailNotifier.update(entrega);
        }

        return passarParaResponse(entrega, request.getIdEditor());
    }

    public EntregaResponseDto passarParaResponse(Entrega entrega, Integer fkResponsavel) {
        Usuario usuario = usuarioRepository.selectWhereId(fkResponsavel);
        return EntregaMapper.toDto(entrega, usuario.getNome(), usuario.getArea());
    }

    public Boolean validarPermissao(Integer idEntrega, Integer idEditor, @NotBlank String permissaoEditor) {
        EntregaResponseDto entrega = buscarPorId(idEntrega);
        Sprint sprint = sprintService.buscarPorId(entrega.getFkSprint());
        Projeto projeto = projetoService.buscarPorId(sprint.getFkProjeto());

        return Objects.equals(idEditor, entrega.getFkResponsavel()) || Objects.equals(idEditor, projeto.getFkResponsavel());
    }
}
