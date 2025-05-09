package com.clubeevantagens.authmicroservice.app.interfaces;

public interface UseCase<I, O> {
  O execute(I input);
}
