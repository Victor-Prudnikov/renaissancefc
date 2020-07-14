package com.learn.epam.victorprudnikov.util;

public class EmptyNameException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public EmptyNameException() {
		super("Name and surname cannot be empty. You must fill one or both fields.");
	}
	
	@Override
	public String toString() {
		return "EmptyNameException";
	}
}
