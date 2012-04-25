package com.gamsond03.object;

import java.util.ArrayList;

public class Request {
	int id;
	private Node oNode;
	private Node dNode;
	private ArrayList<Node> path1;
	private ArrayList<Node> path2;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Node getoNode() {
		return oNode;
	}

	public void setoNode(Node oNode) {
		this.oNode = oNode;
	}

	public Node getdNode() {
		return dNode;
	}

	public void setdNode(Node dNode) {
		this.dNode = dNode;
	}

	public ArrayList<Node> getPath1() {
		return path1;
	}

	public void setPath1(ArrayList<Node> path1) {
		this.path1 = path1;
	}

	public ArrayList<Node> getPath2() {
		return path2;
	}

	public void setPath2(ArrayList<Node> path2) {
		this.path2 = path2;
	}

	public Request(int id, Node oNode, Node dNode, ArrayList<Node> path1,
			ArrayList<Node> path2) {
		super();
		this.id = id;
		this.oNode = oNode;
		this.dNode = dNode;
		this.path1 = path1;
		this.path2 = path2;
	}

}
