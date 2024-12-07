package com.signalapp.tradingsignalapp.Controller;

import com.signalapp.tradingsignalapp.Service.BinanceHistoricalData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class ChartController {
    private final List<String> AvailablePairs;
    private final BinanceHistoricalData binanceHistoricalData;

    @Autowired
    public ChartController(List<String> AvailablePairs, BinanceHistoricalData binanceHistoricalData) {
        this.AvailablePairs = AvailablePairs;
        this.binanceHistoricalData = binanceHistoricalData;
    }

    @GetMapping("/chart/{symbol}")
    public String getChart(@PathVariable String symbol, Model model) {
        if (!AvailablePairs.contains(symbol)) {
            model.addAttribute("error", "Trading pair " + symbol + " is not available.");
            return "error";
        }
        String interval = "15m";// Default interval
        var historicalData = binanceHistoricalData.getHistoricalData(symbol, interval);

        if (historicalData.isEmpty()) {
            model.addAttribute("error", "Failed to load historical data for symbol: " + symbol);
            return "error";
        }

        model.addAttribute("pair", symbol);
        model.addAttribute("historicalData", historicalData);
        return "chart";
    }
}
