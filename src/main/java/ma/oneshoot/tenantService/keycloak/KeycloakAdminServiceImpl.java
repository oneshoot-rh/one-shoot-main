package ma.oneshoot.tenantService.keycloak;

import java.util.List;
import java.util.Objects;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.oneshoot.tenantService.tenant.User;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakAdminServiceImpl implements KeycloakAdminService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;
    
    @Override
    public User createUser(@Valid User user) {
        var firstName = user.getName().contains(" ") ? user.getName().split(" ")[0] : user.getName();
        var lastName = user.getName().contains(" ") ? user.getName().split(" ")[1] : "";
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(user.getPassword());
        credentialRepresentation.setTemporary(true);
        List<CredentialRepresentation> credentials = List.of(credentialRepresentation);

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getTenant().getRequestorProfessionalEmail());
        userRepresentation.setEmail(user.getTenant().getRequestorProfessionalEmail());
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);
        userRepresentation.setCredentials(credentials);

        RealmResource realmResource =  keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        log.info("Creating user in keycloak for tenant: " + user.getTenant().getId());
        log.info("User: ", user);
        
        var response=  usersResource.create(userRepresentation);

        if(Objects.equals(response.getStatus(), 201)) return user;
        return null;

    }

    @Override
    public void deleteUser(Long userId) {
    }

    @Override
    public void updateUser(User user) {
    }

    @Override
    public void addRedirectUrl(String domain) {
        String reactDomain = domain + ":3000";
        String reactValidRedirectURL = reactDomain+"/signin-callback.html";
        String angularValidRedirectURL = domain + ":4000";
        log.info("Adding redirect url to keycloak client for domain: " + domain + " with redirect url: " +reactValidRedirectURL + " and " + angularValidRedirectURL);
        RealmResource realmResource =  keycloak.realm(realm);
        var  client = realmResource.clients().findByClientId("frontend_client").get(0);
        client.getRedirectUris().add(reactValidRedirectURL);
        client.getRedirectUris().add(angularValidRedirectURL);
        client.getWebOrigins().add(reactDomain);
        client.getWebOrigins().add(angularValidRedirectURL);
        client.getRedirectUris().add(reactDomain);
        client.getRedirectUris().add(angularValidRedirectURL);
        realmResource.clients().get(client.getId()).update(client);
        log.info("Redirect url added successfully to keycloak client for domain: " + domain + " with redirect url: " +reactValidRedirectURL + " and " + angularValidRedirectURL);
    }

    
}
