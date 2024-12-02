package com.signalapp.tradingsignalapp.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

public class BinanceTestPing {

    @Value("${binance.apiKey}")
    private String apiKey;

    @Value("${binance.secretKey}")
    private String secretKey;

    public String testConnection() {
        String url = "https://api.binance.com/api/v3/ping";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }
}