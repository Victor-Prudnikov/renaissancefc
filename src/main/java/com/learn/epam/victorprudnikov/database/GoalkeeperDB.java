package com.learn.epam.victorprudnikov.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.learn.epam.victorprudnikov.model.Goalkeeper;
import com.learn.epam.victorprudnikov.util.GoalkeeperComparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoalkeeperDB {
	
	private static final Logger logger = LogManager.getLogger();
	
	// Create and populate Goalkeeper table in H2 Database
	public void createPopulateH2() {
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			try(Connection conn = pool.getConnection()) {
				final String createGoalkeeperTable = "DROP TABLE IF EXISTS Goalkeeper;" 
	     				+ "CREATE TABLE Goalkeeper(" 
	     				+ "KeeperID INT NOT NULL AUTO_INCREMENT,"
	     				+ "KeeperNumber INT NOT NULL,"
	     				+ "KeeperName VARCHAR(30) NOT NULL DEFAULT '',"
	     				+ "KeeperSurname VARCHAR(40) NOT NULL DEFAULT '',"
	     				+ "KeeperBirthDay INT NOT NULL,"
	     				+ "KeeperBirthMonth INT NOT NULL,"
	     				+ "KeeperBirthYear INT NOT NULL,"
	     				+ "KeeperGames INT NOT NULL DEFAULT '0',"
	     				+ "KeeperGoals INT NOT NULL DEFAULT '0',"
	     				+ "KeeperHeight INT NOT NULL DEFAULT '0',"
	     				+ "KeeperWeight INT NOT NULL DEFAULT '0',"
	     				+ "GoalsConceded INT NOT NULL DEFAULT '0',"
	     				+ "PenaltySaved INT NOT NULL DEFAULT '0',"
	     				+ "KeeperPosition VARCHAR(20) NOT NULL DEFAULT '',"
	     				+ "PRIMARY KEY (KeeperID));";

	     		final String populateGoalkeeperTable = "INSERT INTO Goalkeeper VALUES"
	     				+ "('1', '1', 'Martin', 'Luther', '10', '10', '1983', '25', '1', '202', '105', '20', '5', 'Goalkeeper'),"
	     				+ "('2', '12', 'Niccolo', 'Machiavelli', '03', '04', '1989', '5', '0', '195', '96', '7', '0', 'Goalkeeper'),"
	     				+ "('3', '23', 'Thomas', 'More', '07', '01', '1998', '0', '0', '185', '80', '0', '0', 'Goalkeeper');";
	     		
     			try(Statement statement = conn.createStatement()) {
     				statement.executeUpdate(createGoalkeeperTable);
     				statement.executeUpdate(populateGoalkeeperTable);
     			}
			}
		}
		catch(Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
		}
	}
	
	// Retrieve goalkeepers from H2 Database
	public List<Goalkeeper> createList() {
		List<Goalkeeper> keepers = new LinkedList<>();
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			try(Connection conn = pool.getConnection();
					Statement statement = conn.createStatement()) {
				String query = "SELECT * FROM Goalkeeper";
				try(ResultSet result = statement.executeQuery(query)) {
					while (result.next()) {
						int id = result.getInt("KeeperID");
						int number = result.getInt("KeeperNumber");
						String name = result.getString("KeeperName");
						String surname = result.getString("KeeperSurname");
						int birthDay = result.getInt("KeeperBirthDay");
						int birthMonth = result.getInt("KeeperBirthMonth");
						int birthYear = result.getInt("KeeperBirthYear");
						int games = result.getInt("KeeperGames");
						int goals = result.getInt("KeeperGoals");
						int height = result.getInt("KeeperHeight");
						int weight = result.getInt("KeeperWeight");
						int goalsConceded = result.getInt("GoalsConceded");
						int penaltySaved = result.getInt("PenaltySaved");
						String position = result.getString("KeeperPosition");
						Goalkeeper keeper = new Goalkeeper(id, number, name, surname, birthDay, birthMonth, birthYear, games, 
								goals, height, weight, goalsConceded, penaltySaved, position);
						keepers.add(keeper);
					}
				}
			}
			keepers.sort(new GoalkeeperComparator());
		}
		catch(Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
		}
		return keepers;
	}
	
	// Retrieve one goalkeeper by his ID from H2 Database
	public Goalkeeper selectOne(int id) {
		Goalkeeper keeper = null;
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			try(Connection conn = pool.getConnection()) {
				String query = "SELECT * FROM Goalkeeper WHERE KeeperID=?";
				try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
					preparedStatement.setInt(1, id);
					try(ResultSet result = preparedStatement.executeQuery()) {
						if(result.next()) {
							int number = result.getInt("KeeperNumber");
							String name = result.getString("KeeperName");
							String surname = result.getString("KeeperSurname");
							int birthDay = result.getInt("KeeperBirthDay");
							int birthMonth = result.getInt("KeeperBirthMonth");
							int birthYear = result.getInt("KeeperBirthYear");
							int games = result.getInt("KeeperGames");
							int goals = result.getInt("KeeperGoals");
							int height = result.getInt("KeeperHeight");
							int weight = result.getInt("KeeperWeight");
							int goalsConceded = result.getInt("GoalsConceded");
							int penaltySaved = result.getInt("PenaltySaved");
							String position = result.getString("KeeperPosition");
							keeper = new Goalkeeper(id, number, name, surname, birthDay, birthMonth, birthYear, games, 
									goals, height, weight, goalsConceded, penaltySaved, position);
						}
					}
				}
			}
		}
		catch(Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
		}
		return keeper;
	}
	
	// Update goalkeeper record in H2 Database
	public String update(Goalkeeper keeper) {
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			try(Connection conn = pool.getConnection()) {
				String query = "UPDATE Goalkeeper SET KeeperNumber = ?, KeeperName = ?, KeeperSurname = ?, KeeperBirthDay = ?, "
						+ "KeeperBirthMonth = ?, KeeperBirthYear = ?, KeeperGames = ?, KeeperGoals = ?, KeeperHeight = ?, "
						+ "KeeperWeight = ?, GoalsConceded = ?, PenaltySaved = ?, KeeperPosition = ? WHERE KeeperID = ?";
				try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
					preparedStatement.setInt(1, keeper.getNumber());
					preparedStatement.setString(2, keeper.getName());
					preparedStatement.setString(3, keeper.getSurname());
					preparedStatement.setInt(4, keeper.getBirthDay());
					preparedStatement.setInt(5, keeper.getBirthMonth());
					preparedStatement.setInt(6, keeper.getBirthYear());
					preparedStatement.setInt(7, keeper.getGames());
					preparedStatement.setInt(8, keeper.getGoals());
					preparedStatement.setInt(9, keeper.getHeight());
					preparedStatement.setInt(10, keeper.getWeight());
					preparedStatement.setInt(11, keeper.getGoalsConceded());
					preparedStatement.setInt(12, keeper.getPenaltySaved());
					preparedStatement.setString(13, keeper.getPosition());
					preparedStatement.setInt(14, keeper.getId());
					if (preparedStatement.executeUpdate() != 0) {
						return "Success";
					}
					else {
						return "Failed to update - " + keeper.getName() + " " + keeper.getSurname() + " (id=" + keeper.getId() + ").";
					}
				}
			}
		}
		catch(Exception ex){
			logger.error(ex.toString());
			ex.printStackTrace();
			return ex.toString();
		}
	}
	
	// Update goalkeeper record in H2 Database
	public String delete(int id) {
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			try(Connection conn = pool.getConnection()) {
				String query = "DELETE FROM Goalkeeper WHERE KeeperID = ?";
				try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
					preparedStatement.setInt(1, id);
					if (preparedStatement.executeUpdate() != 0) {
						return "Success";
					}
					else {
						return "Failed to delete - id=" + id;
					}
				}
			}
		}
		catch(Exception ex){
			logger.error(ex.toString());
			ex.printStackTrace();
			return ex.toString();
		}
	}
	
	// Insert new goalkeeper record into H2 Database
	public String insert(Goalkeeper keeper) {
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			try(Connection conn = pool.getConnection()) {
				String query = "INSERT INTO Goalkeeper (KeeperNumber, KeeperName, KeeperSurname, KeeperBirthDay, KeeperBirthMonth, "
						+ "KeeperBirthYear, KeeperGames, KeeperGoals, KeeperHeight, KeeperWeight, GoalsConceded, PenaltySaved, "
						+ "KeeperPosition) Values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
					preparedStatement.setInt(1, keeper.getNumber());
					preparedStatement.setString(2, keeper.getName());
					preparedStatement.setString(3, keeper.getSurname());
					preparedStatement.setInt(4, keeper.getBirthDay());
					preparedStatement.setInt(5, keeper.getBirthMonth());
					preparedStatement.setInt(6, keeper.getBirthYear());
					preparedStatement.setInt(7, keeper.getGames());
					preparedStatement.setInt(8, keeper.getGoals());
					preparedStatement.setInt(9, keeper.getHeight());
					preparedStatement.setInt(10, keeper.getWeight());
					preparedStatement.setInt(11, keeper.getGoalsConceded());
					preparedStatement.setInt(12, keeper.getPenaltySaved());
					preparedStatement.setString(13, keeper.getPosition());
					if (preparedStatement.executeUpdate() != 0) {
						return "Success";
					}
					else {
						return "Failed to insert - " + keeper.getName() + " " + keeper.getSurname() + ".";
					}
				}
			}
		}
		catch(Exception ex){
			logger.error(ex.toString());
			ex.printStackTrace();
			return ex.toString();
		}
	}
}
