package ma.oneshoot.oneshootmain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestOneShootMainApplication {

	public static void main(String[] args) {
		SpringApplication.from(OneShootMainApplication::main).with(TestOneShootMainApplication.class).run(args);
	}

}
