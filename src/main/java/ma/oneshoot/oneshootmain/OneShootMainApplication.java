package ma.oneshoot.oneshootmain;

import lombok.extern.slf4j.Slf4j;
import ma.oneshoot.oneshootmain.tenant.Tenant;
import ma.oneshoot.oneshootmain.tenant.TenantRegistrationService;
import ma.oneshoot.oneshootmain.tenant.TenantRepository;
import ma.oneshoot.oneshootmain.tenant.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
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
	CommandLineRunner runner() {
		return args -> {
			long orgId = System.currentTimeMillis();
			Tenant tenant = Tenant.builder()
					.id(1L)
					.organizationName("org_"+ orgId)
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
			tenantRegistrationService.registerTenant(tenant);
		};
	}
}
