package com.app.OtherService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OtherServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtherServiceApplication.class, args);
	}

}
