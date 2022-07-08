package ma.itroad.ram.flightpass.service.impl;

import ma.itroad.ram.flightpass.model.bean.UserBean;
import ma.itroad.ram.flightpass.service.UserService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {



    @Autowired
    private HttpServletRequest request;

    @Autowired
    UsersResource adminClient;


    @Override
    public UserBean getUserInfos() {


        AccessToken accessToken = getAccessToken();
        Map<String, Object> otherClaims = accessToken.getOtherClaims();
        String mobile = (String) otherClaims.get("mobile");


        String country = accessToken.getAddress().getCountry();
        String streetAddress = accessToken.getAddress().getStreetAddress();
        String city = accessToken.getAddress().getLocality();
        String postalCode = accessToken.getAddress().getPostalCode();
        String email = accessToken.getEmail();
        String lastname = accessToken.getFamilyName();
        String firstname = accessToken.getGivenName();
        String subject = accessToken.getSubject();

        return new UserBean(
                firstname, lastname, email, subject, mobile, streetAddress, postalCode, city, country
        );
    }

    @Override
    public String getUserAccountId() {

        AccessToken accessToken = getAccessToken();

        if (accessToken != null) {
            return accessToken.getSubject();
        }
        return null;
    }

    @Override
    public AccessToken getAccessToken() {
//        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth);
        if (!auth.getPrincipal().equals("anonymousUser")) {
            KeycloakPrincipal principal = (KeycloakPrincipal) auth.getPrincipal();
            KeycloakSecurityContext session = principal.getKeycloakSecurityContext();


            AccessToken accessToken = session.getToken();
            return accessToken;
        }

        return null;
    }

    @Override
    public void logout() {
        try {
            request.logout();
        } catch (ServletException e) {
            e.printStackTrace();
        }
        ;
    }

    @Override
    public List<UserRepresentation> getAllUsers() {
        return adminClient.list();
    }

    @Override
    public UserRepresentation getUserById(String id) {
        return adminClient.get(id).toRepresentation();
    }

//    public UsersResource getUserResource() {
//
//        Keycloak kc = KeycloakBuilder.builder()
//                .serverUrl(host)
//                .realm(realm)
//                .username(username)
//                .password(password)
//                .clientId(resource)
//                .clientSecret("ddf1def6-ea66-4c44-962d-96ed2f545f9a")
//                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(20).build())
//                .build();
//
//        // Get realm
//        RealmResource realmResource = kc.realm(realm);
//
//        return realmResource.users();
//
//    }

}
