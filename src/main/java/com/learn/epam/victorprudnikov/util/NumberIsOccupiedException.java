package com.learn.epam.victorprudnikov.util;

public class NumberIsOccupiedException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NumberIsOccupiedException() {
		super("This number is occupied. You must choose free one, two players cannot have the same number.");
	}
	
	@Override
	public String toString() {
		return "NumberIsOccupiedException";
	}
}
