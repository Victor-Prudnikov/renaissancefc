package com.learn.epam.victorprudnikov.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

public class PostTest {
	
	@Test
	public void testGetPost() {
		assertEquals("Head Coach", Post.HEAD.getPost());
	}
}
