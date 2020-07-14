package com.learn.epam.victorprudnikov.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.learn.epam.victorprudnikov.model.Coach;
import com.learn.epam.victorprudnikov.util.CoachComparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CoachDB {

	private static final Logger logger = LogManager.getLogger();

	// Create and populate Coach table in H2 Database
	public void createPopulateH2() {
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			try (Connection conn = pool.getConnection()) {
				final String createCoachTable = "DROP TABLE IF EXISTS Coach;" + "CREATE TABLE Coach("
						+ "CoachID INT NOT NULL AUTO_INCREMENT," + "CoachName VARCHAR(30) NOT NULL DEFAULT '',"
						+ "CoachSurname VARCHAR(40) NOT NULL DEFAULT ''," + "CoachBirthDay INT NOT NULL,"
						+ "CoachBirthMonth INT NOT NULL," + "CoachBirthYear INT NOT NULL,"
						+ "CoachGames INT NOT NULL DEFAULT '0'," + "CoachPost VARCHAR(20) NOT NULL DEFAULT '',"
						+ "PRIMARY KEY (CoachID));";

				final String populateCoachTable = "INSERT INTO Coach VALUES"
						+ "('1', 'Nicolaus', 'Copernicus', '19', '01', '1973', '30', 'Head Coach'),"
						+ "('2', 'Giordano', 'Bruno', '11', '00', '1975', '25', 'Assistent Coach'),"
						+ "('3', 'Galileo', 'Galilei', '15', '01', '1964', '30', 'Assistent Coach'),"
						+ "('4', 'Nicolaus', 'Cusanus', '28', '08', '1979', '30', 'Goalkeeper Coach');";

				try (Statement statement = conn.createStatement()) {
					statement.executeUpdate(createCoachTable);
					statement.executeUpdate(populateCoachTable);
				}
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
		}
	}

	// Retrieve coaches from H2 Database
	public List<Coach> createList() {
		List<Coach> coaches = new LinkedList<>();
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			try (Connection conn = pool.getConnection(); Statement statement = conn.createStatement()) {
				String query = "SELECT * FROM Coach";
				try (ResultSet result = statement.executeQuery(query)) {
					while (result.next()) {
						int id = result.getInt("CoachID");
						String name = result.getString("CoachName");
						String surname = result.getString("CoachSurname");
						int birthDay = result.getInt("CoachBirthDay");
						int birthMonth = result.getInt("CoachBirthMonth");
						int birthYear = result.getInt("CoachBirthYear");
						int games = result.getInt("CoachGames");
						String post = result.getString("CoachPost");
						Coach coach = new Coach(id, name, surname, birthDay, birthMonth, birthYear, games, post);
						coaches.add(coach);
					}
				}
			}
			coaches.sort(new CoachComparator());
		} catch (Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
		}
		return coaches;
	}
	
	// Retrieve one coach by his ID from H2 Database
	public Coach selectOne(int id) {
		Coach coach = null;
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			try (Connection conn = pool.getConnection()) {
				String query = "SELECT * FROM Coach WHERE CoachID=?";
				try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
					preparedStatement.setInt(1, id);
					try (ResultSet result = preparedStatement.executeQuery()) {
						if (result.next()) {
							String name = result.getString("CoachName");
							String surname = result.getString("CoachSurname");
							int birthDay = result.getInt("CoachBirthDay");
							int birthMonth = result.getInt("CoachBirthMonth");
							int birthYear = result.getInt("CoachBirthYear");
							int games = result.getInt("CoachGames");
							String post = result.getString("CoachPost");
							coach = new Coach(id, name, surname, birthDay, birthMonth, birthYear, games, post);
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
		}
		return coach;
	}
	
	// Update coach record in H2 Database
	public String update(Coach coach) {
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			try (Connection conn = pool.getConnection()) {
				String query = "UPDATE Coach SET CoachName = ?, CoachSurname = ?, CoachBirthDay = ?, "
						+ "CoachBirthMonth = ?, CoachBirthYear = ?, CoachGames = ?, CoachPost = ? WHERE CoachID = ?";
				try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
					preparedStatement.setString(1, coach.getName());
					preparedStatement.setString(2, coach.getSurname());
					preparedStatement.setInt(3, coach.getBirthDay());
					preparedStatement.setInt(4, coach.getBirthMonth());
					preparedStatement.setInt(5, coach.getBirthYear());
					preparedStatement.setInt(6, coach.getGames());
					preparedStatement.setString(7, coach.getPost());
					preparedStatement.setInt(8, coach.getId());
					if (preparedStatement.executeUpdate() != 0) {
						return "Success";
					} else {
						return "Failed to update - " + coach.getName() + " " + coach.getSurname() + " (id="
								+ coach.getId() + ").";
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
			return ex.toString();
		}
	}

	// Delete coach record by his ID from H2 Database
	public String delete(int id) {
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			try (Connection conn = pool.getConnection()) {
				String query = "DELETE FROM Coach WHERE CoachID = ?";
				try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
					preparedStatement.setInt(1, id);
					if (preparedStatement.executeUpdate() != 0) {
						return "Success";
					} else {
						return "Failed to delete - id=" + id;
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
			return ex.toString();
		}
	}
	
	// Insert new coach record into H2 Database
	public String insert(Coach coach) {
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			try (Connection conn = pool.getConnection()) {
				String query = "INSERT INTO Coach (CoachName, CoachSurname, CoachBirthDay, CoachBirthMonth, "
						+ "CoachBirthYear, CoachGames, CoachPost) Values (?, ?, ?, ?, ?, ?, ?)";
				try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
					preparedStatement.setString(1, coach.getName());
					preparedStatement.setString(2, coach.getSurname());
					preparedStatement.setInt(3, coach.getBirthDay());
					preparedStatement.setInt(4, coach.getBirthMonth());
					preparedStatement.setInt(5, coach.getBirthYear());
					preparedStatement.setInt(6, coach.getGames());
					preparedStatement.setString(7, coach.getPost());
					if (preparedStatement.executeUpdate() != 0) {
						return "Success";
					} else {
						return "Failed to insert - " + coach.getName() + " " + coach.getSurname() + ".";
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
			return ex.toString();
		}
	}
}
