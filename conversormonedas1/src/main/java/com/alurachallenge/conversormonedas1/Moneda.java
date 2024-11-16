package com.alurachallenge.conversormonedas1;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

import java.util.Map;


public record Moneda(
        @SerializedName("base_code") String monedaOrigen,
        @SerializedName("conversion_rates") Map<String, Double> tasasConversion
) {

    public double getTasaConversion(String monedaDestino) {
        return tasasConversion.getOrDefault(monedaDestino, 0.0);
    }
}




