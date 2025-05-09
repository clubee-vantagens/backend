package com.clubeevantagens.authmicroservice.app.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface Controller<I, O> {
  O handle(I input);
}
