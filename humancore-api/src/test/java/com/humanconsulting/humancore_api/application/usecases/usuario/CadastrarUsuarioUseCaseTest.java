package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.infrastructure.configs.RabbitTemplateConfiguration;
import com.humanconsulting.humancore_api.infrastructure.mappers.EmailCadastroMapper;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.email.EmailCadastroResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CadastrarUsuarioUseCaseTest {

    private UsuarioRepository usuarioRepository;
    private EmpresaRepository empresaRepository;
    private PasswordEncoder passwordEncoder;
    private RabbitTemplateConfiguration rabbitMQ;
    private SalaNotifier salaNotifier;
    private UsuarioResponseMapper usuarioResponseMapper;
    private EmailCadastroMapper emailCadastroMapper;
    private RabbitTemplate rabbitTemplate;

    private CadastrarUsuarioUseCase useCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        empresaRepository = mock(EmpresaRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        rabbitMQ = mock(RabbitTemplateConfiguration.class);
        salaNotifier = mock(SalaNotifier.class);
        usuarioResponseMapper = mock(UsuarioResponseMapper.class);
        emailCadastroMapper = mock(EmailCadastroMapper.class);
        rabbitTemplate = mock(RabbitTemplate.class);

        when(rabbitMQ.rabbitTemplate()).thenReturn(rabbitTemplate);

        useCase = new CadastrarUsuarioUseCase(
                usuarioRepository,
                empresaRepository,
                passwordEncoder,
                rabbitMQ,
                salaNotifier,
                usuarioResponseMapper,
                emailCadastroMapper
        );
    }

    @Test
    void deveCadastrarUsuarioComSucesso() {
        // Arrange
        UsuarioRequestDto request = new UsuarioRequestDto();
        request.setEmail("novo@teste.com");
        request.setFkEmpresa(1);
        request.setNome("Pedro");

        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(1);
        empresa.setNome("EmpresaTeste");

        Usuario usuario = mock(Usuario.class);

        usuario.setEmail(request.getEmail());
        usuario.setNome(request.getNome());
        usuario.setEmpresa(empresa);

        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setIdUsuario(10);
        usuarioSalvo.setEmail(request.getEmail());

        EmailCadastroResponseDto emailDto = mock(EmailCadastroResponseDto.class);

        emailDto.setNome(usuario.getNome());

        LoginResponseDto responseDto = new LoginResponseDto();

        responseDto.setNome("token-falso");
        responseDto.setEmail(usuario.getEmail());

        UsuarioResponseDto usuarioResponseDto = mock(UsuarioResponseDto.class);

        try (MockedStatic<EmailCadastroMapper> mapperMock = mockStatic(EmailCadastroMapper.class)) {
            mapperMock.when(() -> EmailCadastroMapper.toEmailCadastroResponseDto(any())).thenReturn(emailDto);
        }

        when(usuarioRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(empresaRepository.findById(request.getFkEmpresa())).thenReturn(Optional.of(empresa));
        when(passwordEncoder.encode(anyString())).thenReturn("senhaCriptografada");
        when(usuarioRepository.save(any())).thenReturn(usuarioSalvo);
        when(usuarioResponseMapper.toResponse(usuarioSalvo)).thenReturn(usuarioResponseDto);

        // Act
        UsuarioResponseDto result = useCase.execute(request);

        // Assert
        assertNotNull(result);
        verify(usuarioRepository).save(any());
        verify(salaNotifier).adicionarUsuarioEmSalaEmpresa(usuarioSalvo);
    }

    @Test
    void deveLancarExcecaoQuandoEmailJaRegistrado() {
        UsuarioRequestDto request = new UsuarioRequestDto();
        request.setEmail("existente@teste.com");

        when(usuarioRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new Usuario()));

        assertThrows(EntidadeConflitanteException.class, () -> useCase.execute(request));
    }

    @Test
    void deveLancarExcecaoQuandoEmpresaNaoEncontrada() {
        UsuarioRequestDto request = new UsuarioRequestDto();
        request.setEmail("novo@teste.com");
        request.setFkEmpresa(99);

        when(usuarioRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(empresaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> useCase.execute(request));
    }

    @Test
    void deveLancarExcecaoQuandoRabbitMQIndisponivel() {
        UsuarioRequestDto request = new UsuarioRequestDto();
        request.setEmail("novo@teste.com");
        request.setFkEmpresa(1);

        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(1);
        empresa.setNome("EmpresaTeste");

        when(usuarioRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(empresaRepository.findById(request.getFkEmpresa())).thenReturn(Optional.of(empresa));
        when(passwordEncoder.encode(anyString())).thenReturn("senhaCriptografada");

        doThrow(new AmqpConnectException(new RuntimeException("Falha conexão")))
                .when(rabbitTemplate).convertAndSend(anyString(), Optional.ofNullable(any()));

        assertThrows(NullPointerException.class, () -> useCase.execute(request));
    }

    @Test
    void deveLancarExcecaoQuandoFalhaAoEnviarEmail() {
        UsuarioRequestDto request = new UsuarioRequestDto();
        request.setEmail("novo@teste.com");
        request.setFkEmpresa(1);

        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(1);
        empresa.setNome("EmpresaTeste");

        when(usuarioRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(empresaRepository.findById(request.getFkEmpresa())).thenReturn(Optional.of(empresa));
        when(passwordEncoder.encode(anyString())).thenReturn("senhaCriptografada");

        doThrow(new AmqpException("Falha envio"))
                .when(rabbitTemplate).convertAndSend(anyString(), Optional.ofNullable(any()));

        assertThrows(NullPointerException.class, () -> useCase.execute(request));
    }
}
