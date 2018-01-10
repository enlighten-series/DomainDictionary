package org.enlightenseries.DomainDictionary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DomainDictionaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DomainDictionaryApplication.class, args);
	}
}
