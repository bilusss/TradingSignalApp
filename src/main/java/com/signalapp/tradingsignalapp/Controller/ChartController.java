package com.signalapp.tradingsignalapp.Controller;

import com.signalapp.tradingsignalapp.Service.BinanceAvailablePairs;
import com.signalapp.tradingsignalapp.Service.BinanceCurrencyID;
import com.signalapp.tradingsignalapp.Service.BinanceExchangeInfo;
import com.signalapp.tradingsignalapp.Service.BinanceHistoricalData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class ChartController {
    private final List<String> availablePairs;
    private final List<String> currencyIDs;
    private final BinanceHistoricalData binanceHistoricalData;
    private final BinanceExchangeInfo binanceExchangeInfo;


    @Autowired
    public ChartController(BinanceAvailablePairs binanceAvailablePairs, BinanceCurrencyID binanceCurrencyID, BinanceHistoricalData binanceHistoricalData, BinanceExchangeInfo binanceExchangeInfo) {
        this.availablePairs = binanceAvailablePairs.availablePairs();
        this.currencyIDs = binanceCurrencyID.currencyIDs();
        this.binanceHistoricalData = binanceHistoricalData;
        this.binanceExchangeInfo = binanceExchangeInfo;
    }

    @GetMapping({"/chart/{symbol}", "/chart/{symbol}/", "/chart/{symbol}/{tempInterval}", "/chart/{symbol}/{tempInterval}/"})
    public String getChart(@PathVariable String symbol, @PathVariable(required = false) String tempInterval, Model model) {
        // Model allows us to pass data into html file

        // Checking if symbol is on Binance trading list
        if (!availablePairs.contains(symbol)) {
            model.addAttribute("error", "Trading pair " + symbol + " is not available");
            return "error";
        }
        // Available interval: 1s 1m 3m 5m 15m 30m 1h 2h 4h 6h 8h 12h 1d 3d 1w 1M
        String interval = (tempInterval == null) ? "1s" : tempInterval; // Default interval 1s

        var historicalData = binanceHistoricalData.getHistoricalData(symbol, interval);

        if (historicalData.isEmpty()) {
            model.addAttribute("error", "Failed to load historical data for symbol: " + symbol);
            return "error";
        }

        String tick;
        BinanceExchangeInfo.SymbolInfo symbolInfo = binanceExchangeInfo.getSymbolInfo(symbol);
        tick = symbolInfo.getTickSize();
        String quotePrecision = symbolInfo.getQuotePrecision();

        // Passing attributes to html file
        model.addAttribute("pair", symbol);
        model.addAttribute("historicalData", historicalData);
        model.addAttribute("interval", interval);
        model.addAttribute("tick", tick);
        model.addAttribute("quotePrecision", quotePrecision);
        model.addAttribute("currencyIds", currencyIDs);
        model.addAttribute("availablePairs", availablePairs);
        return "chart";
    }
}
