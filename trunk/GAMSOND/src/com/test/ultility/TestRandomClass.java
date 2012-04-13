package com.test.ultility;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.gamsnd.process.RandomInteger;

public class TestRandomClass {
	public static void main(String[] args){
		ArrayList<Integer> array = new ArrayList<Integer>();
		array.add(1);
		array.add(3);
		array.add(5);
		array.add(7);
		array.add(9);
		array.add(11);
		array.add(13);
		array.add(15);
		
		RandomInteger test = new RandomInteger(16, array);
		int x = test.RandomGenerator2();
		System.out.println(x);
	}
}	
