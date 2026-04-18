package api.velasco.factura.appdistribuidas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "api.velasco.factura.appdistribuidas")
public class AppdistribuidasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppdistribuidasApplication.class, args);
	}

}
