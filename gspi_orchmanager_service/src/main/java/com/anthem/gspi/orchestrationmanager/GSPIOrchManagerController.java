package com.anthem.gspi.orchestrationmanager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = { "com.anthem.gspi" })
public class GSPIOrchManagerController extends SpringBootServletInitializer implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(GSPIOrchManagerController.class);

	boolean lockinginstances = true;

	@Autowired
	ApplicationContext application;

	@Autowired
	DataBaseChangeNotificationDAO dataBaseChangeNotificationDAO;

	public static void main(String[] args) {
		new SpringApplicationBuilder(GSPIOrchManagerController.class).web(WebApplicationType.NONE).run(args);
	}

	public void run(String... args) throws Exception {
		/*
		 * pollMethod(() -> { try { callGSPIServiceEndPoint(); } catch
		 * (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }, 10, TimeUnit.SECONDS);
		 */
		DataBaseNotificationProcessor dbNotification =
		application.getBean(DataBaseNotificationProcessor.class);
		dbNotification.prcsDbmsNotification();
		orchestrationManagementProcess();
	}

	private void orchestrationManagementProcess() throws InterruptedException {
		if (lockinginstances) {
			lockinginstances = false;
			synchronized (this) {
				while (true) {
					this.wait();
					callGSPIServiceEndPoint();
					dataBaseChangeNotificationDAO.deRegister();
					dataBaseChangeNotificationDAO.prcsCQN();
				}
			}
		}

	}

	private static void callGSPIServiceEndPoint() throws InterruptedException {
		log.info("polling.....");
		Thread.sleep(5000);
		RestTemplate restTemplate = new RestTemplate();
		SimpleClientHttpRequestFactory neww = new SimpleClientHttpRequestFactory();
		neww.setConnectTimeout(5000);
		restTemplate.setRequestFactory(neww);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> data = new HttpEntity<String>(headers);
		// System.out.println(environment.getProperty("webservice_url"));
		// "http://localhost:9080/v1/gbdsps/processsps"
		log.info("waiting");

		HttpEntity<String> result = restTemplate.exchange("http://gspi-web-services:9080/v1/gbdsps/processsps",
				HttpMethod.GET, data, String.class);

		log.info(result.getBody());

		log.info("Called from gspi_orchmanager");
	}

	private void pollMethod(Runnable method, long interval, TimeUnit minutes) {
		ScheduledExecutorService ex = Executors.newSingleThreadScheduledExecutor();
		ex.scheduleAtFixedRate(method, 0, interval, minutes);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(GSPIOrchManagerController.class);
	}

}
