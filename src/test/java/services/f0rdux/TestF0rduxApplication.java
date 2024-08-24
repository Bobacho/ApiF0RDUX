package services.f0rdux;

import org.springframework.boot.SpringApplication;

public class TestF0rduxApplication {

	public static void main(String[] args) {
		SpringApplication.from(F0rduxApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
