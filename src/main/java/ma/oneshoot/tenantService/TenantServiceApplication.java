package ma.oneshoot.tenantService;

import lombok.extern.slf4j.Slf4j;
import ma.oneshoot.tenantService.tenant.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@Slf4j
@EnableTransactionManagement
@EnableMethodSecurity
//@EnableWebFluxSecurity
//@EnableAspectJAutoProxy
public class TenantServiceApplication {
	@Autowired
	TenantRegistrationService tenantRegistrationService;
	@Autowired
	TenantRepository tenantRepository;

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	public static void main(String[] args) {
		SpringApplication.run(TenantServiceApplication.class, args);
	}

	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(request ->
                    request
                        // .requestMatchers("/actuator/**").permitAll()
						.requestMatchers("/cl/**").permitAll()
						.requestMatchers("/api/cl/**").permitAll()
						// .requestMatchers("/api/cl/**").permitAll()
						// .requestMatchers("/api/cl/tenants/**").permitAll()
						.requestMatchers(HttpMethod.POST,"/cl/subscriptions/**").permitAll()
						.requestMatchers(HttpMethod.POST,"/api/cl/subscriptions/**").permitAll()
                        .anyRequest().authenticated()
						//.anyRequest().permitAll()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .build();
    }

	// produces a message using kafka template
	// @Bean
	// CommandLineRunner runner2() {
	// 	return args -> {
	// 		kafkaTemplate.send("tenant", "myapplication");
	// 	};
	// }

	//@Bean
	@Transactional(rollbackFor = Exception.class)
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
			tenantRegistrationService.registerTenant(tenant, SubscriptionType.PROFESSIONAL,true);
		};
	}


	// @KafkaListener(topics = "tenant", groupId = "group_id")
	// public void consume(Tenant tenant) {
	// 	log.info("Consumed tenant: {}", tenant);
	// }
}
