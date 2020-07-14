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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//The servlet for deleting coach, goalkeeper or player record from the database.
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger();
	private CoachDB coachDB = new CoachDB();
	private PlayerDB playerDB = new PlayerDB();
	private GoalkeeperDB goalkeeperDB = new GoalkeeperDB();

	public DeleteServlet() {
		super();
	}
	
	//The constructor for the unit testing.
	DeleteServlet(CoachDB coachDB, PlayerDB playerDB, GoalkeeperDB goalkeeperDB) {
		super();
		this.coachDB = coachDB;
		this.playerDB = playerDB;
		this.goalkeeperDB = goalkeeperDB;
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
						getServletContext().getRequestDispatcher("/deletecoach.jsp").forward(request, response);
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
						getServletContext().getRequestDispatcher("/deletegoalkeeper.jsp").forward(request, response);
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
						getServletContext().getRequestDispatcher("/deletefieldplayer.jsp").forward(request, response);
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
					Coach coach = coachDB.selectOne(id);
					String deleteMessage = coachDB.delete(id);
					if (deleteMessage.equals("Success")) {
						logger.info("Deleted coach record - " + coach.getName() + " " + coach.getSurname() + " (id="
								+ id + ").");
						response.sendRedirect(request.getContextPath() + "/coachesview");
					} else {
						logger.error(deleteMessage);
						request.setAttribute("error", deleteMessage);
						getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
					}
					break;
				}
				case "keepers": {
					Goalkeeper keeper = goalkeeperDB.selectOne(id);
					String deleteMessage = goalkeeperDB.delete(id);
					if (deleteMessage.equals("Success")) {
						logger.info("Deleted goalkeeper record - " + keeper.getName() + " " + keeper.getSurname() + " (id="
								+ id + ").");
						response.sendRedirect(request.getContextPath() + "/teamview");
					} else {
						logger.error(deleteMessage);
						request.setAttribute("error", deleteMessage);
						getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
					}
					break;
				}
				case "players": {
					Player player = playerDB.selectOne(id);
					String deleteMessage = playerDB.delete(id);
					if (deleteMessage.equals("Success")) {
						logger.info("Deleted player record - " + player.getName() + " " + player.getSurname() + " (id="
								+ id + ").");
						response.sendRedirect(request.getContextPath() + "/teamview");
					} else {
						logger.error(deleteMessage);
						request.setAttribute("error", deleteMessage);
						getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
					}
					break;
				}
				default: {
					logger.error("typeRequest is unknown, cannot delete an unknown entity.");
					request.setAttribute("error", "typeRequest is unknown, cannot delete an unknown entity.");
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
}
