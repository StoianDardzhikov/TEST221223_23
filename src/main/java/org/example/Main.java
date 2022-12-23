package org.example;

import java.net.URI;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpClient httpClient = new HttpClient();

        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("https://jsonplaceholder.typicode.com/todos/1")).GET().build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(httpResponse.body());
    }
}