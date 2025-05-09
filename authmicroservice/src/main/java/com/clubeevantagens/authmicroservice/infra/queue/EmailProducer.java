package com.clubeevantagens.authmicroservice.infra.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${email.exchange.name}")
    private String emailPasswordExchangeName;

    @Value("${email.password.routing.key}")
    private String emailPasswordRoutingKey;

    public void sendEmailMessage(String to, String subject, String text) {
        String message = to + ";" + subject + ";" + text;
        rabbitTemplate.convertAndSend(emailPasswordExchangeName, emailPasswordRoutingKey, message); // Envia a mensagem
    }
}
