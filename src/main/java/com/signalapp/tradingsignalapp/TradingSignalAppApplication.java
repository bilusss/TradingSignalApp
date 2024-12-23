package com.signalapp.tradingsignalapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class TradingSignalAppApplication {

	private static final Logger log = LoggerFactory.getLogger(TradingSignalAppApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(TradingSignalAppApplication.class, args);
	}
	//Please do not remove those lines otherwise BinanceExchangeInfo won't work :)
	//START
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	//END
}

