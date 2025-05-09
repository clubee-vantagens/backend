package com.clubeevantagens.authmicroservice.infra.gateway;

import com.clubeevantagens.authmicroservice.app.gateway.MailGateway;
import com.clubeevantagens.authmicroservice.infra.exception.general.ClientUnavailableException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class JavaMailSenderAdapter implements MailGateway {
  private final JavaMailSenderImpl connection;

  public JavaMailSenderAdapter(JavaMailSenderImpl connection) {
    this.connection = connection;
  }

  @Override
  public void send(String to, String subject, String body) {
    try {
      MimeMessage mimeMessage = this.connection.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
      helper.setFrom(connection.getUsername());
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(body, true);
      this.connection.send(mimeMessage);
    } catch(Exception e) {
      throw new ClientUnavailableException("Failed to send email");
    }
  }
}
