package com.challengeliteralura.challengeliteralura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
    public record DatosIdiomas(
            @JsonAlias("languages") List<String> idiomas) {
    }

