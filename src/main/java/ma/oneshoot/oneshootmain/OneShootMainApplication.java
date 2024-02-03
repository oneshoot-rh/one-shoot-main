package ma.oneshoot.oneshootmain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
@Slf4j
public class OneShootMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(OneShootMainApplication.class, args);
	}


	//@Bean
	CommandLineRunner runner() {
		return args -> {
			log.info("Cleaning upload-dir");
			Files.walk(Paths.get("upload-dir/"))
					.filter(Files::isDirectory)
					.forEach(path -> {
						try {
							Files.delete(path);
						} catch (IOException e) {
							log.error("Failed to delete directory {}", path.getFileName());
							log.error(e.getMessage());
						}
					});
		};
	}
}
