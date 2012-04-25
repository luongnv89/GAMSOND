package com.gamsnd.object;

import java.util.ArrayList;

public class Chromosome {

	private ArrayList<Gen> listGens;

	public ArrayList<Gen> getListGens() {
		return listGens;
	}

	public void setListGens(ArrayList<Gen> listGens) {
		this.listGens = listGens;
	}

	public Chromosome(ArrayList<Gen> listGens) {
		super();
		this.listGens = listGens;
	}

}
