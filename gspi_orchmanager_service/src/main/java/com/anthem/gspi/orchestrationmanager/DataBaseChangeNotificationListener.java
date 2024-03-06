package com.anthem.gspi.orchestrationmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;

@Component
public class DataBaseChangeNotificationListener implements DatabaseChangeListener {

	@Autowired
	GSPIOrchManagerController orchManagerController;

	@Override
	public void onDatabaseChangeNotification(DatabaseChangeEvent arg0) {
		try {
			synchronized (orchManagerController) {
				orchManagerController.notify();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
