package com.signalapp.tradingsignalapp.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.HashMap;

@Component
public class BinanceCurrentPrice {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public Map<String, Double> currentUSDTPrices;


    public BinanceCurrentPrice(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public void fetchingPrices() {
        String url = "https://api.binance.com/api/v3/ticker/price";
        Map<String, Double> usdtPrices = new HashMap<>();

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode rootNode = objectMapper.readTree(response);

            for (JsonNode node : rootNode) {
                String symbol = node.get("symbol").asText();
                String priceStr = node.get("price").asText();

                // Check if the symbol ends with "USDT"
                if (symbol.endsWith("USDT")) {
                    // Remove "USDT" from the symbol
                    String symbolKey = symbol.substring(0, symbol.length() - 4);
                    double price = Double.parseDouble(priceStr);
                    usdtPrices.put(symbolKey, price);
                }
            }
            usdtPrices.put("USDT", 1.0);
        } catch (Exception e) {
            System.err.println("Error fetching current price: " + e.getMessage());
        }
        currentUSDTPrices = usdtPrices;
    }
}
