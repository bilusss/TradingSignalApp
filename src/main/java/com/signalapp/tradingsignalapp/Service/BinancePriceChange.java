package com.signalapp.tradingsignalapp.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BinancePriceChange {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private String priceChangePercent = new String;

    public BinancePriceChange(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }
    public List<String> getPriceChange(String symbol) {

        String url = "https://api.binance.com/api/v3/ticker/24hr?symbol=" + symbol;
        List<String> symbolPriceChangeStatistics = new ArrayList<>();

        try{
            String response = restTemplate.getForObject(url, String.class);
            JsonNode rootNode = objectMapper.readTree(response);
            // If needed any more information from symbolNode then add below
            // (remember to add them also in other places below based on code)
            // added those we need for now
            String priceChangePercent = rootNode.get("priceChangePercent").asText();
            symbolPriceChangeStatistics.add(priceChangePercent);
        }catch (Exception e) {
            System.err.println("Error getting 24hr Ticker Price Change Statistics: " + e.getMessage());
        }
        return symbolPriceChangeStatistics;
    }
    public String getPriceChangePercent() {
        return priceChangePercent;
    }
}
