package com.gamsnd.object;

public class Node {
	private int x;
	private int y;
	private int id;
	private String name;
	private boolean isLogical; // The hien nut hien tai co nam tren do thi Logical khong?

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isLogical() {
		return isLogical;
	}

	public void setLogical(boolean isLogical) {
		this.isLogical = isLogical;
	}

	// Khoi tao 1 node
	public Node(int x, int y, int id, String name, boolean isLogical) {
		super();
		this.x = x;
		this.y = y;
		this.id = id;
		this.name = name;
		this.isLogical = isLogical;
	}
	
	public boolean equals(Node a){
		boolean yes = false;
		if(a.getId() == this.id) yes = true;
		return yes;
	}

}
