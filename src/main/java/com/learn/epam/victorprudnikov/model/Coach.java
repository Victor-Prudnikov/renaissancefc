package com.learn.epam.victorprudnikov.model;

public class Coach extends Person{
	private String post;

	public Coach(int id, String name, String surname, int birthDay, int birthMonth, int birthYear, int games, String post) {
		super(id, name, surname, birthDay, birthMonth, birthYear, games);
		this.post = post;
	}

	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
		

}
