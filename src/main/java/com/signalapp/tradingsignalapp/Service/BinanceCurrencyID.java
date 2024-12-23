package com.signalapp.tradingsignalapp.Service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.*;

@Configuration
public class BinanceCurrencyID {

    private final BinanceExchangeInfo binanceExchangeInfo;

    public BinanceCurrencyID(BinanceExchangeInfo binanceExchangeInfo) {
        this.binanceExchangeInfo = binanceExchangeInfo;
    }
    @Bean
    public ArrayList<String> currencyIDs(){
        ArrayList<String> currencyIDs = new ArrayList<>();

        Map<String, BinanceExchangeInfo.SymbolInfo> symbolMap = binanceExchangeInfo.getSymbolInfoMap();

        for (BinanceExchangeInfo.SymbolInfo symbolInfo : symbolMap.values()) {
            currencyIDs.add(symbolInfo.getBaseAsset());
            currencyIDs.add(symbolInfo.getQuoteAsset());
        }

        Set<String> uniqueCurrencies = new HashSet<>(currencyIDs);
        Set<String> sortedCurrencies = new TreeSet<>(uniqueCurrencies);
        currencyIDs = new ArrayList<>(sortedCurrencies);
        return currencyIDs;
    }
}
