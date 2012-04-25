package com.test.ultility;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

public class TestLinkArray {
	public static void main(String[] args) {
		ArrayList<ArrayList<Integer>> listArrayList = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < 5; i++) {
			ArrayList<Integer> temArrayList = new ArrayList<Integer>();
			for (int j = 1; j < i + 3; j++) {
				int tempInt = j * i;
				temArrayList.add(tempInt);
			}
			listArrayList.add(temArrayList);
		}
		ArrayList<Integer> resultArrayList = linkPath(listArrayList);

		System.out.println("Mang dau vao: ");
		for (int i = 0; i < listArrayList.size(); i++) {
			showArrayList(listArrayList.get(i));
		}

		System.out.println("Mang ket qua: ");
		showArrayList(resultArrayList);

	}

	public static ArrayList<Integer> linkPath(
			ArrayList<ArrayList<Integer>> setPath) {
		ArrayList<Integer> tempLinkPath = new ArrayList<Integer>();
		int lastID = setPath.get(setPath.size() - 1).get(
				setPath.get(setPath.size() - 1).size() - 1);
		for (int i = 0; i < setPath.size(); i++) {
			for (int j = 0; j < setPath.get(i).size() - 1; j++) {
				tempLinkPath.add(setPath.get(i).get(j));
			}
		}
		tempLinkPath.add(lastID);
		return tempLinkPath;
	}

	public static void showArrayList(ArrayList<Integer> temArrayList) {
		System.out.println("Cac phan tu cua mang: ");
		for (int i = 0; i < temArrayList.size(); i++) {
			System.out.print(temArrayList.get(i) + " ");
		}
	}

}
