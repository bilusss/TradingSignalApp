package com.signalapp.tradingsignalapp.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;

public class BinanceAvailablePairs {

    @Value("${binance.apiKey}")
    private String apiKey;

    @Value("${binance.secretKey}")
    private String secretKey;

    public static ArrayList<String> TradingPairs(){
        String url = "https://api.binance.com/api/v3/exchangeInfo";
        ArrayList<String> AvailablePairs = new ArrayList<String>();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForObject(url, String.class);
        AvailablePairs.add("BTCUSDT");
        return AvailablePairs;
    }
}