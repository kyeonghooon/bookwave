package com.library.bookwave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BookwaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookwaveApplication.class, args);
	}

}
