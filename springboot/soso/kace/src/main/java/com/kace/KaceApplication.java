package com.kace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
@ComponentScan(value={"com.common", "com.kace"})
public class KaceApplication {
	public static void main(String[] args) {
		SpringApplication.run(KaceApplication.class, args);
	}

}
