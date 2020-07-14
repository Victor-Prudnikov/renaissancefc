package com.learn.epam.victorprudnikov.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

public class PositionTest {
	
	@Test
	public void testGetPosition() {
		assertEquals("Defender", Position.DEFENDER.getPosition());
	}
}
