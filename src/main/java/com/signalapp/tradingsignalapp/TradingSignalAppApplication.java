package com.signalapp.tradingsignalapp;

import com.signalapp.tradingsignalapp.User.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class TradingSignalAppApplication {

	private static final Logger log = LoggerFactory.getLogger(TradingSignalAppApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(TradingSignalAppApplication.class, args);
	}
	@Bean
	public CommandLineRunner commandLineRunner(UserRepository userRepository) {
		return args -> {
//			User user1 = new User(1,"John","asdad",12.2);
//			User user2 = new User(2, "Luki", "adhakdshiil", 145.2);
//			User user3 = new User(3,"Ziut","aaassa",122.2);
//			userRepository.create(user1);
//			userRepository.create(user2);
//			userRepository.create(user3);
		};
	}
}

