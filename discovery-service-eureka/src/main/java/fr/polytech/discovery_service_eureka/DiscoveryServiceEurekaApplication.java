package fr.polytech.discovery_service_eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DiscoveryServiceEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServiceEurekaApplication.class, args);
	}

}
