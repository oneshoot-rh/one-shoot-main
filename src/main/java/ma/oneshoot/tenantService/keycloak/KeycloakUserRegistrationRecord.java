package ma.oneshoot.tenantService.keycloak;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor  
public class KeycloakUserRegistrationRecord {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private boolean emailVerified;
    private boolean enabled;
    private List<KeycloakUserCredentials> credentials;    
}
