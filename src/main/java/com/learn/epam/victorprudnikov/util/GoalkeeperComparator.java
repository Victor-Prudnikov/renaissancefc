package com.learn.epam.victorprudnikov.util;

import java.util.Comparator;

import com.learn.epam.victorprudnikov.model.Goalkeeper;

//Comparator for sorting goalkeepers by numbers
public class GoalkeeperComparator implements Comparator<Goalkeeper>{
	public int compare(Goalkeeper keeper1, Goalkeeper keeper2) {
		Integer n1 = keeper1.getNumber();
		Integer n2 = keeper2.getNumber();
		return n1.compareTo(n2);
	}
}
