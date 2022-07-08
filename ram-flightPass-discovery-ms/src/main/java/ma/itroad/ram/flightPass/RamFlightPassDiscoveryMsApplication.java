package ma.itroad.ram.flightPass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class RamFlightPassDiscoveryMsApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(RamFlightPassDiscoveryMsApplication.class, args);
	}

}
