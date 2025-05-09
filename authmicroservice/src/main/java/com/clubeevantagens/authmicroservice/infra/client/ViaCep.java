package com.clubeevantagens.authmicroservice.infra.client;

import com.clubeevantagens.authmicroservice.app.dto.response.ViaCepResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://viacep.com.br/ws/", name = "viacep")
@Tag(name = "ViaCep", description = "API para a qual enviamos uma requisição buscando os dados de endereço.")
public interface ViaCep {

    @GetMapping("/{cep}/json")
    ViaCepResponseDto findAddressByCep(@PathVariable String cep);
}