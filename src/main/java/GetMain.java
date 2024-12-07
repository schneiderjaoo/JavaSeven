package src.main.java;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetMain {
    static String escolhaFilme =  "avengers";
    static String OMDB_KEY = " ";

    public static void main(String[] args) {
        try {
            HttpClient client = HttpClient.newHttpClient();
        
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://www.omdbapi.com/?t=" + escolhaFilme + "&apikey=" + OMDB_KEY))
                .build();

                client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
        } catch (Exception e) {
            System.out.println("Erro ao buscar filme na API: " + e.getMessage());
            e.printStackTrace();
        }
    }
}