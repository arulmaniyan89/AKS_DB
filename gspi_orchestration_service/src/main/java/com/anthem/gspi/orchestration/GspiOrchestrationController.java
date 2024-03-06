package com.anthem.gspi.orchestration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Configuration
@RestController
@RequestMapping("/v1" + "/gbdsps")
@ComponentScan("com.anthem.gspi")
@EnableCaching
public class GspiOrchestrationController extends SpringBootServletInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(GspiOrchestrationController.class);

	public static void main(String[] args) {
		SpringApplication.run(GspiOrchestrationController.class, args);
	}

	@GetMapping(value = "/processsps")
	public ResponseEntity<Object> processJSON() {
		LOG.info("Welcome to Docker");
		return new ResponseEntity<>("Hello Docker", HttpStatus.OK);

	}

}