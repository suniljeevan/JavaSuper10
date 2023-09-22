package edu.sunny.tool.greatfactorial;

import edu.sunny.tool.greatfactorial.array.ArrayFactorialLauncher;
import edu.sunny.tool.greatfactorial.executor.ExecutorArrayFactorialLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GreatFactorialApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GreatFactorialApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		StringFactorial.main(args);
//		ForkJoinFactorial.main(args);
//		ExecutorArrayFactorialLauncher.main(args);
		ArrayFactorialLauncher.main(args);
	}
}
