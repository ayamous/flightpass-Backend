package ma.itroad.ramflightpassusermanagement;

import ma.itroad.ramflightpassusermanagement.service.impl.UserServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RamFlightpassUserManagementMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RamFlightpassUserManagementMsApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(){
        return args->{
            UserServiceImpl userService =
                    new UserServiceImpl();
            userService.getUsersResource().list()
                    .forEach(userRepresentation -> {

                        System.out.print(userRepresentation.getEmail()+" ");
                        System.out.print(userRepresentation.getFirstName()+" ");
                        System.out.print(userRepresentation.getLastName()+" ");
                        System.out.print(userRepresentation.getCredentials()+" ");
                        System.out.println("=====================================");
                    });
        };
    }


}
