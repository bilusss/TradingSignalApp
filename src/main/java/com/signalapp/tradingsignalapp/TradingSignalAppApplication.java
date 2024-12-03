package com.signalapp.tradingsignalapp;

import com.signalapp.tradingsignalapp.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class TradingSignalAppApplication {

	private static final Logger log = LoggerFactory.getLogger(TradingSignalAppApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(TradingSignalAppApplication.class, args);
	}
}

