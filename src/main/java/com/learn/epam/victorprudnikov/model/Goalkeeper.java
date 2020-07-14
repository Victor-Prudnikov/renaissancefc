package com.learn.epam.victorprudnikov.model;

public class Goalkeeper extends Player{
	private int goalsConceded;
	private int penaltySaved;
	
	public Goalkeeper(int id, int number, String name, String surname, int birthDay, int birthMonth, int birthYear,
			int games, int goals, int height, int weigth, int goalsConceded, int penaltySaved, String position) {
		super(id, number, name, surname, birthDay, birthMonth, birthYear, games, goals, height, weigth, position);
		this.goalsConceded = goalsConceded;
		this.penaltySaved = penaltySaved;
	}

	public int getGoalsConceded() {
		return goalsConceded;
	}
	public void setGoalsConceded(int goalsConceded) {
		this.goalsConceded = goalsConceded;
	}
	public int getPenaltySaved() {
		return penaltySaved;
	}
	public void setPenaltySaved(int penaltySaved) {
		this.penaltySaved = penaltySaved;
	}

}
