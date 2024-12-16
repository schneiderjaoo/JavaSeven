package com.example.generator;

import java.io.Writer;
import java.io.IOException;
import java.util.List;

public class HTMLGenerator {
    private final Writer writer;

    public HTMLGenerator(Writer writer) {
        this.writer = writer;
    }

    public void generate(List<?> movies) {
        try {
            writer.write("""
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Filmes</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #f0f2f5;
                            margin: 0;
                            padding: 20px;
                        }
                        h1 {
                            text-align: center;
                            color: #333;
                        }
                        .container {
                            display: flex;
                            flex-wrap: wrap;
                            justify-content: center;
                            gap: 20px;
                        }
                        .card {
                            background-color: #fff;
                            border-radius: 10px;
                            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                            padding: 20px;
                            width: 300px;
                            text-align: center;
                            transition: transform 0.3s ease-in-out;
                        }
                        .card:hover {
                            transform: scale(1.05);
                        }
                        .card h2 {
                            margin: 10px 0;
                            font-size: 1.5em;
                            color: #333;
                        }
                        .card p {
                            margin: 5px 0;
                            color: #666;
                        }
                        button {
                            background-color: #007BFF;
                            color: white;
                            border: none;
                            padding: 10px 20px;
                            border-radius: 5px;
                            cursor: pointer;
                            transition: background-color 0.3s;
                        }
                        button:hover {
                            background-color: #0056b3;
                        }
                    </style>
                    <script>
                        function showMovieDetails(movie) {
                            alert("Detalhes do Filme: " + movie);
                        }
                    </script>
                </head>
                <body>
                    <h1>Lista de Filmes</h1>
                    <div class="container">
            """);

            for (Object movie : movies) {
                writer.write(String.format("""
                    <div class="card">
                        <h2>%s</h2>
                        <p>Detalhes do filme gerados dinamicamente.</p>
                        <button onclick="showMovieDetails('%s')">Ver Detalhes</button>
                    </div>
                """, movie.toString(), movie.toString()));
            }

            writer.write("""
                    </div>
                </body>
                </html>
            """);

        } catch (IOException e) {
            System.out.println("Erro ao escrever no HTML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
