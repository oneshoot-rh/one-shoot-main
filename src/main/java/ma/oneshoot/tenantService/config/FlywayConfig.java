package ma.oneshoot.tenantService.config;


import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ma.oneshoot.tenantService.tenant.TenantRepository;
import ma.oneshoot.tenantService.tenant.UserRepository;
import ma.oneshoot.tenantService.tenant.db.HibernateTenantIdentifierResolver;
import ma.oneshoot.tenantService.tenant.db.TenantContext;

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
                String Schema = new StringBuilder().append("tsedb_").append(tenant.getDomainName()).toString();
                Flyway flyway = Flyway.configure()
                        .locations("db/migration/tenants")
                        .dataSource(dataSource)
                        .schemas(Schema)
                        .load();
                flyway.migrate();
            });
        };
    }
}
