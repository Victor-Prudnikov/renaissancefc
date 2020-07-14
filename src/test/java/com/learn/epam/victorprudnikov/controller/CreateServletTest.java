package com.learn.epam.victorprudnikov.controller;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
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

public class CreateServletTest {
	private final String somethingWrong = "/somethingwrong.jsp";
	private final String createCoach = "/createcoach.jsp";
	private final String createGoalkeeper = "/creategoalkeeper.jsp";
	private final String createFieldPlayer = "/createfieldplayer.jsp";
	
	private HttpServletRequest request = mock(HttpServletRequest.class);
	private HttpServletResponse response = mock(HttpServletResponse.class);
	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	private CoachDB coachDB = mock(CoachDB.class);
	private PlayerDB playerDB = mock(PlayerDB.class);
	private GoalkeeperDB goalkeeperDB = mock(GoalkeeperDB.class);
	private NumberValidator numberValidator = mock(NumberValidator.class);
	private ServletContext servletContext = mock(ServletContext.class);
	
	private CreateServlet servlet = new CreateServlet(coachDB, playerDB, goalkeeperDB, numberValidator) {
		private static final long serialVersionUID = 1L;

		public ServletContext getServletContext() {
            return servletContext;
        }
	};
	
	//testing normal work of doGet method for the attribute "coach"
	@Test
	public void test1DoGet() throws ServletException, IOException {
		when(request.getParameter("createlist")).thenReturn("coach");
		when(servletContext.getRequestDispatcher(createCoach)).thenReturn(dispatcher);
		
		servlet.doGet(request, response);
		
		verify(servletContext, times(1)).getRequestDispatcher(createCoach);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing normal work of doGet method for the attribute "goalkeeper"
	@Test
	public void test2DoGet() throws ServletException, IOException {
		when(request.getParameter("createlist")).thenReturn("goalkeeper");
		when(servletContext.getRequestDispatcher(createGoalkeeper)).thenReturn(dispatcher);
		
		servlet.doGet(request, response);
		
		verify(servletContext, times(1)).getRequestDispatcher(createGoalkeeper);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing normal work of doGet method for the attribute "fieldplayer"
	@Test
	public void test3DoGet() throws ServletException, IOException {
		when(request.getParameter("createlist")).thenReturn("fieldplayer");
		when(servletContext.getRequestDispatcher(createFieldPlayer)).thenReturn(dispatcher);
		
		servlet.doGet(request, response);
		
		verify(servletContext, times(1)).getRequestDispatcher(createFieldPlayer);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing work of doGet method for unknown attribute
	@Test
	public void test4DoGet() throws ServletException, IOException {
		when(request.getParameter("createlist")).thenReturn("someValue");
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		
		servlet.doGet(request, response);
		
		assertEquals("createType is unknown", valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing work of doGet method for the null attribute "createlist"
	@Test
	public void test5DoGet() throws ServletException, IOException {
		when(request.getParameter("createlist")).thenReturn(null);
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		
		servlet.doGet(request, response);
		
		assertEquals("createType is null", valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing behavior of doGet method while handling exceptions
	@SuppressWarnings("unchecked")
	@Test
	public void test6DoGet() throws ServletException, IOException {
		when(request.getParameter("createlist")).thenThrow(Exception.class);
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		Exception ex = new Exception();	
		
		servlet.doGet(request, response);
		
		assertEquals(ex.toString(), valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing normal work of doPost method for the attribute "coach"
	@Test
	public void test1DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("coach");
		when(request.getParameter("CoachName")).thenReturn("CoachName");
		when(request.getParameter("CoachSurname")).thenReturn("CoachSurname");
		when(request.getParameter("CoachBirthDate")).thenReturn("1980-09-25");
		when(request.getParameter("CoachGames")).thenReturn("10");
		when(request.getParameter("CoachPost")).thenReturn("CoachPost");
		when(coachDB.insert(any(Coach.class))).thenReturn("Success");
		when(request.getContextPath()).thenReturn("test");
		doNothing().when(response).sendRedirect("test/coachesview");
		
		servlet.doPost(request, response);
		
		verify(coachDB, times(1)).insert(any(Coach.class));
		verify(response, times(1)).sendRedirect("test/coachesview");
	}
	
	//testing EmptyNameException in doPost method for the attribute "coach"
	@Test
	public void test2DoPost() throws ServletException, IOException, EmptyNameException {
		when(request.getParameter("typerequest")).thenReturn("coach");
		when(request.getParameter("CoachName")).thenReturn(null);
		when(request.getParameter("CoachSurname")).thenReturn(null);
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		Exception ex = new EmptyNameException();
		
		servlet.doPost(request, response);
		
		assertEquals(ex.getMessage(), valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing EmptyNameException in doPost method for the attribute "coach"
	@Test
	public void test3DoPost() throws ServletException, IOException, EmptyNameException {
		when(request.getParameter("typerequest")).thenReturn("coach");
		when(request.getParameter("CoachName")).thenReturn("");
		when(request.getParameter("CoachSurname")).thenReturn("");
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		Exception ex = new EmptyNameException();
		
		servlet.doPost(request, response);
		
		assertEquals(ex.getMessage(), valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing work of doPost method for the attribute "coach" with empty CoachName
	@Test
	public void test4DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("coach");
		when(request.getParameter("CoachName")).thenReturn("");
		when(request.getParameter("CoachSurname")).thenReturn("CoachSurname");
		when(request.getParameter("CoachBirthDate")).thenReturn("1980-09-25");
		when(request.getParameter("CoachGames")).thenReturn("10");
		when(request.getParameter("CoachPost")).thenReturn("CoachPost");
		when(coachDB.insert(any(Coach.class))).thenReturn("Success");
		when(request.getContextPath()).thenReturn("test");
		doNothing().when(response).sendRedirect("test/coachesview");
		
		servlet.doPost(request, response);
		
		verify(coachDB, times(1)).insert(any(Coach.class));
		verify(response, times(1)).sendRedirect("test/coachesview");
	}
	
	//testing work of doPost method for the attribute "coach" with empty CoachGames
	@Test
	public void test5DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("coach");
		when(request.getParameter("CoachName")).thenReturn("CoachName");
		when(request.getParameter("CoachSurname")).thenReturn("CoachSurname");
		when(request.getParameter("CoachBirthDate")).thenReturn("1980-09-25");
		when(request.getParameter("CoachGames")).thenReturn("");
		when(request.getParameter("CoachPost")).thenReturn("CoachPost");
		ArgumentCaptor<Coach> valueCapture = ArgumentCaptor.forClass(Coach.class);
		when(coachDB.insert(valueCapture.capture())).thenReturn("Success");
		when(request.getContextPath()).thenReturn("test");
		doNothing().when(response).sendRedirect("test/coachesview");
		
		servlet.doPost(request, response);
		
		assertEquals(0, valueCapture.getValue().getGames());
		verify(coachDB, times(1)).insert(any(Coach.class));
		verify(response, times(1)).sendRedirect("test/coachesview");
	}
	
	//testing work of doPost method for the attribute "coach" with empty CoachGames
	@Test
	public void test6DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("coach");
		when(request.getParameter("CoachName")).thenReturn("CoachName");
		when(request.getParameter("CoachSurname")).thenReturn("");
		when(request.getParameter("CoachBirthDate")).thenReturn("1980-09-25");
		when(request.getParameter("CoachGames")).thenReturn(null);
		when(request.getParameter("CoachPost")).thenReturn("CoachPost");
		ArgumentCaptor<Coach> valueCapture = ArgumentCaptor.forClass(Coach.class);
		when(coachDB.insert(valueCapture.capture())).thenReturn("Success");
		when(request.getContextPath()).thenReturn("test");
		doNothing().when(response).sendRedirect("test/coachesview");
		
		servlet.doPost(request, response);
		
		assertEquals(0, valueCapture.getValue().getGames());
		verify(coachDB, times(1)).insert(any(Coach.class));
		verify(response, times(1)).sendRedirect("test/coachesview");
	}
	
	//testing work of doPost method for the attribute "coach" with failed attempt to insert the entity to the database
	@Test
	public void test7DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("coach");
		when(request.getParameter("CoachName")).thenReturn("CoachName");
		when(request.getParameter("CoachSurname")).thenReturn("CoachSurname");
		when(request.getParameter("CoachBirthDate")).thenReturn("1980-09-25");
		when(request.getParameter("CoachGames")).thenReturn("10");
		when(request.getParameter("CoachPost")).thenReturn("CoachPost");
		when(coachDB.insert(any(Coach.class))).thenReturn("Fail");
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		
		servlet.doPost(request, response);
		
		verify(coachDB, times(1)).insert(any(Coach.class));
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing work of doPost method for the attribute "coach" with invalid birth date
	@Test
	public void test8DoPost() throws ServletException, IOException, IndexOutOfBoundsException {
		when(request.getParameter("typerequest")).thenReturn("coach");
		when(request.getParameter("CoachName")).thenReturn("CoachName");
		when(request.getParameter("CoachSurname")).thenReturn("CoachSurname");
		when(request.getParameter("CoachBirthDate")).thenReturn("foo");
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		
		servlet.doPost(request, response);
		
		assertEquals("Invalid birth date.", valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing normal work of doPost method for the attribute "goalkeeper"
	@Test
	public void test9DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("goalkeeper");
		when(request.getParameter("KeeperNumber")).thenReturn("1");
		when(numberValidator.numberIsOccupied(any(Integer.class))).thenReturn(false);
		when(request.getParameter("KeeperName")).thenReturn("KeeperName");
		when(request.getParameter("KeeperSurname")).thenReturn("KeeperSurname");
		when(request.getParameter("KeeperBirthDate")).thenReturn("1980-09-25");
		when(request.getParameter("KeeperGames")).thenReturn("10");
		when(request.getParameter("KeeperGoals")).thenReturn("0");
		when(request.getParameter("KeeperHeight")).thenReturn("190");
		when(request.getParameter("KeeperWeight")).thenReturn("90");
		when(request.getParameter("GoalsConceded")).thenReturn("5");
		when(request.getParameter("PenaltySaved")).thenReturn("2");
		when(goalkeeperDB.insert(any(Goalkeeper.class))).thenReturn("Success");
		when(request.getContextPath()).thenReturn("test");
		doNothing().when(response).sendRedirect("test/teamview");
		
		servlet.doPost(request, response);
		
		verify(goalkeeperDB, times(1)).insert(any(Goalkeeper.class));
		verify(response, times(1)).sendRedirect("test/teamview");
	}
	
	//testing EmptyNameException in doPost method for the attribute "goalkeeper"
	@Test
	public void test10DoPost() throws ServletException, IOException, EmptyNameException {
		when(request.getParameter("typerequest")).thenReturn("goalkeeper");
		when(request.getParameter("KeeperNumber")).thenReturn("1");
		when(numberValidator.numberIsOccupied(any(Integer.class))).thenReturn(false);
		when(request.getParameter("KeeperName")).thenReturn(null);
		when(request.getParameter("KeeperSurname")).thenReturn(null);
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		Exception ex = new EmptyNameException();
		
		servlet.doPost(request, response);
		
		assertEquals(ex.getMessage(), valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing EmptyNameException in doPost method for the attribute "goalkeeper"
	@Test
	public void test11DoPost() throws ServletException, IOException, EmptyNameException {
		when(request.getParameter("typerequest")).thenReturn("goalkeeper");
		when(request.getParameter("KeeperNumber")).thenReturn("1");
		when(numberValidator.numberIsOccupied(any(Integer.class))).thenReturn(false);
		when(request.getParameter("KeeperName")).thenReturn("");
		when(request.getParameter("KeeperSurname")).thenReturn("");
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		Exception ex = new EmptyNameException();
		
		servlet.doPost(request, response);
		
		assertEquals(ex.getMessage(), valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing work of doPost method for the attribute "goalkeeper" with empty KeeperName
	@Test
	public void test12DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("goalkeeper");
		when(request.getParameter("KeeperNumber")).thenReturn("1");
		when(numberValidator.numberIsOccupied(any(Integer.class))).thenReturn(false);
		when(request.getParameter("KeeperName")).thenReturn("");
		when(request.getParameter("KeeperSurname")).thenReturn("KeeperSurname");
		when(request.getParameter("KeeperBirthDate")).thenReturn("1980-09-25");
		when(request.getParameter("KeeperGames")).thenReturn("10");
		when(request.getParameter("KeeperGoals")).thenReturn("0");
		when(request.getParameter("KeeperHeight")).thenReturn("190");
		when(request.getParameter("KeeperWeight")).thenReturn("90");
		when(request.getParameter("GoalsConceded")).thenReturn("5");
		when(request.getParameter("PenaltySaved")).thenReturn("2");
		when(goalkeeperDB.insert(any(Goalkeeper.class))).thenReturn("Success");
		when(request.getContextPath()).thenReturn("test");
		doNothing().when(response).sendRedirect("test/teamview");
		
		servlet.doPost(request, response);
		
		verify(goalkeeperDB, times(1)).insert(any(Goalkeeper.class));
		verify(response, times(1)).sendRedirect("test/teamview");
	}
	
	//testing work of doPost method for the attribute "goalkeeper" with empty number fields
	@Test
	public void test13DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("goalkeeper");
		when(request.getParameter("KeeperNumber")).thenReturn("1");
		when(numberValidator.numberIsOccupied(any(Integer.class))).thenReturn(false);
		when(request.getParameter("KeeperName")).thenReturn("KeeperName");
		when(request.getParameter("KeeperSurname")).thenReturn("KeeperSurname");
		when(request.getParameter("KeeperBirthDate")).thenReturn("1980-09-25");
		when(request.getParameter("KeeperGames")).thenReturn("");
		when(request.getParameter("KeeperGoals")).thenReturn("");
		when(request.getParameter("KeeperHeight")).thenReturn("");
		when(request.getParameter("KeeperWeight")).thenReturn("");
		when(request.getParameter("GoalsConceded")).thenReturn("");
		when(request.getParameter("PenaltySaved")).thenReturn("");
		ArgumentCaptor<Goalkeeper> valueCapture = ArgumentCaptor.forClass(Goalkeeper.class);
		when(goalkeeperDB.insert(valueCapture.capture())).thenReturn("Success");
		when(request.getContextPath()).thenReturn("test");
		doNothing().when(response).sendRedirect("test/teamview");
		
		servlet.doPost(request, response);
		
		assertEquals(0, valueCapture.getValue().getGames());
		assertEquals(0, valueCapture.getValue().getGoals());
		assertEquals(0, valueCapture.getValue().getHeight());
		assertEquals(0, valueCapture.getValue().getWeight());
		assertEquals(0, valueCapture.getValue().getGoalsConceded());
		assertEquals(0, valueCapture.getValue().getPenaltySaved());
		verify(goalkeeperDB, times(1)).insert(any(Goalkeeper.class));
		verify(response, times(1)).sendRedirect("test/teamview");
	}
	
	//testing work of doPost method for the attribute "goalkeeper" with empty number fields
	@Test
	public void test14DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("goalkeeper");
		when(request.getParameter("KeeperNumber")).thenReturn("1");
		when(numberValidator.numberIsOccupied(any(Integer.class))).thenReturn(false);
		when(request.getParameter("KeeperName")).thenReturn("KeeperName");
		when(request.getParameter("KeeperSurname")).thenReturn("KeeperSurname");
		when(request.getParameter("KeeperBirthDate")).thenReturn("1980-09-25");
		when(request.getParameter("KeeperGames")).thenReturn(null);
		when(request.getParameter("KeeperGoals")).thenReturn(null);
		when(request.getParameter("KeeperHeight")).thenReturn(null);
		when(request.getParameter("KeeperWeight")).thenReturn(null);
		when(request.getParameter("GoalsConceded")).thenReturn(null);
		when(request.getParameter("PenaltySaved")).thenReturn(null);
		ArgumentCaptor<Goalkeeper> valueCapture = ArgumentCaptor.forClass(Goalkeeper.class);
		when(goalkeeperDB.insert(valueCapture.capture())).thenReturn("Success");
		when(request.getContextPath()).thenReturn("test");
		doNothing().when(response).sendRedirect("test/teamview");
		
		servlet.doPost(request, response);
		
		assertEquals(0, valueCapture.getValue().getGames());
		assertEquals(0, valueCapture.getValue().getGoals());
		assertEquals(0, valueCapture.getValue().getHeight());
		assertEquals(0, valueCapture.getValue().getWeight());
		assertEquals(0, valueCapture.getValue().getGoalsConceded());
		assertEquals(0, valueCapture.getValue().getPenaltySaved());
		verify(goalkeeperDB, times(1)).insert(any(Goalkeeper.class));
		verify(response, times(1)).sendRedirect("test/teamview");
	}
	
	//testing work of doPost method for the attribute "goalkeeper" with failed attempt to insert the entity to the database
	@Test
	public void test15DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("goalkeeper");
		when(request.getParameter("KeeperNumber")).thenReturn("1");
		when(numberValidator.numberIsOccupied(any(Integer.class))).thenReturn(false);
		when(request.getParameter("KeeperName")).thenReturn("KeeperName");
		when(request.getParameter("KeeperSurname")).thenReturn("KeeperSurname");
		when(request.getParameter("KeeperBirthDate")).thenReturn("1980-09-25");
		when(request.getParameter("KeeperGames")).thenReturn("10");
		when(request.getParameter("KeeperGoals")).thenReturn("0");
		when(request.getParameter("KeeperHeight")).thenReturn("190");
		when(request.getParameter("KeeperWeight")).thenReturn("90");
		when(request.getParameter("GoalsConceded")).thenReturn("5");
		when(request.getParameter("PenaltySaved")).thenReturn("2");
		when(goalkeeperDB.insert(any(Goalkeeper.class))).thenReturn("Fail");
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		
		servlet.doPost(request, response);
		
		verify(goalkeeperDB, times(1)).insert(any(Goalkeeper.class));
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing EmptyNumberException in doPost method for the attribute "goalkeeper"
	@Test
	public void test16DoPost() throws ServletException, IOException, EmptyNumberException {
		when(request.getParameter("typerequest")).thenReturn("goalkeeper");
		when(request.getParameter("KeeperNumber")).thenReturn("");
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		Exception ex = new EmptyNumberException();
		
		servlet.doPost(request, response);
		
		assertEquals(ex.getMessage(), valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing EmptyNumberException in doPost method for the attribute "goalkeeper"
	@Test
	public void test17DoPost() throws ServletException, IOException, EmptyNumberException {
		when(request.getParameter("typerequest")).thenReturn("goalkeeper");
		when(request.getParameter("KeeperNumber")).thenReturn(null);
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		Exception ex = new EmptyNumberException();			

		servlet.doPost(request, response);
		
		assertEquals(ex.getMessage(), valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing NumberIsOccupiedException in doPost method for the attribute "goalkeeper"
	@Test
	public void test18DoPost() throws ServletException, IOException, NumberIsOccupiedException {
		when(request.getParameter("typerequest")).thenReturn("goalkeeper");
		when(request.getParameter("KeeperNumber")).thenReturn("1");
		when(numberValidator.numberIsOccupied(any(Integer.class))).thenReturn(true);
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		Exception ex = new NumberIsOccupiedException();			

		servlet.doPost(request, response);
		
		assertEquals(ex.getMessage(), valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing normal work of doPost method for the attribute "fieldplayer"
	@Test
	public void test19DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("fieldplayer");
		when(request.getParameter("PlayerNumber")).thenReturn("1");
		when(numberValidator.numberIsOccupied(any(Integer.class))).thenReturn(false);
		when(request.getParameter("PlayerName")).thenReturn("PlayerName");
		when(request.getParameter("PlayerSurname")).thenReturn("PlayerSurname");
		when(request.getParameter("PlayerBirthDate")).thenReturn("1980-09-25");
		when(request.getParameter("PlayerGames")).thenReturn("10");
		when(request.getParameter("PlayerGoals")).thenReturn("0");
		when(request.getParameter("PlayerHeight")).thenReturn("190");
		when(request.getParameter("PlayerWeight")).thenReturn("90");
		when(playerDB.insert(any(Player.class))).thenReturn("Success");
		when(request.getContextPath()).thenReturn("test");
		doNothing().when(response).sendRedirect("test/teamview");
		
		servlet.doPost(request, response);
		
		verify(playerDB, times(1)).insert(any(Player.class));
		verify(response, times(1)).sendRedirect("test/teamview");
	}
	
	//testing EmptyNameException in doPost method for the attribute "fieldplayer"
	@Test
	public void test20DoPost() throws ServletException, IOException, EmptyNameException {
		when(request.getParameter("typerequest")).thenReturn("fieldplayer");
		when(request.getParameter("PlayerNumber")).thenReturn("1");
		when(numberValidator.numberIsOccupied(any(Integer.class))).thenReturn(false);
		when(request.getParameter("PlayerName")).thenReturn(null);
		when(request.getParameter("PlayerSurname")).thenReturn(null);
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		Exception ex = new EmptyNameException();
		
		servlet.doPost(request, response);
		
		assertEquals(ex.getMessage(), valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing EmptyNameException in doPost method for the attribute "fieldplayer"
	@Test
	public void test21DoPost() throws ServletException, IOException, EmptyNameException {
		when(request.getParameter("typerequest")).thenReturn("fieldplayer");
		when(request.getParameter("PlayerNumber")).thenReturn("1");
		when(numberValidator.numberIsOccupied(any(Integer.class))).thenReturn(false);
		when(request.getParameter("PlayerName")).thenReturn("");
		when(request.getParameter("PlayerSurname")).thenReturn("");
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		Exception ex = new EmptyNameException();
		
		servlet.doPost(request, response);
		
		assertEquals(ex.getMessage(), valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing work of doPost method for the attribute "fieldplayer" with empty PlayerName
	@Test
	public void test22DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("fieldplayer");
		when(request.getParameter("PlayerNumber")).thenReturn("1");
		when(numberValidator.numberIsOccupied(any(Integer.class))).thenReturn(false);
		when(request.getParameter("PlayerName")).thenReturn("");
		when(request.getParameter("PlayerSurname")).thenReturn("PlayerSurname");
		when(request.getParameter("PlayerBirthDate")).thenReturn("1980-09-25");
		when(request.getParameter("PlayerGames")).thenReturn("10");
		when(request.getParameter("PlayerGoals")).thenReturn("0");
		when(request.getParameter("PlayerHeight")).thenReturn("190");
		when(request.getParameter("PlayerWeight")).thenReturn("90");
		when(playerDB.insert(any(Player.class))).thenReturn("Success");
		when(request.getContextPath()).thenReturn("test");
		doNothing().when(response).sendRedirect("test/teamview");
		
		servlet.doPost(request, response);
		
		verify(playerDB, times(1)).insert(any(Player.class));
		verify(response, times(1)).sendRedirect("test/teamview");
	}
	
	//testing work of doPost method for the attribute "fieldplayer" with empty number fields
	@Test
	public void test23DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("fieldplayer");
		when(request.getParameter("PlayerNumber")).thenReturn("1");
		when(numberValidator.numberIsOccupied(any(Integer.class))).thenReturn(false);
		when(request.getParameter("PlayerName")).thenReturn("PlayerName");
		when(request.getParameter("PlayerSurname")).thenReturn("PlayerSurname");
		when(request.getParameter("PlayerBirthDate")).thenReturn("1980-09-25");
		when(request.getParameter("PlayerGames")).thenReturn("");
		when(request.getParameter("PlayerGoals")).thenReturn("");
		when(request.getParameter("PlayerHeight")).thenReturn("");
		when(request.getParameter("PlayerWeight")).thenReturn("");
		ArgumentCaptor<Player> valueCapture = ArgumentCaptor.forClass(Player.class);
		when(playerDB.insert(valueCapture.capture())).thenReturn("Success");
		when(request.getContextPath()).thenReturn("test");
		doNothing().when(response).sendRedirect("test/teamview");
		
		servlet.doPost(request, response);
		
		assertEquals(0, valueCapture.getValue().getGames());
		assertEquals(0, valueCapture.getValue().getGoals());
		assertEquals(0, valueCapture.getValue().getHeight());
		assertEquals(0, valueCapture.getValue().getWeight());
		verify(playerDB, times(1)).insert(any(Player.class));
		verify(response, times(1)).sendRedirect("test/teamview");
	}
	
	//testing work of doPost method for the attribute "fieldplayer" with empty number fields
	@Test
	public void test24DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("fieldplayer");
		when(request.getParameter("PlayerNumber")).thenReturn("1");
		when(numberValidator.numberIsOccupied(any(Integer.class))).thenReturn(false);
		when(request.getParameter("PlayerName")).thenReturn("PlayerName");
		when(request.getParameter("PlayerSurname")).thenReturn("PlayerSurname");
		when(request.getParameter("PlayerBirthDate")).thenReturn("1980-09-25");
		when(request.getParameter("PlayerGames")).thenReturn(null);
		when(request.getParameter("PlayerGoals")).thenReturn(null);
		when(request.getParameter("PlayerHeight")).thenReturn(null);
		when(request.getParameter("PlayerWeight")).thenReturn(null);
		ArgumentCaptor<Player> valueCapture = ArgumentCaptor.forClass(Player.class);
		when(playerDB.insert(valueCapture.capture())).thenReturn("Success");
		when(request.getContextPath()).thenReturn("test");
		doNothing().when(response).sendRedirect("test/teamview");
		
		servlet.doPost(request, response);
		
		assertEquals(0, valueCapture.getValue().getGames());
		assertEquals(0, valueCapture.getValue().getGoals());
		assertEquals(0, valueCapture.getValue().getHeight());
		assertEquals(0, valueCapture.getValue().getWeight());
		verify(playerDB, times(1)).insert(any(Player.class));
		verify(response, times(1)).sendRedirect("test/teamview");
	}
	
	//testing work of doPost method for the attribute "fieldplayer" with failed attempt to insert the entity to the database
	@Test
	public void test25DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("fieldplayer");
		when(request.getParameter("PlayerNumber")).thenReturn("1");
		when(numberValidator.numberIsOccupied(any(Integer.class))).thenReturn(false);
		when(request.getParameter("PlayerName")).thenReturn("PlayerName");
		when(request.getParameter("PlayerSurname")).thenReturn("PlayerSurname");
		when(request.getParameter("PlayerBirthDate")).thenReturn("1980-09-25");
		when(request.getParameter("PlayerGames")).thenReturn("10");
		when(request.getParameter("PlayerGoals")).thenReturn("0");
		when(request.getParameter("PlayerHeight")).thenReturn("190");
		when(request.getParameter("PlayerWeight")).thenReturn("90");
		when(request.getParameter("GoalsConceded")).thenReturn("5");
		when(request.getParameter("PenaltySaved")).thenReturn("2");
		when(playerDB.insert(any(Player.class))).thenReturn("Fail");
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		
		servlet.doPost(request, response);
		
		verify(playerDB, times(1)).insert(any(Player.class));
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing EmptyNumberException in doPost method for the attribute "fieldplayer"
	@Test
	public void test26DoPost() throws ServletException, IOException, EmptyNumberException {
		when(request.getParameter("typerequest")).thenReturn("fieldplayer");
		when(request.getParameter("PlayerNumber")).thenReturn("");
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		Exception ex = new EmptyNumberException();
		
		servlet.doPost(request, response);
		
		assertEquals(ex.getMessage(), valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing EmptyNumberException in doPost method for the attribute "fieldplayer"
	@Test
	public void test27DoPost() throws ServletException, IOException, EmptyNumberException {
		when(request.getParameter("typerequest")).thenReturn("fieldplayer");
		when(request.getParameter("PlayerNumber")).thenReturn(null);
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		Exception ex = new EmptyNumberException();			

		servlet.doPost(request, response);
		
		assertEquals(ex.getMessage(), valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing NumberIsOccupiedException in doPost method for the attribute "fieldplayer"
	@Test
	public void test28DoPost() throws ServletException, IOException, NumberIsOccupiedException {
		when(request.getParameter("typerequest")).thenReturn("fieldplayer");
		when(request.getParameter("PlayerNumber")).thenReturn("1");
		when(numberValidator.numberIsOccupied(any(Integer.class))).thenReturn(true);
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		Exception ex = new NumberIsOccupiedException();			

		servlet.doPost(request, response);
		
		assertEquals(ex.getMessage(), valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing work of doPost method for unknown attribute
	@Test
	public void test29DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("someValue");
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		
		servlet.doPost(request, response);
		
		assertEquals("typeRequest is unknown, cannot create an unknown entity.", valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing behavior of doPost method while handling exceptions
	@SuppressWarnings("unchecked")
	@Test
	public void test30DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenThrow(Exception.class);
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		Exception ex = new Exception();	
		
		servlet.doPost(request, response);
		
		assertEquals(ex.toString(), valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing work of doPost method for the null attribute "typeRequest"
	@Test
	public void test31DoPost() throws ServletException, IOException {
		when(request.getParameter("typeRequest")).thenReturn(null);
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		
		servlet.doPost(request, response);
		
		assertEquals("typeRequest is null", valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}

	//testing constructor
	@Test
	public void testConstructor() throws ServletException, IOException {
		CreateServlet servlet = new CreateServlet();
		assertNotNull(servlet);
	}
}
