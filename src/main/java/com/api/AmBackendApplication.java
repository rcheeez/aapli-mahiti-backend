package com.api;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Aapli Mahiti Backend",
		version = "1.0.0",
		description = "This is Aapli Mahiti Rest api backend applicaton.",
		contact = @Contact(
			name = "Archies Gurav",
			email = "archies.gurav18@gmail.com"
		)
	)
)
public class AmBackendApplication {
	
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(AmBackendApplication.class, args);
	}

}
