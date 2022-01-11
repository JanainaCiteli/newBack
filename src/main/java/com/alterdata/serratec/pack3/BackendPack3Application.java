package com.alterdata.serratec.pack3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BackendPack3Application {
	
	
	public static void main(String[] args) {
		SpringApplication.run(BackendPack3Application.class, args);
	}

}
