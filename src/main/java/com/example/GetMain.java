package com.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

public class GetMain {
    static String escolhaFilme =  "avengers";
    static String OMDB_KEY = "";

    public static void main(String[] args) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://www.omdbapi.com/?t=" + escolhaFilme + "&apikey=" + OMDB_KEY))
                .build();
        
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    
                    JSONObject json = new JSONObject(response);
                    if (json.getString("Response").equals("True")) {
                        String title = json.getString("Title");
                        String year = json.getString("Year");
                        String director = json.getString("Director");
        
                        System.out.println("Title: " + title);
                        System.out.println("Year: " + year);
                        System.out.println("Director: " + director);
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
}