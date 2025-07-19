package um.haberes.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HaberesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaberesApplication.class, args);
	}

}
