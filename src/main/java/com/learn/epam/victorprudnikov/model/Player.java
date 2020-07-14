package com.learn.epam.victorprudnikov.model;

public class Player extends Person{
	private int number;
	private int goals;
	private int height;
	private int weight;
	private String position;
	
	public Player(int id, int number, String name, String surname, int birthDay, int birthMonth, int birthYear, 
			int games, int goals, int height, int weight, String position) {
		super(id, name, surname, birthDay, birthMonth, birthYear, games);
		this.number = number;
		this.goals = goals;
		this.height = height;
		this.weight = weight;
		this.position = position;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getGoals() {
		return goals;
	}
	public void setGoals(int goals) {
		this.goals = goals;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
}
