package com.learn.epam.victorprudnikov.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

import com.learn.epam.victorprudnikov.model.Player;

public class FieldPlayerComparatorTest {
	private FieldPlayerComparator comparator = new FieldPlayerComparator();
	private Player player1 = new Player(0, 20, "A", "B", 5, 4, 1985, 20, 10, 180, 80, "Defender");
	private Player player2 = new Player(1, 10, "C", "D", 25, 11, 1990, 10, 5, 170, 70, "Midfielder");
	private Player player3 = new Player(2, 10, "E", "F", 25, 11, 1990, 10, 5, 170, 70, "Striker");
	private Player player4 = new Player(3, 30, "G", "H", 10, 2, 1987, 5, 1, 175, 74, "Defender");
	private Player player5 = new Player(4, 25, "I", "J", 8, 3, 1988, 7, 2, 174, 73, "");
	private Player player6 = new Player(5, 10, "K", "L", 30, 9, 1986, 12, 2, 168, 69, "Midfielder");
	
	@Test
	public void test1Compare() {
		assertEquals(-1, comparator.compare(player1, player2));
	}
	
	@Test
	public void test2Compare() {
		assertEquals(-1, comparator.compare(player2, player3));
	}
	
	@Test
	public void test3Compare() {
		assertEquals(-1, comparator.compare(player1, player3));
	}
	
	@Test
	public void test4Compare() {
		assertEquals(0, comparator.compare(player5, player5));
	}
	
	@Test
	public void test5Compare() {
		assertEquals(0, comparator.compare(player2, player6));
	}
	
	@Test
	public void test6Compare() {
		assertEquals(1, comparator.compare(player3, player4));
	}
	
	@Test
	public void test7Compare() {
		assertEquals(1, comparator.compare(player5, player6));
	}
}
