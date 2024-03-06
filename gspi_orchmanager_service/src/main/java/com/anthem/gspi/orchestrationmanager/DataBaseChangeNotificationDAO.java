package com.anthem.gspi.orchestrationmanager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleStatement;
import oracle.jdbc.dcn.DatabaseChangeRegistration;

@Component
public class DataBaseChangeNotificationDAO {

	@Autowired
	DataSource dataSource;

	@Autowired
	DataBaseChangeNotificationListener dBaseNotificationListener;

	Connection hikariCon = null;

	DatabaseChangeRegistration dcr = null;

	public void prcsCQN() {
		OracleConnection conn = null;
		Statement stmt = null;

		try {
			conn = getOracleConnection();
			Properties prop = new Properties();
			prop.setProperty(OracleConnection.DCN_NOTIFY_ROWIDS, "true");
			prop.setProperty(OracleConnection.DCN_QUERY_CHANGE_NOTIFICATION, "true");
			prop.setProperty(OracleConnection.DCN_BEST_EFFORT, "true");
			if (conn != null) {
				dcr = conn.registerDatabaseChangeNotification(prop);
				dcr.addListener(dBaseNotificationListener);
				stmt = conn.createStatement();
				((OracleStatement) stmt).setDatabaseChangeRegistration(dcr);
				stmt.execute("SELECT person_id from persons");
			}
		} catch (Exception ex) {
			if (conn != null) {
				try {
					conn.unregisterDatabaseChangeNotification(dcr);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	private OracleConnection getOracleConnection() throws SQLException {
		OracleConnection conn = null;
		hikariCon = DataSourceUtils.getConnection(dataSource);
		if (hikariCon.isWrapperFor(OracleConnection.class)) {
			conn = hikariCon.unwrap(OracleConnection.class);
		}
		return conn;
	}

	public void deRegister() {
		OracleConnection conn = null;
		try {
			conn = getOracleConnection();
			if (conn != null && dcr != null) {
				conn.unregisterDatabaseChangeNotification(dcr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (hikariCon != null) {
				try {
					hikariCon.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
