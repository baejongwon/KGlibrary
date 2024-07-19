package com.kg.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
//import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1000) //spring session 기본 30분이나 수정 함
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})

public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

}
