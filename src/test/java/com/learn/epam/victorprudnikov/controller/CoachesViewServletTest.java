package com.learn.epam.victorprudnikov.controller;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;

import com.learn.epam.victorprudnikov.database.CoachDB;
import com.learn.epam.victorprudnikov.model.Coach;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CoachesViewServletTest {
	private final String somethingWrong = "/somethingwrong.jsp";
	private final String coachesJSP = "/coaches.jsp";
	private final List<Coach> coaches = new ArrayList<>();
	
	private HttpServletRequest request = mock(HttpServletRequest.class);
	private HttpServletResponse response = mock(HttpServletResponse.class);
	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	private CoachDB coachDB = mock(CoachDB.class);
	private ServletContext servletContext = mock(ServletContext.class);
	
	private CoachesViewServlet servlet = new CoachesViewServlet(coachDB) {
		private static final long serialVersionUID = 1L;

		public ServletContext getServletContext() {
            return servletContext;
        }
	};
	
	//testing doGet method when list of coaches is null
	@Test
	public void test1DoGet() throws ServletException, IOException {
		when(coachDB.createList()).thenReturn(null);
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		
		servlet.doGet(request, response);
		
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing normal work of doGet method
	@Test
	public void test2DoGet() throws ServletException, IOException {
		when(coachDB.createList()).thenReturn(coaches);
		when(servletContext.getRequestDispatcher(coachesJSP)).thenReturn(dispatcher);
		
		servlet.doGet(request, response);
		
		verify(servletContext, times(1)).getRequestDispatcher(coachesJSP);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing behavior of doGet method while handling exceptions
	@SuppressWarnings("unchecked")
	@Test
	public void test3DoGet() throws ServletException, IOException {
		when(coachDB.createList()).thenThrow(Exception.class);
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		ArgumentCaptor<String> valueCapture = ArgumentCaptor.forClass(String.class);
		doNothing().when(request).setAttribute(any(String.class), valueCapture.capture());
		Exception ex = new Exception();
		
		servlet.doGet(request, response);
		
		assertEquals(ex.toString(), valueCapture.getValue());
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing doPost method when list of coaches is null
	@Test
	public void test1DoPost() throws ServletException, IOException {
		when(coachDB.createList()).thenReturn(null);
		when(servletContext.getRequestDispatcher(somethingWrong)).thenReturn(dispatcher);
		
		servlet.doPost(request, response);
		
		verify(servletContext, times(1)).getRequestDispatcher(somethingWrong);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing normal work of doPost method
	@Test
	public void test2DoPost() throws ServletException, IOException {
		when(coachDB.createList()).thenReturn(coaches);
		when(servletContext.getRequestDispatcher(coachesJSP)).thenReturn(dispatcher);
		
		servlet.doPost(request, response);
		
		verify(servletContext, times(1)).getRequestDispatcher(coachesJSP);
		verify(dispatcher, times(1)).forward(request, response);
	}
	
	//testing behavior of doPost method while handling exceptions
	@SuppressWarnings("unchecked")
	@Test
	public void test3DoPost() throws ServletException, IOException {
		when(coachDB.createList()).thenThrow(Exception.class);
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
		CoachesViewServlet servlet = new CoachesViewServlet();
		assertNotNull(servlet);
	}
}
