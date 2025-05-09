package com.clubeevantagens.authmicroservice.app.gateway;

public interface MailGateway {
  void send(String to, String subject, String body);
}
