package com.oranbyte.ecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:lang/messages.properties")
@ComponentScan(basePackages = "com.oranbyte")
public class EComApplication {

	public static void main(String[] args) {
		SpringApplication.run(EComApplication.class, args);
	}

}
