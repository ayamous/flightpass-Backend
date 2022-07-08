package ma.itroad.ram.flightPass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RamFlightPassGatewayMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RamFlightPassGatewayMsApplication.class, args);
	}

}
