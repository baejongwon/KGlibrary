package com.kg.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EnableRedisHttpSession
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})

public class LibraryApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

}
