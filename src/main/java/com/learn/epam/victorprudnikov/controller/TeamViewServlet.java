package com.learn.epam.victorprudnikov.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.learn.epam.victorprudnikov.database.PlayerDB;
import com.learn.epam.victorprudnikov.model.Player;
import com.learn.epam.victorprudnikov.database.GoalkeeperDB;
import com.learn.epam.victorprudnikov.model.Goalkeeper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//The servlet for viewing all goalkeeper and player records from the database.
public class TeamViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger();
	private PlayerDB playerDB = new PlayerDB();
	private GoalkeeperDB goalkeeperDB = new GoalkeeperDB();
	
	public TeamViewServlet() {
		super();
	}
	
	//The constructor for the unit testing.
	TeamViewServlet(PlayerDB playerDB, GoalkeeperDB goalkeeperDB) {
		super();
		this.playerDB = playerDB;
		this.goalkeeperDB = goalkeeperDB;
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Player> players = playerDB.createList();
			List<Goalkeeper> keepers = goalkeeperDB.createList();
			
			if (players == null) {
				request.setAttribute("error", "Field players table is empty.");
				getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
			}
			else {
				if (keepers == null) {
					request.setAttribute("error", "Goalkeepers table is empty.");
					getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
				} else {
					request.setAttribute("players", players);
					request.setAttribute("keepers", keepers);
					getServletContext().getRequestDispatcher("/team.jsp").forward(request, response);
				}
			}
		}
		catch(Exception ex) {
			logger.error(ex.toString());
			request.setAttribute("error", ex.toString());
			getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
		}
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
