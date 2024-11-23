package polytech.service_emplacements;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "polytech.service_emplacements.model") // ajout de ca
public class ServiceEmplacementsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceEmplacementsApplication.class, args);
	}

}
