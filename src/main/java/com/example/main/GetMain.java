package com.example.main;

import com.example.generator.HTMLGenerator;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

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
                        gerarHTML(List.of(filme)); // Geração do HTML com lista de filmes
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
        System.out.println("Title: " + filme.title()+"/n");
        System.out.println("Year: " + filme.year()+"/n");
        System.out.println("Director: " + filme.director());
    }

    private static void gerarHTML(List<Filme> filmes) {
    String outputPath = "src/main/resources/output/movies.html";
    File outputDir = new File("src/main/resources/output");

    if (!outputDir.exists()) {
        outputDir.mkdirs();
    }

    try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
        HTMLGenerator htmlGenerator = new HTMLGenerator(writer);
        htmlGenerator.generate(filmes);
        System.out.println("HTML gerado com sucesso em: " + outputPath);
    } catch (IOException e) {
        System.out.println("Erro ao gerar HTML: " + e.getMessage());
        e.printStackTrace();
    }
}

}
