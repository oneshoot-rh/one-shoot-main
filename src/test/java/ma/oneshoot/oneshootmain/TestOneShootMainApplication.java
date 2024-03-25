package ma.oneshoot.oneshootmain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

import ma.oneshoot.tenantService.TenantServiceApplication;

@TestConfiguration(proxyBeanMethods = false)
public class TestOneShootMainApplication {

	public static void main(String[] args) {
		SpringApplication.from(TenantServiceApplication::main).with(TestOneShootMainApplication.class).run(args);
	}

}
