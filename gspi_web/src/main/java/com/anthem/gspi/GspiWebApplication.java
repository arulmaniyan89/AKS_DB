package com.anthem.gspi;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan(basePackages = "com.anthem.gspi")
@Configuration
public class GspiWebApplication{

	public static void main(String[] args) {
		Properties props = new Properties();
		// This property is used to allow the circular dependencies between the beans/classes.
		props.put("spring.main.allow-circular-references", "true");
		SpringApplication application = new SpringApplication(GspiWebApplication.class);	       
        application.setDefaultProperties(props);
        application.run(args);
		
	}

}
