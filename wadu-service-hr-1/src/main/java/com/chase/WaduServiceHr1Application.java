package com.chase;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan({"com.chase.config","com.chase.reader","com.chase.writer","com.chase.processor"})
public class WaduServiceHr1Application {

	public static void main(String[] args) {
		SpringApplication.run(WaduServiceHr1Application.class, args);
	}

}
