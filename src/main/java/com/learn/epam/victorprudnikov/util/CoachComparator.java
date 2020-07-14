package com.learn.epam.victorprudnikov.util;

import java.util.Comparator;

import com.learn.epam.victorprudnikov.model.Coach;

//Comparator for sorting coaches by posts
public class CoachComparator implements Comparator<Coach>{
	public int compare(Coach coach1, Coach coach2) {
		Integer p1;
		Integer p2;
		switch(coach1.getPost()) {
		case "Head Coach": {
				p1 = 1;
				break;
			}
		case "Assistent Coach": {
				p1 = 2;
				break;
			}
		case "Goalkeeper Coach": {
				p1 = 3;
				break;
			}
			default: {
				p1 = 4;
			}
		}
		switch(coach2.getPost()) {
		case "Head Coach": {
				p2 = 1;
				break;
			}
		case "Assistent Coach": {
				p2 = 2;
				break;
			}
		case "Goalkeeper Coach": {
				p2 = 3;
				break;
			}
			default: {
				p2 = 4;
			}
		}
		return p1.compareTo(p2);
	}
}
