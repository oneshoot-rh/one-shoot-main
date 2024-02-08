package ma.oneshoot.oneshootmain.config;


import ma.oneshoot.oneshootmain.tenant.TenantRepository;
import ma.oneshoot.oneshootmain.tenant.UserRepository;
import ma.oneshoot.oneshootmain.tenant.db.HibernateTenantIdentifierResolver;
import ma.oneshoot.oneshootmain.tenant.db.TenantContext;
import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Bean
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .locations("db/migration/default")
                .dataSource(dataSource)
                .schemas(TenantContext.DEFAULT_TENANT_ID)
                .load();
        flyway.migrate();
        return flyway;
    }

    @Bean
    CommandLineRunner commandLineRunner(TenantRepository repository, DataSource dataSource) {
        return args -> {
            repository.findAll().forEach(tenant -> {
                String tenantId = tenant.getOrganizationName();
                Flyway flyway = Flyway.configure()
                        .locations("db/migration/tenants")
                        .dataSource(dataSource)
                        .schemas(tenantId)
                        .load();
                flyway.migrate();
            });
        };
    }
}
