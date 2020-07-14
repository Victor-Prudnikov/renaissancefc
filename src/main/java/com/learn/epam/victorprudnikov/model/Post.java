package com.learn.epam.victorprudnikov.model;

//Coaches posts
public enum Post {
	HEAD ("Head Coach"),
	ASSISTENT ("Assistent Coach"),
	KEEPER ("Goalkeeper Coach");
	
	private String post;
	
	Post (String post) {
		this.post = post;
	}
	
	public String getPost() {
		return post;
	}
}
