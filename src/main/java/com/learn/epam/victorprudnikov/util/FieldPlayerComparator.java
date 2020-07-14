package com.learn.epam.victorprudnikov.util;

import java.util.Comparator;

import com.learn.epam.victorprudnikov.model.Player;

//Comparator for sorting field players first by position and second by number
public class FieldPlayerComparator implements Comparator<Player>{
	public int compare(Player keeper1, Player keeper2) {
		Integer p1;
		Integer p2;
		Integer n1 = keeper1.getNumber();
		Integer n2 = keeper2.getNumber();
		switch(keeper1.getPosition()) {
		case "Defender": {
				p1 = 1;
				break;
			}
		case "Midfielder": {
				p1 = 2;
				break;
			}
		case "Striker": {
				p1 = 3;
				break;
			}
			default: {
				p1 = 4;
			}
		}
		switch(keeper2.getPosition()) {
		case "Defender": {
				p2 = 1;
				break;
			}
		case "Midfielder": {
				p2 = 2;
				break;
			}
		case "Striker": {
				p2 = 3;
				break;
			}
			default: {
				p2 = 4;
			}
		}
		if (p1.compareTo(p2) != 0) {
			return p1.compareTo(p2);
		}
		else {
			return n1.compareTo(n2);
		}
	}
}
