package com.learn.epam.victorprudnikov.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

import com.learn.epam.victorprudnikov.model.Coach;

public class CoachComparatorTest {
	private CoachComparator comparator = new CoachComparator();
	private Coach coach1 = new Coach(0, "A", "B", 5, 4, 1980, 2, "Head Coach");
	private Coach coach2 = new Coach(1, "C", "D", 25, 10, 1985, 3, "Assistent Coach");
	private Coach coach3 = new Coach(2, "E", "F", 13, 8, 1987, 5, "");
	private Coach coach4 = new Coach(3, "G", "H", 17, 2, 1982, 4, "Goalkeeper Coach");
	
	@Test
	public void test1Compare() {
		assertEquals(-1, comparator.compare(coach1, coach2));
	}
	
	@Test
	public void test2Compare() {
		assertEquals(0, comparator.compare(coach1, coach1));
	}
	
	@Test
	public void test3Compare() {
		assertEquals(1, comparator.compare(coach3, coach4));
	}
	
	@Test
	public void test4Compare() {
		assertEquals(-1, comparator.compare(coach2, coach3));
	}
	
	@Test
	public void test5Compare() {
		assertEquals(1, comparator.compare(coach4, coach2));
	}
}
