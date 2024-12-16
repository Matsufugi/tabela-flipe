package br.com.alura.TabelaFlipe.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record Dados(
        @JsonAlias("codigo") String cod,
        @JsonAlias("nome") String name
) {
}
