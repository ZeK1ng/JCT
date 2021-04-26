package com.example.Market;

import com.example.Market.service.ExecuterService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketApplication {

	private static ExecuterService executerService = new ExecuterService();


	public static void main(String[] args) {

		SpringApplication.run(MarketApplication.class, args);

		executerService.runJob(23,0,0);
	}

}
