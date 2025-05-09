package com.clubeevantagens.authmicroservice.infra.queue;

import com.clubeevantagens.authmicroservice.app.usecase.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

@Component
public class EmailConsumer implements ChannelAwareMessageListener {

    @Autowired
    private EmailService emailService;

    @Override
    @RabbitListener(queues = "${email.password.queue.name}")
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            String body = new String(message.getBody());
            String[] parts = body.split(";");
            if (parts.length < 3) {
                throw new IllegalArgumentException("Mensagem inválida: " + body);
            }

            String to = parts[0];
            String subject = parts[1];
            String text = parts[2];

            String retorno = emailService.sendSimpleEmail(to, subject, text);
            System.out.println(retorno);

            // Confirma a mensagem
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // Em caso de falha, a mensagem não é confirmada e pode ser reprocessada
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            System.err.println("Erro ao processar a mensagem: " + e.getMessage());
        }
    }
}
