package br.com.alura.TabelaFlipe.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties (ignoreUnknown = true)
public record Models (
        @JsonAlias("modelos") List<Dados> modelos) {

}
