package com.learn.epam.victorprudnikov.controller;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
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

public class DeleteServletTest {
	private final String somethingWrong = "/somethingwrong.jsp";
	private final String deleteCoach = "/deletecoach.jsp";
	private final String deleteGoalkeeper = "/deletegoalkeeper.jsp";
	private final String deleteFieldPlayer = "/deletefieldplayer.jsp";
	
	private Coach coachTest = new Coach(99, "CoachName", "CoachSurname", 20, 10, 1990, 10, "Assistent Coach");
	private Goalkeeper keeperTest = new Goalkeeper(99, 50, "KeeperName", "KeeperSurname", 20, 10, 1990, 10, 0, 190, 90, 2, 1, "Goalkeeper");
	private Player playerTest = new Player(99, 40, "PlayerName", "PlayerSurname", 20, 10, 1990, 10, 0, 190, 90, "Defender");

	private HttpServletRequest request = mock(HttpServletRequest.class);
	private HttpServletResponse response = mock(HttpServletResponse.class);
	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	private CoachDB coachDB = mock(CoachDB.class);
	private PlayerDB playerDB = mock(PlayerDB.class);
	private GoalkeeperDB goalkeeperDB = mock(GoalkeeperDB.class);
	private ServletContext servletContext = mock(ServletContext.class);
	
	private DeleteServlet servlet = new DeleteServlet(coachDB, playerDB, goalkeeperDB) {
		private static final long serialVersionUID = 1L;

		public ServletContext getServletContext() {
            return servletContext;
        }
	};
	
	//testing work of doGet method for the null attribute "typeRequest"
	@Test
	public void test1DoGet() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn(null);
		when(request.getParameter("id")).thenReturn("1");
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		
		servlet.doGet(request, response);
		
		assertEquals("ID or typeRequest = null", valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	
	//testing work of doGet method for the null attribute "id"
	@Test
	public void test2DoGet() throws ServletException, IOException {
		when(request.getParameter("id")).thenReturn(null);
		when(request.getParameter("typerequest")).thenReturn("coaches");
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		
		servlet.doGet(request, response);
		
		assertEquals("ID or typeRequest = null", valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing normal work of doGet method for the attribute "coaches"
	@Test
	public void test3DoGet() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("coaches");
		when(request.getParameter("id")).thenReturn("1");
		when(coachDB.selectOne(1)).thenReturn(coachTest);
		when(servletContext.getRequestDispatcher(deleteCoach)).thenReturn(dispatcher);
		
		servlet.doGet(request, response);
		
		verify(request, times(1)).setAttribute("coach", coachTest);
		verify(servletContext, times(1)).getRequestDispatcher(deleteCoach);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing normal work of doGet method for the attribute "keepers"
	@Test
	public void test4DoGet() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("keepers");
		when(request.getParameter("id")).thenReturn("1");
		when(goalkeeperDB.selectOne(1)).thenReturn(keeperTest);
		when(servletContext.getRequestDispatcher(deleteGoalkeeper)).thenReturn(dispatcher);
		
		servlet.doGet(request, response);
		
		verify(request, times(1)).setAttribute("keeper", keeperTest);
		verify(servletContext, times(1)).getRequestDispatcher(deleteGoalkeeper);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing normal work of doGet method for the attribute "players"
	@Test
	public void test5DoGet() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("players");
		when(request.getParameter("id")).thenReturn("1");
		when(playerDB.selectOne(1)).thenReturn(playerTest);
		when(servletContext.getRequestDispatcher(deleteFieldPlayer)).thenReturn(dispatcher);
		
		servlet.doGet(request, response);
		
		verify(request, times(1)).setAttribute("player", playerTest);
		verify(servletContext, times(1)).getRequestDispatcher(deleteFieldPlayer);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing work of doGet method while selecting null coach
	@Test
	public void test6DoGet() throws ServletException, IOException {
		when(request.getParameter("id")).thenReturn("1");
		when(request.getParameter("typerequest")).thenReturn("coaches");
		when(coachDB.selectOne(1)).thenReturn(null);
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		
		servlet.doGet(request, response);
		
		assertEquals("There is no such coach in the database.", valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing work of doGet method while selecting null goalkeeper
	@Test
	public void test7DoGet() throws ServletException, IOException {
		when(request.getParameter("id")).thenReturn("1");
		when(request.getParameter("typerequest")).thenReturn("keepers");
		when(goalkeeperDB.selectOne(1)).thenReturn(null);
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		
		servlet.doGet(request, response);
		
		assertEquals("There is no such goalkeeper in the database.", valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing work of doGet method while selecting null field player
	@Test
	public void test8DoGet() throws ServletException, IOException {
		when(request.getParameter("id")).thenReturn("1");
		when(request.getParameter("typerequest")).thenReturn("players");
		when(playerDB.selectOne(1)).thenReturn(null);
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		
		servlet.doGet(request, response);
		
		assertEquals("There is no such player in the database.", valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing work of doGet method for unknown attribute
	@Test
	public void test9DoGet() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("someValue");
		when(request.getParameter("id")).thenReturn("1");
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		
		servlet.doGet(request, response);
		
		assertEquals("typeRequest is unknown.", valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing behavior of doGet method while handling exceptions
	@SuppressWarnings("unchecked")
	@Test
	public void test10DoGet() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenThrow(Exception.class);
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		Exception ex = new Exception();	
		
		servlet.doGet(request, response);
		
		assertEquals(ex.toString(), valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing work of doPost method for the null attribute "typeRequest"
	@Test
	public void test1DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn(null);
		when(request.getParameter("id")).thenReturn("1");
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		
		servlet.doPost(request, response);
		
		assertEquals("ID or typeRequest = null", valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing work of doPost method for the null attribute "id"
	@Test
	public void test2DoPost() throws ServletException, IOException {
		when(request.getParameter("id")).thenReturn(null);
		when(request.getParameter("typerequest")).thenReturn("coaches");
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		
		servlet.doPost(request, response);
		
		assertEquals("ID or typeRequest = null", valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}

	//testing normal work of doPost method for the attribute "coaches"
	@Test
	public void test3DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("coaches");
		when(request.getParameter("id")).thenReturn("1");
		when(coachDB.selectOne(1)).thenReturn(coachTest);
		when(coachDB.delete(1)).thenReturn("Success");
		when(request.getContextPath()).thenReturn("test");
		doNothing().when(response).sendRedirect("test/coachesview");
		
		servlet.doPost(request, response);
		
		verify(coachDB, times(1)).selectOne(any(Integer.class));
		verify(coachDB, times(1)).delete(any(Integer.class));
		verify(response, times(1)).sendRedirect("test/coachesview");
	}
	
	//testing normal work of doPost method for the attribute "keepers"
	@Test
	public void test4DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("keepers");
		when(request.getParameter("id")).thenReturn("1");
		when(goalkeeperDB.selectOne(1)).thenReturn(keeperTest);
		when(goalkeeperDB.delete(1)).thenReturn("Success");
		when(request.getContextPath()).thenReturn("test");
		doNothing().when(response).sendRedirect("test/teamview");
		
		servlet.doPost(request, response);
		
		verify(goalkeeperDB, times(1)).selectOne(any(Integer.class));
		verify(goalkeeperDB, times(1)).delete(any(Integer.class));
		verify(response, times(1)).sendRedirect("test/teamview");
	}
	
	//testing normal work of doPost method for the attribute "players"
	@Test
	public void test5DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("players");
		when(request.getParameter("id")).thenReturn("1");
		when(playerDB.selectOne(1)).thenReturn(playerTest);
		when(playerDB.delete(1)).thenReturn("Success");
		when(request.getContextPath()).thenReturn("test");
		doNothing().when(response).sendRedirect("test/teamview");
		
		servlet.doPost(request, response);
		
		verify(playerDB, times(1)).selectOne(any(Integer.class));
		verify(playerDB, times(1)).delete(any(Integer.class));
		verify(response, times(1)).sendRedirect("test/teamview");
	}
	
	//testing work of doPost method for the attribute "coaches" with failed attempt to delete the entity from the database
	@Test
	public void test9DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("coaches");
		when(request.getParameter("id")).thenReturn("1");
		when(coachDB.selectOne(1)).thenReturn(coachTest);
		when(coachDB.delete(1)).thenReturn("Fail");
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		
		servlet.doPost(request, response);
		
		verify(coachDB, times(1)).selectOne(any(Integer.class));
		verify(coachDB, times(1)).delete(any(Integer.class));
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing work of doPost method for the attribute "keepers" with failed attempt to delete the entity from the database
	@Test
	public void test10DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("keepers");
		when(request.getParameter("id")).thenReturn("1");
		when(goalkeeperDB.selectOne(1)).thenReturn(keeperTest);
		when(goalkeeperDB.delete(1)).thenReturn("Fail");
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		
		servlet.doPost(request, response);
		
		verify(goalkeeperDB, times(1)).selectOne(any(Integer.class));
		verify(goalkeeperDB, times(1)).delete(any(Integer.class));
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing work of doPost method for the attribute "players" with failed attempt to delete the entity from the database
	@Test
	public void test11DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("players");
		when(request.getParameter("id")).thenReturn("1");
		when(playerDB.selectOne(1)).thenReturn(playerTest);
		when(playerDB.delete(1)).thenReturn("Fail");
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		
		servlet.doPost(request, response);
		
		verify(playerDB, times(1)).selectOne(any(Integer.class));
		verify(playerDB, times(1)).delete(any(Integer.class));
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing work of doPost method for unknown attribute
	@Test
	public void test12DoPost() throws ServletException, IOException {
		when(request.getParameter("typerequest")).thenReturn("someValue");
		when(request.getParameter("id")).thenReturn("1");
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		
		servlet.doPost(request, response);
		
		assertEquals("typeRequest is unknown, cannot delete an unknown entity.", valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing behavior of doPost method while handling exceptions
	@SuppressWarnings("unchecked")
	@Test
	public void test13DoPost() throws ServletException, IOException {
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

	//testing constructor
	@Test
	public void testConstructor() throws ServletException, IOException {
		DeleteServlet servlet = new DeleteServlet();
		assertNotNull(servlet);
	}
}
