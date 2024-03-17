package ma.oneshoot.oneshootmain;

import lombok.extern.slf4j.Slf4j;
import ma.oneshoot.oneshootmain.tenant.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Slf4j
@EnableTransactionManagement
//@EnableWebFluxSecurity
//@EnableAspectJAutoProxy
public class OneShootMainApplication {
	@Autowired
	TenantRegistrationService tenantRegistrationService;
	@Autowired
	TenantRepository tenantRepository;

	public static void main(String[] args) {
		SpringApplication.run(OneShootMainApplication.class, args);
	}

	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(request ->
                    request
                        .requestMatchers("/actuator/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .build();
    }


	//@Bean
	CommandLineRunner runner() {
		return args -> {
			long orgId = System.currentTimeMillis();
			Tenant tenant = Tenant.builder()
					.id(1L)
					.organizationName("org"+ orgId)
					.build();
			User user = User.builder()
					.id(1L)
					.username("user_"+orgId)
					.name("user_name_"+orgId)
					.password("default")
					.tenant(tenant)
					.build();
			log.info("User: {}", user);
			tenant.addUser(user);
			tenantRepository.save(tenant);
			tenantRegistrationService.registerTenant(tenant, SubscriptionType.FREE);
		};
	}
}
