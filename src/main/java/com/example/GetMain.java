package com.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

import io.github.cdimascio.dotenv.Dotenv;

record Filme(String title, String year, String director) {}

public class GetMain {
    static String escolhaFilme = "avengers";
    
    public static void main(String[] args) {
        try {
            Dotenv dotenv = Dotenv.load();
            String OMDB_KEY = dotenv.get("OMDB_KEY");
            
            if (OMDB_KEY == null) {
                System.out.println("API Key não encontrada no .env");
                return;
            }
            
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://www.omdbapi.com/?t=" + escolhaFilme + "&apikey=" + OMDB_KEY))
                .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    JSONObject json = new JSONObject(response);
                    if (json.getString("Response").equals("True")) {
                        Filme filme = criarFilme(json);
                        exibirFilme(filme);
                    } else {
                        System.out.println("Filme não encontrado");
                    }
                })
                .join();
        } catch (Exception e) {
            System.out.println("Erro ao buscar filme na API: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Filme criarFilme(JSONObject json) {
        return new Filme(
            json.getString("Title"),
            json.getString("Year"),
            json.getString("Director")
        );
    }

    private static void exibirFilme(Filme filme) {
        System.out.println("Title: " + filme.title());
        System.out.println("Year: " + filme.year());
        System.out.println("Director: " + filme.director());
    }
}
