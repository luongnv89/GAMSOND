package com.gamsnd.object;

import java.util.ArrayList;

public class Request {
	int id;
	private int oNode;
	private int dNode;
	private ArrayList<Integer> path1;
	private ArrayList<Integer> path2;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getoNode() {
		return oNode;
	}
	public void setoNode(int oNode) {
		this.oNode = oNode;
	}
	public int getdNode() {
		return dNode;
	}
	public void setdNode(int dNode) {
		this.dNode = dNode;
	}
	public ArrayList<Integer> getPath1() {
		return path1;
	}
	public void setPath1(ArrayList<Integer> path1) {
		this.path1 = path1;
	}
	public ArrayList<Integer> getPath2() {
		return path2;
	}
	public void setPath2(ArrayList<Integer> path2) {
		this.path2 = path2;
	}
	public Request(int id, int oNode, int dNode, ArrayList<Integer> path1,
			ArrayList<Integer> path2) {
		super();
		this.id = id;
		this.oNode = oNode;
		this.dNode = dNode;
		this.path1 = path1;
		this.path2 = path2;
	}
	
	
	
	
}
