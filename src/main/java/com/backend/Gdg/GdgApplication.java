package com.backend.Gdg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GdgApplication {

	public static void main(String[] args) {
		SpringApplication.run(GdgApplication.class, args);
	}

}
