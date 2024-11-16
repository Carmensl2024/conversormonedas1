package com.alurachallenge.conversormonedas1;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;

public class Conversor {

    private Scanner teclado = new Scanner(System.in);

    public double conversor(double cantidad, double tasaConversion) {
        return cantidad * tasaConversion;
    }


    public void muestraElMenu() {
        int opcion = -1;
        while (opcion != 0) {
            String menu =

                    """
                    
                    ********************************************
                    1-       EUR      euro
                    2-       USD      dolares americanos
                    3-       COP      pesos colombianos
                    4-       JPY      yen japones
                    5-       BRL      real brasileño
                    
                    0-       Salir
                    ********************************************
                    ESCRIBA EL NÚMERO DE LA OPCIÓN!
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> convertirMoneda("EUR");
                case 2 -> convertirMoneda("USD");
                case 3 -> convertirMoneda("COP");
                case 4 -> convertirMoneda("JPY");
                case 5 -> convertirMoneda("BRL");
                case 0 -> System.out.println("¡Hasta pronto!");
                default -> System.out.println("Opción inválida. Por favor, selecciona una opción válida.");
            }
        }
    }

    private void convertirMoneda(String monedaOrigen) {
        System.out.println("""
                ********************************************
                ¿QUE CANTIDAD DESEA CONVERTIR?
                ********************************************
                """);
        double cantidad = teclado.nextDouble();
        teclado.nextLine();

        System.out.println("""
                **********************************************************
                ESCRIBA EL CODIGO DE LA MONEDA RESULTANTE:
                  (EUR, USD, COP, JPY, BRL)
                **********************************************************
                """);
        String monedaDestino = teclado.nextLine().toUpperCase();

        try {
            double tasaConversion = obtenerTasaConversion(monedaOrigen, monedaDestino);
            double resultado = conversor(cantidad, tasaConversion);
            System.out.printf("RESULTADO: %.2f %s%n", resultado, monedaDestino);
        } catch (Exception e) {
            System.out.println("Error al realizar la conversión: " + e.getMessage());
        }
    }

    private double obtenerTasaConversion(String monedaOrigen, String monedaDestino) throws Exception {
        String url = String.format("https://v6.exchangerate-api.com/v6/efdeed70d3179e3e43699de9/latest/%s", monedaOrigen);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Moneda moneda = new Gson().fromJson(response.body(), Moneda.class);

        Map<String, Double> tasasConversion = moneda.tasasConversion();
        Double tasaConversion = tasasConversion.get(monedaDestino);

        if (tasaConversion != null) {
            return tasaConversion;
        } else {
            throw new RuntimeException("Tasa de conversión no encontrada");
        }
    }
}



