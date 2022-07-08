package ma.itroad.ram.flightpass.config;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KecyloakAdminClient {

    //    @Value("${admin-keycloak.auth-server-url}")
    private String host = "http://localhost:8080/auth";
    //    @Value("${admin-keycloak.realm}")
    private String realm = "flightpass-realm";

    //    @Value("${admin-keycloak.resource}")
    private String resource = "admin-cli";

    //    @Value("${admin-keycloak.username}")
    private String username = "redouane.elaouni@edu.uca.ac.ma";
    //    @Value("${admin-keycloak.password}")
    private String password = "admin";

    @Bean
    public UsersResource getUserResource() {

        Keycloak kc = KeycloakBuilder.builder()
                .serverUrl(host)
                .realm(realm)
                .username(username)
                .password(password)
                .clientId(resource)
                .clientSecret("ddf1def6-ea66-4c44-962d-96ed2f545f9a")
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(20).build())
                .build();

        // Get realm
        RealmResource realmResource = kc.realm(realm);

        return realmResource.users();

    }
}
