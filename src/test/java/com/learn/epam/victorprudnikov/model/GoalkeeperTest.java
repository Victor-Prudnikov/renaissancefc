package com.learn.epam.victorprudnikov.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

public class GoalkeeperTest {
	private final Goalkeeper goalkeeper1 = 
			new Goalkeeper(0, 55, "First", "Second", 5, 4, 1985, 20, 0, 180, 80, 10, 2, "Goalkeeper");
	private Goalkeeper goalkeeper2;
	
	@Test
	public void testGetGoalsConceded() {
		assertEquals(10, goalkeeper1.getGoalsConceded());
	}
	
	@Test
	public void testSetGoalsConceded() {
		goalkeeper2 = new Goalkeeper(1, 50, "Name", "Surname", 25, 11, 1990, 10, 5, 170, 70, 5, 1, "Goalkeeper");
		goalkeeper2.setGoalsConceded(15);
		assertEquals(15, goalkeeper2.getGoalsConceded());
	}
	
	@Test
	public void testGetPenaltySaved() {
		assertEquals(2, goalkeeper1.getPenaltySaved());
	}
	
	@Test
	public void testSetPenaltySaved() {
		goalkeeper2 = new Goalkeeper(1, 50, "Name", "Surname", 25, 11, 1990, 10, 5, 170, 70, 5, 1, "Goalkeeper");
		goalkeeper2.setPenaltySaved(3);
		assertEquals(3, goalkeeper2.getPenaltySaved());
	}
}
