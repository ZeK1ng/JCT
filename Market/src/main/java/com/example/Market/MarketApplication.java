package com.example.Market;

import com.example.Market.Controllers.Services.ExecuterService;
import com.example.Market.Controllers.Services.ReportService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*")
public class MarketApplication {

	private static ExecuterService executerService = new ExecuterService();


	public static void main(String[] args) {

		SpringApplication.run(MarketApplication.class, args);

		executerService.runJob(23,0,0);
	}

}
