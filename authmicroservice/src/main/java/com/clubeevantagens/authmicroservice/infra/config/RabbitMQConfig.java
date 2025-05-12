package com.clubeevantagens.authmicroservice.infra.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RabbitMQConfig {
    @Value("${spring.rabbitmq.addresses}")
    private String uri;

    @Value("${email.exchange.name}")
    private String exchangeName;

    @Value("${email.password.queue.name}")
    private String queueName;

    @Value("${email.password.routing.key}")
    private String routingKey;

    @Bean
    public Channel rabbitMQConnection() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUri(uri);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            setup(channel);
            return channel;
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to RabbitMQ", e);
        }
    }

    private void setup(Channel channel) {
        createExchange(channel, exchangeName, "direct", true);
        createQueue(channel, queueName, true, false, false);
        bindingExchangeAndQueue(channel, queueName, exchangeName, routingKey);
    }

    private void createExchange(Channel channel, String exchangeName, String type, boolean isDurable) {
        try {
            channel.exchangeDeclare(exchangeName, type, isDurable);
        } catch(IOException e) {
            throw new RuntimeException("Failed to create exchange", e);
        }
    }

    private void createQueue(Channel channel, String queueName, boolean durable, boolean exclusive, boolean autoDelete) {
        try {
            channel.queueDeclare(queueName, durable, exclusive, autoDelete, null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to declare queue " + queueName, e);
        }
    }

    private void bindingExchangeAndQueue(Channel channel, String queueName, String exchangeName, String routingKey) {
        try {
            channel.queueBind(queueName, exchangeName, routingKey);
        } catch (Exception e) {
            throw new RuntimeException("Failed to bind queue to exchange", e);
        }
    }
}

