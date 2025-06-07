package com.humanconsulting.humancore_api.observer;

import com.humanconsulting.humancore_api.controller.dto.notificar.NotificacaoEmailDto;
import com.humanconsulting.humancore_api.controller.dto.request.UsuarioEnviarCodigoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.model.Tarefa;
import com.humanconsulting.humancore_api.model.Projeto;
import com.humanconsulting.humancore_api.model.Sprint;
import com.humanconsulting.humancore_api.model.Usuario;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class EmailNotifier implements Observer {
    private final JavaMailSender emailSender;

    @Override
    public void update(Tarefa tarefa, Sprint sprintEntrega, Projeto projetoEntrega, Usuario tarefaResponsavel, Usuario projetoResponsavel, LoginResponseDto responsavelProjeto, LoginResponseDto responsavelEntrega) {
        List<String> emails = new ArrayList<>();
        emails.add(responsavelProjeto.getEmail());
        if (!responsavelProjeto.getEmail().equals(responsavelEntrega.getEmail()))
            emails.add(responsavelEntrega.getEmail());

        NotificacaoEmailDto notificacao = new NotificacaoEmailDto();
        notificacao.setNomeResponsavel(responsavelProjeto.getNome());
        notificacao.setEmails(emails);
        notificacao.setMensagem(String.format("""
                            <div style='font-family: system-ui, sans-serif, Arial; font-size: 14px; color: #333; padding: 20px 14px; background-color: #f5f5f5;'>
                              <div style='max-width: 600px; margin: auto; background-color: #fff;'>
                                <div style='text-align: center; background-color: #333; padding: 14px;'>
                                  <a style='text-decoration: none; outline: none; color: white;' href='https://www.humanconsulting.com.br/' target='_blank'>
                                    HUMAN CONSULTING
                                  </a>
                                </div>
                                <div style='padding: 24px;'>
                                  <h2 style='font-size: 20px; margin-bottom: 20px; text-align: center;'>%s</h2>
                                  <p><strong>Projeto:</strong> %s</p>
                                  <p><strong>Sprint:</strong> %s</p>
                                  <p><strong>Tarefa:</strong> %s</p>
                                  <p><strong>Responsável:</strong> %s</p>
                                  <p><strong>Comentário:</strong> %s</p>
                                  <p style='margin-top: 30px; text-align: center;'>Atenciosamente,<br />Equipe Human Consulting</p>
                                </div>
                              </div>
                              <div style='max-width: 600px; margin: auto; text-align: center; font-size: 12px; color: #999; margin-top: 20px;'>
                                Este email foi enviado para %s<br />
                                Você o recebeu porque está registrado na Human Consulting.
                              </div>
                            </div>
                        """,
                tarefa.getComImpedimento() ? "Impedimento resolvido!" : "Novo impedimento identificado!",
                projetoEntrega.getDescricao(),
                sprintEntrega.getDescricao(),
                tarefa.getDescricao(),
                responsavelEntrega.getNome(),
                tarefa.getComentario(),
                String.join(", ", emails)
        ));
        enviarEmail(emails, "Impedimento no Projeto: " + projetoEntrega.getDescricao(), notificacao.getMensagem());
    }

    @Override
    public void cadastro(Usuario usuario) {
        NotificacaoEmailDto notificacao = new NotificacaoEmailDto();
        notificacao.setNomeResponsavel(usuario.getNome());
        List emailUnico = new ArrayList<>();
        emailUnico.add(usuario.getEmail());
        notificacao.setEmails(emailUnico);
        notificacao.setMensagem(String.format("""
                            <div style='font-family: system-ui, sans-serif, Arial; font-size: 14px; color: #333; padding: 20px 14px; background-color: #f5f5f5;'>
                              <div style='max-width: 600px; margin: auto; background-color: #fff;'>
                                <div style='text-align: center; background-color: #333; padding: 14px;'>
                                  <a style='text-decoration: none; outline: none; color: white;' href='https://www.humanconsulting.com.br/' target='_blank'>
                                    HUMAN CONSULTING
                                  </a>
                                </div>
                                <div style='padding: 24px;'>
                                  <h1 style='font-size: 22px; margin-bottom: 20px; text-align: center;'>
                                    Bem-vindo(a), %s!
                                  </h1>
                                  <p style='margin-bottom: 20px; text-align: center;'>
                                    Seu usuário foi adicionado aos projetos da <strong>%s</strong> como <strong>%s</strong> na área de <strong>%s</strong>.
                                  </p>
                                  <p style='margin-bottom: 20px; text-align: center;'>
                                    Use as credenciais abaixo para seu primeiro acesso:
                                  </p>
                                  <div style='text-align: center; margin: 24px 0;'>
                                    <div style='font-size: 16px; margin-bottom: 8px;'><strong>Email:</strong> %s</div>
                                    <div style='font-size: 16px;'><strong>Senha:</strong> %s</div>
                                  </div>
                                  <p style='text-align: center; margin-top: 20px;'>
                                    Por segurança, recomendamos que altere sua senha após o primeiro login.
                                  </p>
                                  <p style='margin-top: 30px; text-align: center;'>
                                    Atenciosamente,<br />
                                    Equipe Human Consulting
                                  </p>
                                </div>
                              </div>
                              <div style='max-width: 600px; margin: auto; text-align: center; font-size: 12px; color: #999; margin-top: 20px;'>
                                Este email foi enviado para %s<br />
                                Você o recebeu porque está registrado na Human Consulting.
                              </div>
                            </div>
                        """,
                usuario.getNome(),
                usuario.getEmpresa().getNome(),
                usuario.getCargo(),
                usuario.getArea(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getEmail()
        ));
        enviarEmail(emailUnico, "Bem vindo à Human Consulting", notificacao.getMensagem());
    }

    @Override
    public void codigo(UsuarioEnviarCodigoRequestDto usuarioEnviarCodigoRequestDto) {
        NotificacaoEmailDto notificacao = new NotificacaoEmailDto();
        List emailUnico = new ArrayList<>();
        emailUnico.add(usuarioEnviarCodigoRequestDto.getEmail());
        notificacao.setEmails(emailUnico);
        notificacao.setMensagem(String.format("""
                            <div
                                    style="
                                      font-family: system-ui, sans-serif, Arial;
                                      font-size: 14px;
                                      color: #333;
                                      padding: 20px 14px;
                                      background-color: #f5f5f5;
                                    "
                                  >
                                    <div style="max-width: 600px; margin: auto; background-color: #fff">
                                      <div style="text-align: center; background-color: #333; padding: 14px">
                                        <a style='text-decoration: none; outline: none; color: white;' href='https://www.humanconsulting.com.br/' target='_blank'>
                                            HUMAN CONSULTING
                                        </a>
                                      </div>
                                      <div style="padding: 24px">
                                        <h1 style="font-size: 22px; margin-bottom: 20px; text-align: center;">
                                          Código de Verificação
                                        </h1>
                                        <p style="margin-bottom: 20px; text-align: center;">
                                          Utilize o código abaixo para validar seu acesso ou redefinir sua senha:
                                        </p>
                        
                                        <!-- Quadradinhos do código -->
                                        <div style="text-align: center; margin: 24px 0;">
                                          <span style="
                                            display: inline-block;
                                            width: 42px;
                                            height: 42px;
                                            margin: 0 4px;
                                            border: 2px solid #333;
                                            border-radius: 6px;
                                            line-height: 42px;
                                            font-size: 20px;
                                            font-weight: bold;
                                          ">
                                            %s
                                          </span>
                                          <span style="display: inline-block; width: 42px; height: 42px; margin: 0 4px; border: 2px solid #333; border-radius: 6px; line-height: 42px; font-size: 20px; font-weight: bold;">
                                            %s
                                          </span>
                                          <span style="display: inline-block; width: 42px; height: 42px; margin: 0 4px; border: 2px solid #333; border-radius: 6px; line-height: 42px; font-size: 20px; font-weight: bold;">
                                            %s
                                          </span>
                                          <span style="display: inline-block; width: 42px; height: 42px; margin: 0 4px; border: 2px solid #333; border-radius: 6px; line-height: 42px; font-size: 20px; font-weight: bold;">
                                            %s
                                          </span>
                                          <span style="display: inline-block; width: 42px; height: 42px; margin: 0 4px; border: 2px solid #333; border-radius: 6px; line-height: 42px; font-size: 20px; font-weight: bold;">
                                            %s
                                          </span>
                                          <span style="display: inline-block; width: 42px; height: 42px; margin: 0 4px; border: 2px solid #333; border-radius: 6px; line-height: 42px; font-size: 20px; font-weight: bold;">
                                            %s
                                          </span>
                                        </div>
                        
                                        <p style="text-align: center;">
                                          Se você não solicitou este código, ignore este email.
                                        </p>
                        
                                        <p style="margin-top: 30px; text-align: center;">
                                          Atenciosamente,<br />
                                          Equipe Human Consulting
                                        </p>
                                      </div>
                                    </div>
                                    <div style="max-width: 600px; margin: auto; text-align: center; font-size: 12px; color: #999; margin-top: 20px;">
                                      Este email foi enviado para {{to_email}}<br />
                                      Você o recebeu porque está registrado na Human Consulting
                                    </div>
                                  </div>
                        """,
                usuarioEnviarCodigoRequestDto.getCodigo().charAt(0),
                usuarioEnviarCodigoRequestDto.getCodigo().charAt(1),
                usuarioEnviarCodigoRequestDto.getCodigo().charAt(2),
                usuarioEnviarCodigoRequestDto.getCodigo().charAt(3),
                usuarioEnviarCodigoRequestDto.getCodigo().charAt(4),
                usuarioEnviarCodigoRequestDto.getCodigo().charAt(5)
        ));
        enviarEmail(emailUnico, "Reset de Senha", notificacao.getMensagem());
    }

    public void enviarEmail(List<String> to, String assunto, String conteudoHtml) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(new InternetAddress("contato@humanconsulting.com.br", "Human Consulting"));

            helper.setTo(to.toArray(new String[0]));

            helper.setSubject(assunto);

            helper.setText(conteudoHtml, true);
            emailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
