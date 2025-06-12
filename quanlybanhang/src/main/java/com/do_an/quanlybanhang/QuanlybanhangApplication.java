package com.do_an.quanlybanhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class QuanlybanhangApplication {
	public static void main(String[] args) {
		SpringApplication.run(QuanlybanhangApplication.class, args);
	}
}
