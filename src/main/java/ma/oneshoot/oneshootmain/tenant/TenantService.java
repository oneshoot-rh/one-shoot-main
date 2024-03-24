package ma.oneshoot.oneshootmain.tenant;


import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@RequiredArgsConstructor
public class TenantService {

    private final DataSource dataSource;
    private final TenantRepository tenantRepository;


    public void initDataBase(String scheme) {
        Flyway flyway = Flyway.configure()
                .locations("db/migration/tenants")
                .dataSource(dataSource)
                .schemas(scheme)
                .load();
        flyway.migrate();
    }

    public Tenant saveTenant(Tenant tenant) {
        return tenantRepository.save(tenant);

    }

    public Boolean isTenantDomainAvailable(String domain) {
        return tenantRepository.findByOrganizationName(domain).isEmpty();
    }
}
