package com.example.CryptoPortolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.CryptoPortolio")
public class CryptoPortolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(CryptoPortolioApplication.class, args);
	}

}
