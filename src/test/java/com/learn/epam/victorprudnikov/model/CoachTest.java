package com.learn.epam.victorprudnikov.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

public class CoachTest {
	private final Coach coach1 = new Coach(0, "First", "Second", 5, 4, 1980, 2, "Head Coach");
	private Coach coach2;
	
	@Test
	public void testGetPost() {
		assertEquals("Head Coach", coach1.getPost());
	}
	
	@Test
	public void testSetPost() {
		coach2 = new Coach(1, "Name", "Surname", 25, 11, 1990, 1, "Assistent Coach");
		coach2.setPost("Goalkeeper Coach");
		assertEquals("Goalkeeper Coach", coach2.getPost());
	}
}
