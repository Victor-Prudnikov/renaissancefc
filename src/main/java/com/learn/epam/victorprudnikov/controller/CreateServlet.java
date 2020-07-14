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

//The servlet for creating coach, goalkeeper or player record in the database.
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger();
	private CoachDB coachDB = new CoachDB();
	private PlayerDB playerDB = new PlayerDB();
	private GoalkeeperDB goalkeeperDB = new GoalkeeperDB();
	private NumberValidator numberValidator = new NumberValidator();
	
	public CreateServlet() {
		super();
	}
	
	//The constructor for the unit testing.
	CreateServlet(CoachDB coachDB, PlayerDB playerDB, GoalkeeperDB goalkeeperDB, NumberValidator numberValidator) {
		super();
		this.coachDB = coachDB;
		this.playerDB = playerDB;
		this.goalkeeperDB = goalkeeperDB;
		this.numberValidator = numberValidator;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String createType = request.getParameter("createlist");
			if (createType == null) {
				logger.error("Error while creating new record - createType is null.");
				request.setAttribute("error", "createType is null");
				getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
			} else {
				switch (createType) {
				case "coach": {
					getServletContext().getRequestDispatcher("/createcoach.jsp").forward(request, response);
					break;
				}
				case "goalkeeper": {
					getServletContext().getRequestDispatcher("/creategoalkeeper.jsp").forward(request, response);
					break;
				}
				case "fieldplayer": {
					getServletContext().getRequestDispatcher("/createfieldplayer.jsp").forward(request, response);
					break;
				}
				default: {
					logger.error("Error while creating new record - createType is unknown.");
					request.setAttribute("error", "createType is unknown");
					getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
				}}
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
			String typeRequest = request.getParameter("typerequest");
			if (typeRequest == null) {
				logger.error("Error while creating new record - typeRequest is null.");
				request.setAttribute("error", "typeRequest is null");
				getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
			} else {
				switch (typeRequest) {
				case "coach": {
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
					Coach coach = new Coach(0, coachName, coachSurname, coachBirthDay, coachBirthMonth, coachBirthYear,
							coachGames, coachPost);
					String insertMessage = coachDB.insert(coach);
					if (insertMessage.equals("Success")) {
						logger.info("Created new coach record - " + coach.getName() + " " + coach.getSurname() + ".");
						response.sendRedirect(request.getContextPath() + "/coachesview");
					} else {
						logger.error(insertMessage);
						request.setAttribute("error", insertMessage);
						getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
					}
					break;
				}
				case "goalkeeper": {
					int keeperGames, keeperGoals, keeperHeight, keeperWeight, goalsConceded, penaltySaved;
					if ((request.getParameter("KeeperNumber") == null) || (request.getParameter("KeeperNumber").equals(""))) {
						throw new EmptyNumberException();
					}
					int keeperNumber = Integer.parseInt(request.getParameter("KeeperNumber"));
					//Check if the number is occupied by other player.
					if (numberValidator.numberIsOccupied(keeperNumber)) {
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
					Goalkeeper keeper = new Goalkeeper(0, keeperNumber, keeperName, keeperSurname, keeperBirthDay,
							keeperBirthMonth, keeperBirthYear, keeperGames, keeperGoals, keeperHeight, keeperWeight,
							goalsConceded, penaltySaved, "Goalkeeper");
					String insertMessage = goalkeeperDB.insert(keeper);
					if (insertMessage.equals("Success")) {
						logger.info( "Created new goalkeeper record - " + keeper.getName() + " " + keeper.getSurname() + ".");
						response.sendRedirect(request.getContextPath() + "/teamview");
					} else {
						logger.error(insertMessage);
						request.setAttribute("error", insertMessage);
						getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
					}
					break;
				}
				case "fieldplayer": {
					int playerGames, playerGoals, playerHeight, playerWeight;
					if ((request.getParameter("PlayerNumber") == null) || (request.getParameter("PlayerNumber").equals(""))) {
						throw new EmptyNumberException();
					}
					int playerNumber = Integer.parseInt(request.getParameter("PlayerNumber"));
					//Check if the number is occupied by other player.
					if (numberValidator.numberIsOccupied(playerNumber)) {
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
					if ((request.getParameter("PlayerHeight") == null)
							|| (request.getParameter("PlayerHeight").equals(""))) {
						playerHeight = 0;
					} else {
						playerHeight = Integer.parseInt(request.getParameter("PlayerHeight"));
					}
					if ((request.getParameter("PlayerWeight") == null)
							|| (request.getParameter("PlayerWeight").equals(""))) {
						playerWeight = 0;
					} else {
						playerWeight = Integer.parseInt(request.getParameter("PlayerWeight"));
					}
					String playerPosition = request.getParameter("PlayerPosition");
					Player player = new Player(0, playerNumber, playerName, playerSurname, playerBirthDay, playerBirthMonth, 
							playerBirthYear, playerGames, playerGoals, playerHeight, playerWeight, playerPosition);
					String insertMessage = playerDB.insert(player);
					if (insertMessage.equals("Success")) {
						logger.info( "Created new field player record - " + player.getName() + " " + player.getSurname() + ".");
						response.sendRedirect(request.getContextPath() + "/teamview");
					} else {
						logger.error(insertMessage);
						request.setAttribute("error", insertMessage);
						getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
					}
					break;
				}
				default: {
					logger.error("typeRequest is unknown, cannot create an unknown entity");
					request.setAttribute("error", "typeRequest is unknown, cannot create an unknown entity.");
					getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
				}}
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
