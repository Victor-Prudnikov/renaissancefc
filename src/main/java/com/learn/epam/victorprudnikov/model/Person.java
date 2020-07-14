package com.learn.epam.victorprudnikov.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Person {
	private int id;
	private String name;
	private String surname;
	private Calendar birthDate;
	private int games;
	
	public Person(int id, String name, String surname, int birthDay, int birthMonth, int birthYear, int games) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		birthDate = new GregorianCalendar(birthYear, birthMonth, birthDay);
		this.games = games;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getBirthDate() {
		DateFormat df = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
		return df.format(birthDate.getTime());
	}
	public void setBirthDate(int birthDay, int birthMonth, int birthYear) {
		birthDate.set(Calendar.DAY_OF_MONTH, birthDay);
		birthDate.set(Calendar.MONTH, birthMonth);
		birthDate.set(Calendar.YEAR, birthYear);
	}
	public int getGames() {
		return games;
	}
	public void setGames(int games) {
		this.games = games;
	}
	public int getAge() {
		return LocalDate.now().minus(Period.of(this.getBirthYear(), this.getBirthMonth(), 
				this.getBirthDay())).getYear();
	}
	// Get date of birth view for the HTML-form.
	public String getBirthDateForForm() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(birthDate.getTime());
	}
	public int getBirthDay() {
		return birthDate.get(Calendar.DAY_OF_MONTH);
	}
	public int getBirthMonth() {
		return birthDate.get(Calendar.MONTH);
	}
	public int getBirthYear() {
		return birthDate.get(Calendar.YEAR);
	}
}
