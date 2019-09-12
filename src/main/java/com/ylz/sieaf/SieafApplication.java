package com.ylz.sieaf;

import com.ylz.sieaf.listener.ApplicationStartedEventListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SieafApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SieafApplication.class);
		app.addListeners(new ApplicationStartedEventListener());
		app.run(args);
	}

}
