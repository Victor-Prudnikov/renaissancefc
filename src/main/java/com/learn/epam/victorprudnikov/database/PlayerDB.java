package com.learn.epam.victorprudnikov.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.learn.epam.victorprudnikov.model.Player;
import com.learn.epam.victorprudnikov.util.FieldPlayerComparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerDB {

	private static final Logger logger = LogManager.getLogger();

	// Create and populate Player table in H2 Database
	public void createPopulateH2() {
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			try (Connection conn = pool.getConnection()) {
				final String createPlayerTable = "DROP TABLE IF EXISTS Player;"
						+ "CREATE TABLE Player("
						+ "PlayerID INT NOT NULL AUTO_INCREMENT,"
						+ "PlayerNumber INT NOT NULL,"
						+ "PlayerName VARCHAR(30) NOT NULL DEFAULT '',"
						+ "PlayerSurname VARCHAR(40) NOT NULL DEFAULT '',"
						+ "PlayerBirthDay INT NOT NULL,"
						+ "PlayerBirthMonth INT NOT NULL,"
						+ "PlayerBirthYear INT NOT NULL,"
						+ "PlayerGames INT NOT NULL DEFAULT '0',"
						+ "PlayerGoals INT NOT NULL DEFAULT '0',"
						+ "PlayerHeight INT NOT NULL DEFAULT '0',"
						+ "PlayerWeight INT NOT NULL DEFAULT '0',"
						+ "PlayerPosition VARCHAR(20) NOT NULL DEFAULT '',"
						+ "PRIMARY KEY (PlayerID));";

				final String populatePlayerTable = "INSERT INTO Player VALUES"
						+ "('1', '2', 'Dante', 'Alighieri', '31', '04', '1985', '30', '4', '180', '78', 'Defender'),"
						+ "('2', '3', 'Francesco', 'Petrarca', '20', '06', '1994', '27', '1', '192', '90', 'Defender'),"
						+ "('3', '4', 'Giovanni', 'Boccaccio', '16', '05', '1993', '25', '0', '189', '87', 'Defender'),"
						+ "('4', '5', 'William', 'Shakespeare', '26', '03', '1994', '26', '3', '175', '73', 'Defender'),"
						+ "('5', '6', 'Raffael', 'Santi', '06', '03', '1983', '28', '1', '176', '74', 'Midfielder'),"
						+ "('6', '7', 'Tiziano', 'Vecellio', '01', '11', '1990', '27', '4', '174', '72', 'Midfielder'),"
						+ "('7', '8', 'Giorgione', '', '30', '10', '1992', '8', '1', '186', '82', 'Midfielder'),"
						+ "('8', '9', 'Michelangelo', 'Buonarroti', '06', '02', '1985', '29', '25', '188', '85', 'Striker'),"
						+ "('9', '10', 'Leonardo', 'da Vinci', '15', '03', '1992', '30', '10', '169', '70', 'Midfielder'),"
						+ "('10', '11', 'Donato', 'Bramante', '25', '10', '1994', '20', '8', '182', '78', 'Striker'),"
						+ "('11', '13', 'Francois', 'Rabelais', '17', '08', '1996', '12', '1', '185', '82', 'Defender'),"
						+ "('12', '14', 'Miguel', 'de Cervantes', '29', '08', '1987', '8', '0', '182', '80', 'Defender'),"
						+ "('13', '15', 'Lope', 'de Vega', '25', '10', '1992', '7', '0', '198', '95', 'Defender'),"
						+ "('14', '16', 'Geoffrey', 'Chaucer', '10', '05', '2001', '1', '0', '184', '78', 'Defender'),"
						+ "('15', '17', 'Donatello', '', '25', '09', '1986', '18', '3', '166', '66', 'Midfielder'),"
						+ "('16', '18', 'Sandro', 'Botticelli', '01', '02', '1995', '16', '2', '178', '77', 'Midfielder'),"
						+ "('17', '19', 'Jacopo', 'Sansovino', '02', '06', '1986', '6', '0', '174', '73', 'Midfielder'),"
						+ "('18', '20', 'Pieter Paul', 'Rubens', '28', '05', '1997', '2', '0', '172', '71', 'Midfielder'),"
						+ "('19', '21', 'Filippo', 'Brunelleschi', '10', '07', '1984', '10', '7', '186', '86', 'Striker'),"
						+ "('20', '22', 'Andrea', 'Palladio', '30', '10', '1998', '4', '1', '176', '74', 'Striker');";

				try (Statement statement = conn.createStatement()) {
					statement.executeUpdate(createPlayerTable);
					statement.executeUpdate(populatePlayerTable);
				}
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
		}
	}

	// Retrieve players from H2 Database
	public List<Player> createList() {
		List<Player> players = new LinkedList<>();
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			try (Connection conn = pool.getConnection(); Statement statement = conn.createStatement()) {
				String query = "SELECT * FROM Player";
				try (ResultSet result = statement.executeQuery(query)) {
					while (result.next()) {
						int id = result.getInt("PlayerID");
						int number = result.getInt("PlayerNumber");
						String name = result.getString("PlayerName");
						String surname = result.getString("PlayerSurname");
						int birthDay = result.getInt("PlayerBirthDay");
						int birthMonth = result.getInt("PlayerBirthMonth");
						int birthYear = result.getInt("PlayerBirthYear");
						int games = result.getInt("PlayerGames");
						int goals = result.getInt("PlayerGoals");
						int height = result.getInt("PlayerHeight");
						int weight = result.getInt("PlayerWeight");
						String position = result.getString("PlayerPosition");
						Player player = new Player(id, number, name, surname, birthDay, birthMonth, birthYear, games,
								goals, height, weight, position);
						players.add(player);
					}
				}
			}
			players.sort(new FieldPlayerComparator());
		} catch (Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
		}
		return players;
	}
	
	// Retrieve one player by his ID from H2 Database
	public Player selectOne(int id) {
		Player player = null;
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			try(Connection conn = pool.getConnection()) {
				String query = "SELECT * FROM Player WHERE PlayerID=?";
				try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
					preparedStatement.setInt(1, id);
					try(ResultSet result = preparedStatement.executeQuery()) {
						if(result.next()) {
							int number = result.getInt("PlayerNumber");
							String name = result.getString("PlayerName");
							String surname = result.getString("PlayerSurname");
							int birthDay = result.getInt("PlayerBirthDay");
							int birthMonth = result.getInt("PlayerBirthMonth");
							int birthYear = result.getInt("PlayerBirthYear");
							int games = result.getInt("PlayerGames");
							int goals = result.getInt("PlayerGoals");
							int height = result.getInt("PlayerHeight");
							int weight = result.getInt("PlayerWeight");
							String position = result.getString("PlayerPosition");
							player = new Player(id, number, name, surname, birthDay, birthMonth, birthYear, games, 
									goals, height, weight, position);
						}
					}
				}
			}
		}
		catch(Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
		}
		return player;
	}
	
	// Update player record in H2 Database
	public String update(Player player) {
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			try(Connection conn = pool.getConnection()) {
				String query = "UPDATE Player SET PlayerNumber = ?, PlayerName = ?, PlayerSurname = ?, PlayerBirthDay = ?, "
						+ "PlayerBirthMonth = ?, PlayerBirthYear = ?, PlayerGames = ?, PlayerGoals = ?, PlayerHeight = ?, "
						+ "PlayerWeight = ?, PlayerPosition = ? WHERE PlayerID = ?";
				try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
					preparedStatement.setInt(1, player.getNumber());
					preparedStatement.setString(2, player.getName());
					preparedStatement.setString(3, player.getSurname());
					preparedStatement.setInt(4, player.getBirthDay());
					preparedStatement.setInt(5, player.getBirthMonth());
					preparedStatement.setInt(6, player.getBirthYear());
					preparedStatement.setInt(7, player.getGames());
					preparedStatement.setInt(8, player.getGoals());
					preparedStatement.setInt(9, player.getHeight());
					preparedStatement.setInt(10, player.getWeight());
					preparedStatement.setString(11, player.getPosition());
					preparedStatement.setInt(12, player.getId());
					if (preparedStatement.executeUpdate() != 0) {
						return "Success";
					}
					else {
						return "Failed to update - " + player.getName() + " " + player.getSurname() + " (id=" + player.getId() + ").";
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
	
	// Delete player record by his ID from H2 Database
	public String delete(int id) {
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			try(Connection conn = pool.getConnection()) {
				String query = "DELETE FROM Player WHERE PlayerID = ?";
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

	// Insert new player record into H2 Database
	public String insert(Player player) {
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			try(Connection conn = pool.getConnection()) {
				String query = "INSERT INTO Player (PlayerNumber, PlayerName, PlayerSurname, PlayerBirthDay, PlayerBirthMonth, "
						+ "PlayerBirthYear, PlayerGames, PlayerGoals, PlayerHeight, PlayerWeight, PlayerPosition) "
						+ "Values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				try(PreparedStatement preparedStatement = conn.prepareStatement(query)) {
					preparedStatement.setInt(1, player.getNumber());
					preparedStatement.setString(2, player.getName());
					preparedStatement.setString(3, player.getSurname());
					preparedStatement.setInt(4, player.getBirthDay());
					preparedStatement.setInt(5, player.getBirthMonth());
					preparedStatement.setInt(6, player.getBirthYear());
					preparedStatement.setInt(7, player.getGames());
					preparedStatement.setInt(8, player.getGoals());
					preparedStatement.setInt(9, player.getHeight());
					preparedStatement.setInt(10, player.getWeight());
					preparedStatement.setString(11, player.getPosition());
					if (preparedStatement.executeUpdate() != 0) {
						return "Success";
					}
					else {
						return "Failed to insert - " + player.getName() + " " + player.getSurname() + ".";
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
