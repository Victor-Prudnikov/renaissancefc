package com.learn.epam.victorprudnikov.util;

public class EmptyNumberException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public EmptyNumberException() {
		super("Number of the player cannot be empty. You must fill the number.");
	}
	
	@Override
	public String toString() {
		return "EmptyNumberException";
	}
}
