package com.signalapp.tradingsignalapp.Controller;

import com.signalapp.tradingsignalapp.Service.BinanceHistoricalData;

import com.signalapp.tradingsignalapp.Service.BinancePriceChange;
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
    private final BinancePriceChange binancePriceChange;

    @Autowired
    public ChartController(List<String> AvailablePairs, BinanceHistoricalData binanceHistoricalData, BinancePriceChange binancePriceChange) {
        this.AvailablePairs = AvailablePairs;
        this.binanceHistoricalData = binanceHistoricalData;
        this.binancePriceChange = binancePriceChange;
    }

    @GetMapping({"/chart/{symbol}", "/chart/{symbol}/", "/chart/{symbol}/{interval}", "/chart/{symbol}/{interval}/"})
    public String getChart(@PathVariable String symbol, @PathVariable(required = false) String interval, Model model) {
        // Model allows us to pass data into html file
        if (!AvailablePairs.contains(symbol)) {
            model.addAttribute("error", "Trading pair " + symbol + " is not available");
            return "error";
        }
        // Available interval: 1s 1m 3m 5m 15m 30m 1h 2h 4h 6h 8h 12h 1d 3d 1w 1M
        String tempInterval = (interval == null) ? "1s" : interval; // Default interval 1s

        var historicalData = binanceHistoricalData.getHistoricalData(symbol, tempInterval);
        var priceChange = binancePriceChange.getPriceChange(symbol);

        if (historicalData.isEmpty()) {
            model.addAttribute("error", "Failed to load historical data for symbol: " + symbol);
            return "error";
        }

        String symbolText = symbol + " on " + interval;
        String priceChangeText = "";
        if (priceChange.startsWith("-")){
            priceChangeText = "(" + priceChange + "%)";
        }else{
            priceChangeText = "(+" + priceChange + "%)";
        }
        String priceChangeClass = priceChange.startsWith("-") ? "red" :
                ("0.000".equals(priceChange) || "0".equals(priceChange) ? "gray" : "green");

        model.addAttribute("symbolText", symbolText);
        model.addAttribute("priceChangeClass", priceChangeClass);
        model.addAttribute("priceChangeText", priceChangeText);
        model.addAttribute("pair", symbol);
        model.addAttribute("historicalData", historicalData);
        model.addAttribute("interval", tempInterval);
        return "chart";
    }
}
