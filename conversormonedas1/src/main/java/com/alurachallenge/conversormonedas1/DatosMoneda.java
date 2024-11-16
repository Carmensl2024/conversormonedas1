package com.alurachallenge.conversormonedas1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosMoneda(
        String monedaOrigen,
        Double tasaConversion
) {

    @Override
    public String toString() {
        return "DatosMoneda[" +
                "monedaOrigen=" + monedaOrigen + ", " +
                "tasaConversion=" + tasaConversion + ']';
    }
}

