package com.clubeevantagens.authmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CluBeeVantagensAuthMicroserviceApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(CluBeeVantagensAuthMicroserviceApplication.class, args);
	}

	// CrossOrigin global
	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**")
				.allowedMethods("*")
				.allowedHeaders("*")
				.allowedOrigins("http://localhost:5173", "http://localhost:8081","http://localhost:3000", "http://192.268.0.50:8081", "http://192.168.0.50:8081");


	}
}