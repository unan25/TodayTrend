package com.todaytrend.imageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ImageServiceApplication { //12

	public static void main(String[] args) {
		SpringApplication.run(ImageServiceApplication.class, args);
	}

}
