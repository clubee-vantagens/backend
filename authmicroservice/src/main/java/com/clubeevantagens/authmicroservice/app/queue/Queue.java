package com.clubeevantagens.authmicroservice.app.queue;

import java.util.function.Consumer;

public interface Queue {
  void publish(String exchange, String routingKey, String message);
  void consume(String queue, Consumer<String> callback);
}
