package com.tiagotaquelim.kindlerecaller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class KindleRecallerApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(KindleRecallerApplication.class, args);
		// Arrays.stream(context.getBeanDefinitionNames()).sorted().forEach(System.out::println);

	}
}
