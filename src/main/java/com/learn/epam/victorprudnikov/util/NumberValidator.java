package com.learn.epam.victorprudnikov.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learn.epam.victorprudnikov.database.ConnectionPool;

public class NumberValidator {
	
	private static final Logger logger = LogManager.getLogger();
	
	public boolean numberIsOccupied(int number) {
		List<Integer> numbers = new ArrayList<>();
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			try(Connection conn = pool.getConnection();
					Statement statement = conn.createStatement()) {
				String query = "SELECT KeeperNumber FROM Goalkeeper";
				try(ResultSet result = statement.executeQuery(query)) {
					while (result.next()) {
						numbers.add(result.getInt("KeeperNumber"));
					}
				}
				query = "SELECT PlayerNumber FROM Player";
				try(ResultSet result = statement.executeQuery(query)) {
					while (result.next()) {
						numbers.add(result.getInt("PlayerNumber"));
					}
				}
			}
		}
		catch(Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
		}
		if (numbers.contains(number)) {
			return true;
		} else {
			return false;
		}
	}
}
