package br.com.alura.challenge.litealura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record  DadosBook(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DadosAuthors> autor,
        @JsonAlias("languages") ArrayList languages,
        @JsonAlias("download_count") int downloads
        ) {
}
