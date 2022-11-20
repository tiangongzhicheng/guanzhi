package com.moyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GuanzhiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuanzhiApplication.class, args);
	}
}
