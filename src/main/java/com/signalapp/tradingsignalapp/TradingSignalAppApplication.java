package com.signalapp.tradingsignalapp;

import com.signalapp.tradingsignalapp.Service.BinanceAvailablePairs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TradingSignalAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingSignalAppApplication.class, args);
		BinanceAvailablePairs availablePairs = new BinanceAvailablePairs();
		System.out.println(availablePairs.TradingPairs());
	}
}
