package com.clubeevantagens.authmicroservice.app.interfaces;

@FunctionalInterface
public interface NoInputUseCase<O> extends UseCase<Void, O> {
    O execute();

    @Override
    default O execute(Void input) {
      return execute();
    }
}
