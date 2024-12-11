package com.signalapp.tradingsignalapp.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BinanceHistoricalData {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public BinanceHistoricalData(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<List<String>> getHistoricalData(String symbol, String interval) {
        String limit = "1000";//By default, it's set to 500, but we will be using 1000
        // as we need more data if user move chart more to the left (to the past :D)
        String url = "https://api.binance.com/api/v3/uiKlines?symbol=" + symbol + "&interval=" + interval + "&limit=" + limit;
        List<List<String>> symbolData = new ArrayList<>();

        try{
            String response = restTemplate.getForObject(url, String.class);
            JsonNode rootNode = objectMapper.readTree(response);

            for (JsonNode entry : rootNode) {
                List<String> row = new ArrayList<>();
                for (JsonNode value : entry) {
                    row.add(value.asText());
                }
                symbolData.add(row);
            }
        }catch (Exception e) {
            System.err.println("Error fetching historical data: " + e.getMessage());
        }
        return symbolData;
    }
}