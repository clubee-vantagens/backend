package com.clubeevantagens.authmicroservice.infra.queue;

import com.clubeevantagens.authmicroservice.app.dto.SendEmailUseCaseInput;
import com.clubeevantagens.authmicroservice.app.queue.Queue;
import com.clubeevantagens.authmicroservice.app.usecase.SendEmailUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class QueueController {
  private final ObjectMapper objectMapper = new ObjectMapper();

  public QueueController(Queue queue, SendEmailUseCase sendEmailUseCase) {
    queue.consume("email-password.queue", message -> {
      try {
        SendEmailUseCaseInput input = objectMapper.readValue(message, SendEmailUseCaseInput.class);
        sendEmailUseCase.execute(input);
      } catch (JsonProcessingException e) {
        throw new RuntimeException("Error processing queue message:", e);
      }
    });
  }
}
