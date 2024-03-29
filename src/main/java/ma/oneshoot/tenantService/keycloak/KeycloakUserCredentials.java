package ma.oneshoot.tenantService.keycloak;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeycloakUserCredentials {
    private String type;
    private String value;
    private boolean temporary;
}
