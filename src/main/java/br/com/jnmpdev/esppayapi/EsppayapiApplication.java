package br.com.jnmpdev.esppayapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EsppayapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EsppayapiApplication.class, args);
	}

}
