package ma.oneshoot.oneshootmain.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TenantRegistrationService {

    private UserRepository userRepository;
    private TenantService tenantService;

    @Autowired
    public TenantRegistrationService(UserRepository userRepository, TenantService tenantService) {
        this.userRepository = userRepository;
        this.tenantService = tenantService;
    }




    public Tenant registerTenant(Tenant tenant) {
        Objects.requireNonNull(tenant, "User cannot be null");
        // todo: pwd encoding
        userRepository.saveAll(tenant.getUsers());
        tenantService.saveTenant(tenant);
        tenantService.initDataBase(tenant.getOrganizationName());
        return tenant;
    }
}
