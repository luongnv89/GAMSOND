package com.gamsond03.object;

import java.util.ArrayList;

/**
 * Present a physical path of a request
 * 
 * */
public class Gen {
	private int id;
	private ArrayList<Node> ListNodeGen;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public final ArrayList<Node> getListNodeGen() {
		return ListNodeGen;
	}

	public final void setListNodeGen(ArrayList<Node> listNodeGen) {
		ListNodeGen = listNodeGen;
	}

	public Gen(int id, ArrayList<Node> listNodeGen) {
		super();
		this.id = id;
		ListNodeGen = listNodeGen;
	}

	

}
