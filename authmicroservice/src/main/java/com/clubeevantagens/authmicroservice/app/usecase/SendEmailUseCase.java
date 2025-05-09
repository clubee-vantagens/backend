package com.clubeevantagens.authmicroservice.app.usecase;

import com.clubeevantagens.authmicroservice.app.dto.SendEmailUseCaseInput;
import com.clubeevantagens.authmicroservice.app.gateway.MailGateway;
import com.clubeevantagens.authmicroservice.app.interfaces.UseCase;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class SendEmailUseCase implements UseCase<SendEmailUseCaseInput, Void> {
  private final MailGateway mailGateway;
  private final TemplateEngine templateEngine;

  public SendEmailUseCase(MailGateway mailGateway, TemplateEngine templateEngine) {
    this.mailGateway = mailGateway;
    this.templateEngine = templateEngine;
  }

  @Override
  public Void execute(SendEmailUseCaseInput input) {
    Context context = new Context();
    context.setVariable("name", input.name());
    context.setVariable("newPassword", input.newPassword());

    String body = this.templateEngine.process("forgot-password", context);
    String subject = "Alerta Clubee | Redefina sua senha e continue aproveitando suas vantagens!";

    this.mailGateway.send(input.email(), subject, body);
    return null;
  }
}
