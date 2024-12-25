package com.signalapp.tradingsignalapp.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class BinanceExchangeInfo {
    @Value("${binance.exchangeInfo.url}")
    private String URL_BINANCE_EXCHANGE_INFO;

    private double lastFetchTime;
    private Map<String, SymbolInfo> symbolMap;

    private static final long DURATION_IN_MILLIS = 5*60*1000; // 5 minutes in millis

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public BinanceExchangeInfo(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.symbolMap = new HashMap<>();
        this.lastFetchTime = 0;
    }

    @PostConstruct
    public void initializeExchangeInfo() {
        try {
            fetchExchangeInfo();
        } catch (IOException e) {
            System.err.println("Failed to initialize exchange info: " + e.getMessage());
        }
    }

    public void fetchExchangeInfo() throws IOException {
        double currentTime = System.currentTimeMillis();

        if (currentTime - lastFetchTime < DURATION_IN_MILLIS && !symbolMap.isEmpty()) {
            System.out.println("Using cached exchange info");
            return;
        }

        System.out.println("Fetching exchange info");
        String response = restTemplate.getForObject(URL_BINANCE_EXCHANGE_INFO, String.class);

        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode symbolsNode = rootNode.get("symbols");

        symbolMap.clear();

        if (symbolsNode != null) {
            for (JsonNode symbolNode : symbolsNode) {
                // If needed any more information from symbolsNode then add below
                // (remember to add them also in other places below based on code)
                // added those we need for now
                String symbol = symbolNode.get("symbol").asText();
                String status = symbolNode.get("status").asText();
                String baseAsset = symbolNode.get("baseAsset").asText();
                String quoteAsset = symbolNode.get("quoteAsset").asText();
                JsonNode filtersNode = symbolNode.get("filters");
                String tickSize = null;
                if (filtersNode != null) {
                    for (JsonNode filterNode : filtersNode) {
                        if (filterNode.get("filterType").asText().equals("PRICE_FILTER")) {
                            tickSize = filterNode.get("tickSize").asText();
                            break;
                        }
                    }
                }
                String quotePrecision = symbolNode.get("quotePrecision").asText();
                SymbolInfo symbolInfo = new SymbolInfo(symbol, status, baseAsset, quoteAsset, tickSize, quotePrecision);
                symbolMap.put(symbol, symbolInfo);
            }
        }
        double inTime = (System.currentTimeMillis() - currentTime)/1000;
        System.out.println("Fetched in " + inTime + "s");
        lastFetchTime = currentTime;
    }

    public SymbolInfo getSymbolInfo(String symbol) {
        // Get info about pair (called symbol by Binance)
        return symbolMap.get(symbol);
    }

    public static class SymbolInfo {
        private final String symbol;
        private final String status;
        private final String baseAsset;
        private final String quoteAsset;
        private final String tickSize;
        private final String quotePrecision;

        public SymbolInfo(String symbol, String status, String baseAsset, String quoteAsset, String tickSize, String quotePrecision) {
            this.symbol = symbol;
            this.status = status;
            this.baseAsset = baseAsset;
            this.quoteAsset = quoteAsset;
            this.tickSize = tickSize;
            this.quotePrecision = quotePrecision;
        }

        public String getSymbol() {
            return symbol;
        }
        public String getStatus() {
            return status;
        }
        public String getBaseAsset() {
            return baseAsset;
        }
        public String getQuoteAsset() {
            return quoteAsset;
        }
        public String getTickSize() {
            return tickSize;
        } // USE THIS TO GET MINIMAL PRICE MOVE
        public String getQuotePrecision() {
            return quotePrecision;
        } // USE THIS TO GET PRECISION

        @Override
        public String toString() {
            return "SymbolInfo{" +
                    "symbol:" + symbol + " ," +
                    "status:" + status + " ," +
                    "baseAsset:" + baseAsset + " ," +
                    "quoteAsset:" + quoteAsset + " ," +
                    "tickSize:" + tickSize + " ," +
                    "quotePrecision:" + quotePrecision + "}";
        }
    }
    public Map<String, SymbolInfo> getSymbolInfoMap() {
        return symbolMap;
    }
}
