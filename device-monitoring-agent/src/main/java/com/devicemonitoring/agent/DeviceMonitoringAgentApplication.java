package com.devicemonitoring.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DeviceMonitoringAgentApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceMonitoringAgentApplication.class, args);
	}

	@org.springframework.context.annotation.Bean
	public org.springframework.web.reactive.function.client.WebClient.Builder webClientBuilder() {
		return org.springframework.web.reactive.function.client.WebClient.builder();
	}

}
