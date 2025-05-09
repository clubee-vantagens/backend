package com.clubeevantagens.authmicroservice.app.interfaces;

@FunctionalInterface
public interface NoInputController<O> extends Controller<Void, O> {
  O handle();

  @Override
  default O handle(Void input) {
    return handle();
  }
}
