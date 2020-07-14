package com.learn.epam.victorprudnikov.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.api.*;

public class PersonTest {	
	private final Person person1 = new Person(0, "First", "Second", 5, 4, 1980, 2);
	private Person person2;
	
	@Test
	public void testGetId() {
		assertEquals(0, person1.getId());
	}
	
	@Test
	public void testSetId() {
		person2 = new Person(1, "Name", "Surname", 25, 11, 1990, 1);
		person2.setId(0);
		assertEquals(0, person2.getId());
	}
	
	@Test
	public void testGetName() {
		assertEquals("First", person1.getName());
	}
	
	@Test
	public void testSetName() {
		person2 = new Person(1, "Name", "Surname", 25, 11, 1990, 1);
		person2.setName("First");
		assertEquals("First", person2.getName());
	}
	
	@Test
	public void testGetSurname() {
		assertEquals("Second", person1.getSurname());
	}
	
	@Test
	public void testSetSurname() {
		person2 = new Person(1, "Name", "Surname", 25, 11, 1990, 1);
		person2.setSurname("Second");
		assertEquals("Second", person2.getSurname());
	}
	
	@Test
	public void testGetBirthDate() {
		assertEquals("05 May 1980", person1.getBirthDate());
	}
	
	@Test
	public void testSetBirthDate() {
		person2 = new Person(1, "Name", "Surname", 25, 11, 1990, 1);
		person2.setBirthDate(28, 8, 1985);
		assertEquals("28 September 1985", person2.getBirthDate());
	}
	
	@Test
	public void testGetGames() {
		assertEquals(2, person1.getGames());
	}
	
	@Test
	public void testSetGames() {
		person2 = new Person(1, "Name", "Surname", 25, 11, 1990, 1);
		person2.setGames(3);
		assertEquals(3, person2.getGames());
	}
	
	@Test
	public void testGetAge() {
		assertEquals(LocalDate.now().minus(Period.of(1980, 4, 5)).getYear(), person1.getAge());
	}
	
	@Test
	public void testGetBirthDateForForm() {
		assertEquals("1980-05-05", person1.getBirthDateForForm());
	}
	
	@Test
	public void testGetBirthDay() {
		assertEquals(5, person1.getBirthDay());
	}
	
	@Test
	public void testGetBirthMonth() {
		assertEquals(4, person1.getBirthMonth());
	}
	
	@Test
	public void testGetBirthYear() {
		assertEquals(1980, person1.getBirthYear());
	}
}
