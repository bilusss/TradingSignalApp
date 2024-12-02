package com.signalapp.tradingsignalapp.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
public class BinanceHistoricalData {
    @Value("${binance.apiKey}")
    private String apiKey;

    @Value("${binance.secretKey}")
    private String secretKey;

}
