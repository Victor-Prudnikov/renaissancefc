package com.learn.epam.victorprudnikov.model;

//Field players position
public enum Position {
	DEFENDER ("Defender"),
	MIDFIELDER ("Midfielder"),
	STRIKER ("Striker");
	
	private String position;
	
	Position (String position) {
		this.position = position;
	}
	
	public String getPosition() {
		return position;
	}
}
