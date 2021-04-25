package com.example.Market;

import com.example.Market.Controllers.Services.ExecuterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*")
public class MarketApplication {
	@Autowired
	private static ExecuterService executerService;

	public static void main(String[] args) {

		SpringApplication.run(MarketApplication.class, args);
		executerService.runJob(23,0,0);
	}

}
