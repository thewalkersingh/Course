package com.course.config;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(@NonNull CorsRegistry registry) {
				
				registry.addMapping("/course/**")
						  .allowedOrigins(
							  "http://localhost:5173",
							  "http://localhost:8080/swagger-ui/**")
						  .allowedMethods("GET", "POST", "PUT", "DELETE");
			}
		};
	}
	
}