package com.anthem.gspi.orchestrationmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataBaseNotificationProcessor {

	@Autowired
	DataBaseChangeNotificationDAO dataBaseChangeNotificationDAO;
	
	public void prcsDbmsNotification() {
		dataBaseChangeNotificationDAO.prcsCQN();
	}
	
}
