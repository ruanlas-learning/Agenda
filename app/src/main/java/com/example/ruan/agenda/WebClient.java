package com.example.ruan.agenda;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class WebClient {
    public String post(String json){
        try {
            URL url = new URL("https://www.caelum.com.br/mobile");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // informa no cabeçalho da requisição qual tipo de dados está sendo enviado
            connection.setRequestProperty("Content-type", "application/json");
            // informa no cabeçalho da requisição os dados que a aplicação espera na resposta
            // (está passando esta informação no cabeçalho da requisição)
            connection.setRequestProperty("Accept", "application/json");

            // este parâmetro informa que será enviado dados no corpo da requisição (post)
            connection.setDoOutput(true);

            // adiciona o corpo da requisição
            PrintStream output = new PrintStream(connection.getOutputStream());
            output.println(json);

            connection.connect();

            // obtém a resposta da requisição
            Scanner scanner = new Scanner(connection.getInputStream());
            String resposta = scanner.next();
            return resposta;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
