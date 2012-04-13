package com.gamsnd.object;

public class Edge {
	private int aNode;
	private int bNode;
	private int edgeID;
	private boolean used; // Danh dau canh da duoc su dung chua. Neu used = true -> da duoc su dung truoc do
	private double weight; // Trong so cua canh
	
	public int getaNode() {
		return aNode;
	}
	public void setaNode(int aNode) {
		this.aNode = aNode;
	}
	public int getbNode() {
		return bNode;
	}
	public void setbNode(int bNode) {
		this.bNode = bNode;
	}
	public int getEdgeID() {
		return edgeID;
	}
	public void setEdgeID(int edgeID) {
		this.edgeID = edgeID;
	}
	public boolean isUsed() {
		return used;
	}
	public void setUsed(boolean used) {
		this.used = used;
	}
	public double getWeight() {
		return weight;
	}
	
	// Tinh trong so cho canh
	public void setWeight(float x) {
		this.weight = x;
	}
	
	// Khoi tao mot canh
	public Edge(int aNode, int bNode, int edgeID, boolean used, double weight2) {
		super();
		this.aNode = aNode;
		this.bNode = bNode;
		this.edgeID = edgeID;
		this.used = used;
		this.weight = weight2;
	}
	

}
