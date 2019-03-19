package com.bb.auth;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.context.annotation.Configuration;


import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@Configuration
@SpringBootApplication


public class AuthApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(AuthApplication.class, args);
	}



}
