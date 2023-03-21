package br.com.badr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("br.com.badr")
public class AppStart {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AppStart.class, args);
	}

}
