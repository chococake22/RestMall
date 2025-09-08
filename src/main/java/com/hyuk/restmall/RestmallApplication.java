package com.hyuk.restmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.hyuk.restmall")
@EnableJpaAuditing
public class RestmallApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestmallApplication.class, args);
	}

}
