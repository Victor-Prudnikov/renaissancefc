package com.learn.epam.victorprudnikov.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.learn.epam.victorprudnikov.database.CoachDB;
import com.learn.epam.victorprudnikov.model.Coach;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//The servlet for viewing all coach records from the database.
public class CoachesViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger();
	private CoachDB coachDB = new CoachDB();
	
	public CoachesViewServlet() {
		super();
	}
	
	//The constructor for the unit testing.
	CoachesViewServlet(CoachDB coachDB) {
		super();
		this.coachDB = coachDB;
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			List<Coach> coaches = coachDB.createList();
			if (coaches == null) {
				request.setAttribute("error", "Coach table is empty.");
				getServletContext().getRequestDispatcher("/somethingwrong.jsp").forward(request, response);
			}
			else {
				request.setAttribute("coaches", coaches);
				getServletContext().getRequestDispatcher("/coaches.jsp").forward(request, response);
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
