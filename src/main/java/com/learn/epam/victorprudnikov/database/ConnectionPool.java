package com.learn.epam.victorprudnikov.database;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.h2.jdbcx.JdbcConnectionPool;

public class ConnectionPool {

	private static final Logger logger = LogManager.getLogger();
	private DataSource dataSource;
	
	// Create connection to the in-memory H2 embedded database.
	public ConnectionPool() {
		try {
			dataSource = JdbcConnectionPool.create(
					"jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "user", "password");

		} catch (Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
		}
	}

	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (Exception ex) {
			logger.error(ex.toString());
			return null;
		}
	}

	private static class ConnectionPoolHolder {
		public static final ConnectionPool INSTANCE = new ConnectionPool();
	}

	public static ConnectionPool getInstance() {
		return ConnectionPoolHolder.INSTANCE;
	}
}