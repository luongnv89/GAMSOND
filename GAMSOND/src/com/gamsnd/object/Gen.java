package com.gamsnd.object;

import java.util.ArrayList;

/**
 * Present a physical path of a request
 * 
 * */
public class Gen {
	private int id;	//id of path 
//	private int requestID;
	private ArrayList<Edge> listEdge;
	public int getId() {
		return id; 
	}
	public void setId(int id) {
		this.id = id;
	}
//	public int getRequestID() {
//		return requestID;
//	}
//	public void setRequestID(int requestID) {
//		this.requestID = requestID;
//	}
	public ArrayList<Edge> getListEdge() {
		return listEdge;
	}
	public void setListEdge(ArrayList<Edge> listEdge) {
		this.listEdge = listEdge;
	}
	public Gen(int id, ArrayList<Edge> listEdge) {
		super();
		this.id = id;
//		this.requestID = requestID;
		this.listEdge = listEdge;
	}
		
}
