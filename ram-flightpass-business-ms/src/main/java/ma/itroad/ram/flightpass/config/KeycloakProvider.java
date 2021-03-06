package ma.itroad.ram.flightpass.config;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KeycloakProvider {

	@SuppressWarnings("unchecked")
	public String getConnectedUser() {
		KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return kp.getKeycloakSecurityContext().getToken().getPreferredUsername();
	}

	@SuppressWarnings("unchecked")
	public String getClaimValue(String key) {
		KeycloakPrincipal<KeycloakSecurityContext> kp = (KeycloakPrincipal<KeycloakSecurityContext>) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		AccessToken token = kp.getKeycloakSecurityContext().getToken();
		Map<String, Object> otherClaims = token.getOtherClaims();
		return otherClaims.get(key) != null ? (String) otherClaims.get(key) : null;
	}
}