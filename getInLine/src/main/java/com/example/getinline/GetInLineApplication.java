package com.example.getinline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScans;

@ConfigurationPropertiesScan
@SpringBootApplication
public class GetInLineApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetInLineApplication.class, args);
	}

}
