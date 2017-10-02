package org.enoy.awesomeradio.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.enoy.awesomeradio")
public class AwesomeRadioBoot {

	public static void main(String[] args) {
		SpringApplication.run(AwesomeRadioBoot.class, args);
	}

}
