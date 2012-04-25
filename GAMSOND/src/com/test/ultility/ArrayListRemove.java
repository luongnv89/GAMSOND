package com.test.ultility;

import java.util.ArrayList;

public class ArrayListRemove {
	public static void main(String[] args){
		ArrayList<Integer> totalArray = new ArrayList<Integer>();
		for(int i=0;i<20;i++){
			totalArray.add(i);
		}
		System.out.println(totalArray.toString());
		ArrayList<Integer> oldArray = new ArrayList<Integer>();
		for(int i=1;i<20;i=i+2){
			oldArray.add(i);
		}
		System.out.println(oldArray.toString());
//		ArrayList<Integer> newArray = new ArrayList<Integer>();
		totalArray.removeAll(oldArray);
		System.out.println(totalArray.toString());
	}
	
}
