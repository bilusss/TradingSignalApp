package com.signalapp.tradingsignalapp.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;

@Configuration
public class BinanceAvailablePairs {

    private static final String url = "https://api.binance.com/api/v3/exchangeInfo";

    @Bean
    public static ArrayList<String> TradingPairs(){
        ArrayList<String> AvailablePairs = new ArrayList<String>();
        RestTemplate restTemplate = new RestTemplate();

        try {
            String response = restTemplate.getForObject(url, String.class);

            JsonNode rootNode = com.fasterxml.jackson.databind.json.JsonMapper.builder()
                    .build()
                    .readTree(response);

            JsonNode symbolsNode = rootNode.path("symbols");
            for (JsonNode symbolNode : symbolsNode) {
                String symbol = symbolNode.path("symbol").asText();
                String status = symbolNode.path("status").asText();

                if ("TRADING".equals(status)) {
                    AvailablePairs.add(symbol);
                }
            }
        } catch (Exception err){
            System.err.println("Binance error: " + err.getMessage());
        }
        return AvailablePairs;
    }
}