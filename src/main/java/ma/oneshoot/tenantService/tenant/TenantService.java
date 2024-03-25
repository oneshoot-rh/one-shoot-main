package ma.oneshoot.tenantService.tenant;


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
    // abstraction
    public Boolean isTenantDomainAvailable(String domain) {
        return !this.existsByDomainName(domain);
    }

    public boolean existsByDomainName(String domainName) {
        return tenantRepository.existsByDomainName(domainName);
    }
}
