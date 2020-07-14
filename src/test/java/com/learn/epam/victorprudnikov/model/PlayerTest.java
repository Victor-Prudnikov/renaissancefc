package com.learn.epam.victorprudnikov.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

public class PlayerTest {
	private final Player player1 = new Player(0, 55, "First", "Second", 5, 4, 1985, 20, 10, 180, 80, "Defender");
	private Player player2;
	
	@Test
	public void testGetNumber() {
		assertEquals(55, player1.getNumber());
	}
	
	@Test
	public void testSetNumber() {
		player2 = new Player(1, 50, "Name", "Surname", 25, 11, 1990, 10, 5, 170, 70, "Midfielder");
		player2.setNumber(60);
		assertEquals(60, player2.getNumber());
	}
	
	@Test
	public void testGetGoals() {
		assertEquals(10, player1.getGoals());
	}
	
	@Test
	public void testSetGoals() {
		player2 = new Player(1, 50, "Name", "Surname", 25, 11, 1990, 10, 5, 170, 70, "Midfielder");
		player2.setGoals(3);
		assertEquals(3, player2.getGoals());
	}
	
	@Test
	public void testGetHeight() {
		assertEquals(180, player1.getHeight());
	}
	
	@Test
	public void testSetHeight() {
		player2 = new Player(1, 50, "Name", "Surname", 25, 11, 1990, 10, 5, 170, 70, "Midfielder");
		player2.setHeight(190);
		assertEquals(190, player2.getHeight());
	}
	
	@Test
	public void testGetWeight() {
		assertEquals(80, player1.getWeight());
	}
	
	@Test
	public void testSetWeight() {
		player2 = new Player(1, 50, "Name", "Surname", 25, 11, 1990, 10, 5, 170, 70, "Midfielder");
		player2.setWeight(90);
		assertEquals(90, player2.getWeight());
	}
	
	@Test
	public void testGetPosition() {
		assertEquals("Defender", player1.getPosition());
	}
	
	@Test
	public void testSetPosition() {
		player2 = new Player(1, 50, "Name", "Surname", 25, 11, 1990, 10, 5, 170, 70, "Midfielder");
		player2.setPosition("Striker");
		assertEquals("Striker", player2.getPosition());
	}
}
