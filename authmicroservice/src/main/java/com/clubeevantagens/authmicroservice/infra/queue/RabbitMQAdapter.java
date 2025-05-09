package com.clubeevantagens.authmicroservice.infra.queue;

import com.clubeevantagens.authmicroservice.app.queue.Queue;
import com.rabbitmq.client.Channel;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

@Component
public class RabbitMQAdapter implements Queue {
  private final Channel channel;

  public RabbitMQAdapter(Channel channel) {
    this.channel = channel;
  }

  @Override
  public void publish(String exchange, String routingKey, String message) {
    try {
      this.channel.basicPublish(exchange, routingKey, null, message.getBytes());
    } catch (Exception e) {
      throw new RuntimeException("Error publishing message", e);
    }
  }

  @Override
  public void consume(String queue, Consumer<String> callback) {
    try {
      channel.basicConsume(queue, false, (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
        try {
          callback.accept(message);
          channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        } catch (Exception e) {
          channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
          System.err.println("Failed to process message" + e.getMessage());
        }
      }, consumerTag -> {});
  } catch (IOException e) {
      throw new RuntimeException("Failed to consume message", e);
    }
  }
}
