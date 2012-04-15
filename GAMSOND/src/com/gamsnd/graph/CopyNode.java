package com.gamsnd.graph;

import java.util.ArrayList;

public class CopyNode {
	int ID;
	boolean visited;
	CopyNode prevID;
	ArrayList<Integer> setClose;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public CopyNode getPrevID() {
		return prevID;
	}

	public void setPrevID(CopyNode prevID) {
		this.prevID = prevID;
	}

	public ArrayList<Integer> getSetClose() {
		return setClose;
	}

	public void setSetClose(ArrayList<Integer> setClose) {
		this.setClose = setClose;
	}

	public CopyNode(int iD, boolean visited, CopyNode prevID,
			ArrayList<Integer> setClose) {
		super();
		ID = iD;
		this.visited = visited;
		this.prevID = prevID;
		this.setClose = setClose;
	}

}
