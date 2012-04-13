package com.gamsnd.process;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.text.BoxView;

public class RandomInteger {
	int size;
	int x;
	ArrayList<Integer> y;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public RandomInteger(int size) {
		// TODO Auto-generated constructor stub
		this.size = size;
	}

	public RandomInteger(int size, int x) {
		super();
		this.size = size;
		this.x = x;
	}

	public int RandomGenerator() {
		Random random = new Random();
		int temp;
		temp = random.nextInt(size);
		while (temp == x) {
			temp = random.nextInt(size);
		}
		return temp;
	}

	public RandomInteger(int size, ArrayList<Integer> y) {
		this.size = size;
		this.y = y;
	}

	public int RandomGenerator2() {
		int temp;
		boolean exist = false;
		Random random = new Random();
		do {
			temp = random.nextInt(size);
			exist = false;
			for (int i = 0; i < y.size(); i++) {
				if (temp == y.get(i)) {
					exist = true;
				}
			}
			
		} while (exist != false);
		return temp;
	}

}