package com.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value={"com.common", "com.sso"})
public class SSOApplication {
	public static void main(String[] args) {
		SpringApplication.run(SSOApplication.class, args);
	}

}
