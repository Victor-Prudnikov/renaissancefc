package com.learn.epam.victorprudnikov.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.learn.epam.victorprudnikov.database.CoachDB;
import com.learn.epam.victorprudnikov.database.GoalkeeperDB;
import com.learn.epam.victorprudnikov.database.PlayerDB;
import com.learn.epam.victorprudnikov.model.*;
import com.learn.epam.victorprudnikov.util.EmptyNameException;
import com.learn.epam.victorprudnikov.util.EmptyNumberException;
import com.learn.epam.victorprudnikov.util.NumberIsOccupiedException;
import com.learn.epam.victorprudnikov.util.NumberValidator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//The servlet for updating coach, goalkeeper or player record in the database.
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger();
	private CoachDB coachDB = new CoachDB();
	private PlayerDB playerDB = new PlayerDB();
	private GoalkeeperDB goalkeeperDB = new GoalkeeperDB();
	private NumberValidator numberValidator = new NumberValidator();

	public EditServlet() {
		super();
	}
	
	//The constructor for the unit testing.
	EditServlet(CoachDB coachDB, PlayerDB playerDB, GoalkeeperDB goalkeeperDB, NumberValidator numberValidator) {
		super();
		this.coachDB = coachDB;
		this.playerDB = playerDB;
		this.goalkeeperDB = goalkeeperDB;
		this.numberValidator = numberValidator;
	}	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int id;
			String typeRequest = request.getParameter("typerequest");
			if ((request.getParameter("id") == null) || (typeRequest == null)) {
				logger.error("ID or typeRequest = null");
				request.setAttribute("error", "ID or typeRequest = null");
				getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
			} else {
				id = Integer.parseInt(request.getParameter("id"));
				switch (typeRequest) {
				case "coaches": {
					Coach coach = coachDB.selectOne(id);
					if (coach != null) {
						request.setAttribute("coach", coach);
						getServletContext().getRequestDispatcher("/editcoach.jsp").forward(request, response);
					} else {
						logger.error("Failed to find the coach in the database.");
						request.setAttribute("error", "There is no such coach in the database.");
						getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
					}
					break;
				}
				case "keepers": {
					Goalkeeper keeper = goalkeeperDB.selectOne(id);
					if (keeper != null) {
						request.setAttribute("keeper", keeper);
						getServletContext().getRequestDispatcher("/editgoalkeeper.jsp").forward(request, response);
					} else {
						logger.error("Failed to find the goalkeeper in the database.");
						request.setAttribute("error", "There is no such goalkeeper in the database.");
						getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
					}
					break;
				}
				case "players": {
					Player player = playerDB.selectOne(id);
					if (player != null) {
						request.setAttribute("player", player);
						getServletContext().getRequestDispatcher("/editfieldplayer.jsp").forward(request, response);
					} else {
						logger.error("Failed to find the player in the database.");
						request.setAttribute("error", "There is no such player in the database.");
						getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
					}
					break;
				}
				default: {
					logger.error("typeRequest is unknown.");
					request.setAttribute("error", "typeRequest is unknown.");
					getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
				}
				}
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
			request.setAttribute("error", ex.toString());
			getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int id;
			String typeRequest = request.getParameter("typerequest");
			if ((request.getParameter("id") == null) || (typeRequest == null)) {
				logger.error("ID or typeRequest = null");
				request.setAttribute("error", "ID or typeRequest = null");
				getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
			} else {
				id = Integer.parseInt(request.getParameter("id"));
				switch (typeRequest) {
				case "coaches": {
					String coachName = request.getParameter("CoachName");
					String coachSurname = request.getParameter("CoachSurname");
					if (((coachName == null) || coachName.equals("")) && ((coachSurname == null) || coachSurname.equals(""))) {
						throw new EmptyNameException();
					}
					int coachBirthDay = Integer.parseInt((request.getParameter("CoachBirthDate")).substring(8));
					//Need to subtract 1 from month because in Calendar objects months starts from 0.
					int coachBirthMonth = Integer.parseInt((request.getParameter("CoachBirthDate")).substring(5, 7)) - 1;
					int coachBirthYear = Integer.parseInt((request.getParameter("CoachBirthDate")).substring(0, 4));
					int coachGames;
					if ((request.getParameter("CoachGames") == null) || (request.getParameter("CoachGames").equals(""))) {
						coachGames = 0;
					} else {
						coachGames = Integer.parseInt(request.getParameter("CoachGames"));
					}
					String coachPost = request.getParameter("CoachPost");
					Coach coach = new Coach(id, coachName, coachSurname, coachBirthDay, coachBirthMonth, coachBirthYear,
							coachGames, coachPost);
					String updateMessage = coachDB.update(coach);
					if (updateMessage.equals("Success")) {
						logger.info("Updated coach record - " + coach.getName() + " " + coach.getSurname() + 
								" (id="+ coach.getId() + ").");
						response.sendRedirect(request.getContextPath() + "/coachesview");
					} else {
						logger.error(updateMessage);
						request.setAttribute("error", updateMessage);
						getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
					}
					break;
				}
				case "keepers": {
					int keeperGames, keeperGoals, keeperHeight, keeperWeight, goalsConceded, penaltySaved;
					if ((request.getParameter("KeeperNumber") == null) || (request.getParameter("KeeperNumber").equals(""))) {
						throw new EmptyNumberException();
					}
					int keeperNumber = Integer.parseInt(request.getParameter("KeeperNumber"));
					int keeperOldNumber = Integer.parseInt(request.getParameter("KeeperOldNumber"));
					//Check if the number is occupied by other player.
					if ((keeperNumber != keeperOldNumber) && (numberValidator.numberIsOccupied(keeperNumber))) {
						throw new NumberIsOccupiedException();
					}
					String keeperName = request.getParameter("KeeperName");
					String keeperSurname = request.getParameter("KeeperSurname");
					if (((keeperName == null) || keeperName.equals("")) && ((keeperSurname == null) || keeperSurname.equals(""))) {
						throw new EmptyNameException();
					}
					int keeperBirthDay = Integer.parseInt((request.getParameter("KeeperBirthDate")).substring(8));
					//Need to subtract 1 from month because in Calendar objects months starts from 0.
					int keeperBirthMonth = Integer.parseInt((request.getParameter("KeeperBirthDate")).substring(5, 7)) - 1;
					int keeperBirthYear = Integer.parseInt((request.getParameter("KeeperBirthDate")).substring(0, 4));
					if ((request.getParameter("KeeperGames") == null) || (request.getParameter("KeeperGames").equals(""))) {
						keeperGames = 0;
					} else {
						keeperGames = Integer.parseInt(request.getParameter("KeeperGames"));
					}
					if ((request.getParameter("KeeperGoals") == null) || (request.getParameter("KeeperGoals").equals(""))) {
						keeperGoals = 0;
					} else {
						keeperGoals = Integer.parseInt(request.getParameter("KeeperGoals"));
					}
					if ((request.getParameter("KeeperHeight") == null)
							|| (request.getParameter("KeeperHeight").equals(""))) {
						keeperHeight = 0;
					} else {
						keeperHeight = Integer.parseInt(request.getParameter("KeeperHeight"));
					}
					if ((request.getParameter("KeeperWeight") == null)
							|| (request.getParameter("KeeperWeight").equals(""))) {
						keeperWeight = 0;
					} else {
						keeperWeight = Integer.parseInt(request.getParameter("KeeperWeight"));
					}
					if ((request.getParameter("GoalsConceded") == null)
							|| (request.getParameter("GoalsConceded").equals(""))) {
						goalsConceded = 0;
					} else {
						goalsConceded = Integer.parseInt(request.getParameter("GoalsConceded"));
					}
					if ((request.getParameter("PenaltySaved") == null)
							|| (request.getParameter("PenaltySaved").equals(""))) {
						penaltySaved = 0;
					} else {
						penaltySaved = Integer.parseInt(request.getParameter("PenaltySaved"));
					}
					Goalkeeper keeper = new Goalkeeper(id, keeperNumber, keeperName, keeperSurname, keeperBirthDay,
							keeperBirthMonth, keeperBirthYear, keeperGames, keeperGoals, keeperHeight, keeperWeight,
							goalsConceded, penaltySaved, "Goalkeeper");
					String updateMessage = goalkeeperDB.update(keeper);
					if (updateMessage.equals("Success")) {
						logger.info("Updated keeper record - " + keeper.getName() + " " + keeper.getSurname() + 
								" (id="+ keeper.getId() + ").");
						response.sendRedirect(request.getContextPath() + "/teamview");
					} else {
						logger.error(updateMessage);
						request.setAttribute("error", updateMessage);
						getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
					}
					break;
				}
				case "players": {
					int playerGames, playerGoals, playerHeight, playerWeight;
					if ((request.getParameter("PlayerNumber") == null) || (request.getParameter("PlayerNumber").equals(""))) {
						throw new EmptyNumberException();
					}
					int playerNumber = Integer.parseInt(request.getParameter("PlayerNumber"));
					int playerOldNumber = Integer.parseInt(request.getParameter("PlayerOldNumber"));
					//Check if the number is occupied by other player.
					if ((playerNumber != playerOldNumber) && (numberValidator.numberIsOccupied(playerNumber))) {
						throw new NumberIsOccupiedException();
					}
					String playerName = request.getParameter("PlayerName");
					String playerSurname = request.getParameter("PlayerSurname");
					if (((playerName == null) || playerName.equals("")) && ((playerSurname == null) || playerSurname.equals(""))) {
						throw new EmptyNameException();
					}
					int playerBirthDay = Integer.parseInt((request.getParameter("PlayerBirthDate")).substring(8));
					//Need to subtract 1 from month because in Calendar objects months starts from 0.
					int playerBirthMonth = Integer.parseInt((request.getParameter("PlayerBirthDate")).substring(5, 7)) - 1;
					int playerBirthYear = Integer.parseInt((request.getParameter("PlayerBirthDate")).substring(0, 4));
					if ((request.getParameter("PlayerGames") == null) || (request.getParameter("PlayerGames").equals(""))) {
						playerGames = 0;
					} else {
						playerGames = Integer.parseInt(request.getParameter("PlayerGames"));
					}
					if ((request.getParameter("PlayerGoals") == null) || (request.getParameter("PlayerGoals").equals(""))) {
						playerGoals = 0;
					} else {
						playerGoals = Integer.parseInt(request.getParameter("PlayerGoals"));
					}
					if ((request.getParameter("PlayerHeight") == null) || (request.getParameter("PlayerHeight").equals(""))) {
						playerHeight = 0;
					} else {
						playerHeight = Integer.parseInt(request.getParameter("PlayerHeight"));
					}
					if ((request.getParameter("PlayerWeight") == null) || (request.getParameter("PlayerWeight").equals(""))) {
						playerWeight = 0;
					} else {
						playerWeight = Integer.parseInt(request.getParameter("PlayerWeight"));
					}
					String playerPosition = request.getParameter("PlayerPosition");
					Player player = new Player(id, playerNumber, playerName, playerSurname, playerBirthDay, playerBirthMonth, 
							playerBirthYear, playerGames, playerGoals, playerHeight, playerWeight, playerPosition);
					String updateMessage = playerDB.update(player);
					if (updateMessage.equals("Success")) {
						logger.info("Updated player record - " + player.getName() + " " + player.getSurname() + 
								" (id="+ player.getId() + ").");
						response.sendRedirect(request.getContextPath() + "/teamview");
					} else {
						logger.error(updateMessage);
						request.setAttribute("error", updateMessage);
						getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
					}
					break;
				}
				default: {
					logger.error("typeRequest is unknown, cannot edit an unknown entity");
					request.setAttribute("error", "typeRequest is unknown, cannot edit an unknown entity.");
					getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
				}
				}
			}
		} catch (EmptyNameException | NumberIsOccupiedException | EmptyNumberException ex) {
			logger.error(ex.toString());
			request.setAttribute("error", ex.getMessage());
			getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
		} catch (IndexOutOfBoundsException ex) {
			logger.error("Invalid birth date - " + ex.toString());
			request.setAttribute("error", "Invalid birth date.");
			getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
		} catch (Exception ex) {
			logger.error(ex.toString());
			request.setAttribute("error", ex.toString());
			getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
		}
	}
}
