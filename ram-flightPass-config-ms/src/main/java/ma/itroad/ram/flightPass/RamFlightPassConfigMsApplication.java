package ma.itroad.ram.flightPass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class RamFlightPassConfigMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RamFlightPassConfigMsApplication.class, args);
	}

}
