package com.signalapp.tradingsignalapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Configuration
public class BinanceAvailablePairs {

    private final BinanceExchangeInfo binanceExchangeInfo;

    @Autowired
    public BinanceAvailablePairs(BinanceExchangeInfo binanceExchangeInfo) {
        this.binanceExchangeInfo = binanceExchangeInfo;
    }

    @Bean
    public ArrayList<String> tradingPairs() throws IOException {
        ArrayList<String> availablePairs = new ArrayList<>();

        Map<String, BinanceExchangeInfo.SymbolInfo> symbolMap = binanceExchangeInfo.getSymbolInfoMap();

        for (BinanceExchangeInfo.SymbolInfo symbolInfo : symbolMap.values()) {
            if ("TRADING".equals(symbolInfo.getStatus())) {
                availablePairs.add(symbolInfo.getSymbol());
            }
        }
        return availablePairs;
    }
}
