package ma.itroad.ram.flightpass.service;

import ma.itroad.ram.flightpass.model.bean.UserBean;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface UserService {

    UserBean getUserInfos();
    String getUserAccountId();
    AccessToken getAccessToken();
    void logout();
    List<UserRepresentation> getAllUsers();
    UserRepresentation getUserById(String id);

}
