package com.clubeevantagens.authmicroservice.app.usecase;

import com.clubeevantagens.authmicroservice.app.queue.Queue;
import com.clubeevantagens.authmicroservice.app.repository.ClientRepository;
import com.clubeevantagens.authmicroservice.domain.entity.Client;
import com.clubeevantagens.authmicroservice.infra.exception.general.EntityNotFoundException;
import com.clubeevantagens.authmicroservice.app.interfaces.UseCase;
import com.clubeevantagens.authmicroservice.domain.service.PasswordGenerator;
import com.clubeevantagens.authmicroservice.domain.entity.User;
import com.clubeevantagens.authmicroservice.app.dto.ForgotPasswordInput;
import com.clubeevantagens.authmicroservice.app.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordUseCase implements UseCase<ForgotPasswordInput, Void> {
  private final UserRepository userRepository;
  private final ClientRepository clientRepository;
  private final PasswordEncoder encoder;
  private final Queue queue;

  public ForgotPasswordUseCase(UserRepository userRepository, ClientRepository clientRepository, PasswordEncoder encoder, Queue queue) {
    this.userRepository = userRepository;
    this.clientRepository = clientRepository;
    this.encoder = encoder;
    this.queue = queue;
  }

  @Override
  public Void execute(ForgotPasswordInput input) {
    User user = userRepository.findUserByEmail(input.email()).orElseThrow(() -> new EntityNotFoundException("User not found"));
    Client client = this.clientRepository.findByUserEmail(input.email()).orElseThrow(() -> new EntityNotFoundException("Client not found"));
    String newPassword = PasswordGenerator.generatePassword();
    String hashedPassword = this.encoder.encode(newPassword);
    user.setPassword(hashedPassword);
    String message = String.format("{\"email\":\"%s\", \"name\":\"%s\",\"newPassword\":\"%s\"}", input.email(), client.getName(), newPassword);
    this.queue.publish("email.ex", "email-password.routing-key", message);
    userRepository.save(user);
    return null;
  }
}
