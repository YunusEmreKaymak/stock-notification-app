package com.yunus.stock_service.stock;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;

public class FetchStock {

    public static String getStockData(String stockName, String apiKey) throws URISyntaxException, ExecutionException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(new URI("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=" + stockName + "&apikey=" + apiKey))
                .build();
        String stockHistory = client.sendAsync(req, HttpResponse.BodyHandlers.ofString()).get().body();

        return stockHistory;
    }
}
