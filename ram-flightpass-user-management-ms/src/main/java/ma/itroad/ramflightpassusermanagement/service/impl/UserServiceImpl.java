package ma.itroad.ramflightpassusermanagement.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ma.itroad.ramflightpassusermanagement.model.bean.UserDto;
import ma.itroad.ramflightpassusermanagement.service.IUserService;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceImpl implements IUserService {


//    @Value("${keycloak.auth-server-url}")
    private String host = "http://localhost:8080/auth";
//    @Value("${keycloak.realm}")
    private String realm = "flightpass-realm";

//    @Value("${keycloak.resource}")
    private String resource = "admin-cli";

//    @Value("${admin.username}")
    private String username = "redouane.elaouni@edu.uca.ac.ma";
//    @Value("${admin.password}")
    private String password = "admin";


    @Override
    public ResponseEntity<?> createUser(UserDto userDto) {

        Keycloak kc = KeycloakBuilder.builder()
                .serverUrl(host)
                .realm(realm)
                .clientId(resource)
                .username(username)
                .password(password)
                .build();

        // Define user
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        // Get realm
        RealmResource realmResource = kc.realm(realm);
        UsersResource usersRessource = realmResource.users();

        // Create user (requires manage-users role)
        Response response = usersRessource.create(user);
        System.out.printf("Repsonse: %s %s%n", response.getStatusInfo(), response.getStatus());
        System.out.println(response.getLocation());
        String userId = CreatedResponseUtil.getCreatedId(response);

        System.out.printf("User created with userId: %s%n", userId);

        // Define password credential
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(userDto.getPassword());

        UserResource userResource = usersRessource.get(userId);

        // Set password credential
        userResource.resetPassword(passwordCred);

        //Send Email Verification

        userResource.executeActionsEmail("my-react-client", "http://localhost:3000/",
                Arrays.asList(""));
        return new ResponseEntity<>(HttpStatus.valueOf(response.getStatus()));
    }

    @Override
    public ResponseEntity<?> resetPassword(String email) {

        UsersResource usersRessource = getUsersResource();

        List<UserRepresentation> search = usersRessource.search(email);
        Optional<UserRepresentation> first = search.stream().findFirst();


        if (!first.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"utilisateur introuvable pour cet Email");
        }

        String userId = first.get().getId();
        UserResource userResource = usersRessource.get(userId);
        userResource.executeActionsEmail(Arrays.asList(""));




        return null;
    }

    public  UsersResource getUsersResource() {
        Keycloak kc = KeycloakBuilder.builder()
                .serverUrl(host)
                .realm(realm)
                .clientId(resource)
                .username(username)
                .password(password)
                .build();

        RealmResource realmResource = kc.realm(realm);
        return realmResource.users();
    }



}