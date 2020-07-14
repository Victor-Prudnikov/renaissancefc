package com.learn.epam.victorprudnikov.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

import com.learn.epam.victorprudnikov.model.Goalkeeper;

public class GoalkeeperComparatorTest {
	private GoalkeeperComparator comparator = new GoalkeeperComparator();
	private Goalkeeper goalkeeper1 = 
			new Goalkeeper(0, 55, "First", "Second", 5, 4, 1985, 20, 0, 180, 80, 10, 2, "Goalkeeper");
	private Goalkeeper goalkeeper2 = 
			new Goalkeeper(1, 20, "First", "Second", 10, 8, 1990, 25, 1, 185, 85, 15, 1, "Goalkeeper");
	
	@Test
	public void test1Compare() {
		assertEquals(1, comparator.compare(goalkeeper1, goalkeeper2));
	}
	
	@Test
	public void test2Compare() {
		assertEquals(-1, comparator.compare(goalkeeper2, goalkeeper1));
	}
	
	@Test
	public void test3Compare() {
		assertEquals(0, comparator.compare(goalkeeper1, goalkeeper1));
	}
}
