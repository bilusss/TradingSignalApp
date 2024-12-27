package com.signalapp.tradingsignalapp.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class BinanceCurrentPriceScheduledFetcher {
    private static final Logger log = LoggerFactory.getLogger(BinanceCurrentPriceScheduledFetcher.class);
    private static final int THREAD_SLEEP_INTERVAL_MS = 1 * 30 * 1000; // (30s) Sleep interval in milliseconds

    private final BinanceCurrentPrice binanceCurrentPrice;

    public BinanceCurrentPriceScheduledFetcher(BinanceCurrentPrice binanceCurrentPrice) {
        this.binanceCurrentPrice = binanceCurrentPrice;
    }

    @Scheduled(fixedRate = THREAD_SLEEP_INTERVAL_MS)
    public void runTask() {
        String time = LocalTime.now().toString();
        log.info("Fetching current prices (" + time.split("\\.")[0] + ")");
        binanceCurrentPrice.fetchingPrices();
    }
}
