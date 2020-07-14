package com.learn.epam.victorprudnikov.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.learn.epam.victorprudnikov.database.CoachDB;
import com.learn.epam.victorprudnikov.database.PlayerDB;
import com.learn.epam.victorprudnikov.database.GoalkeeperDB;
import com.learn.epam.victorprudnikov.model.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServletListener implements ServletContextListener {

	private static final Logger logger = LogManager.getLogger();
	private CoachDB coachDB = new CoachDB();
	private PlayerDB playerDB = new PlayerDB();
	private GoalkeeperDB goalkeeperDB = new GoalkeeperDB();
	
    public void contextInitialized(ServletContextEvent sce) {
        try {
        	ServletContext sc = sce.getServletContext();
            
            // Create and populate the embedded H2 database.
            coachDB.createPopulateH2();
            playerDB.createPopulateH2();
            goalkeeperDB.createPopulateH2();
            
            // Retrieve records from database and place into ServletContext.
            List<Coach> coaches = coachDB.createList();
            List<Player> players = playerDB.createList();
            List<Goalkeeper> keepers = goalkeeperDB.createList();
            sc.setAttribute("coaches", coaches);
            sc.setAttribute("players", players);
            sc.setAttribute("keepers", keepers);            
            sc.setAttribute("postlist", new ArrayList<Post>(Arrays.asList(Post.values())));
            sc.setAttribute("positionlist", new ArrayList<Position>(Arrays.asList(Position.values())));
        }
        catch(Exception ex) {
			logger.error(ex.toString());
			ex.printStackTrace();
		}
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }
    
}
