package ma.oneshoot.tenantService.keycloak;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import ma.oneshoot.tenantService.tenant.User;

public interface KeycloakAdminService {

    public User createUser(@Valid User user);

    public void deleteUser(@NotNull Long userId);

    public void updateUser(@Valid User user);
    
}
