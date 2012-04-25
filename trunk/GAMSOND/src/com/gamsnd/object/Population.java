package com.gamsnd.object;

import java.util.ArrayList;

public class Population {
	private int populationID;
	private ArrayList<Individual> listIndividual;

	public int getpopulationID() {
		return populationID;
	}

	public void setpopulationID(int generationOrder) {
		this.populationID = generationOrder;
	}

	public ArrayList<Individual> getListIndividual() {
		return listIndividual;
	}

	public void setListIndividual(ArrayList<Individual> listIndividual) {
		this.listIndividual = listIndividual;
	}

	// Khoi tao quan the
	public Population(int generationOrder, ArrayList<Individual> listIndividual) {
		// super();
		this.populationID = generationOrder;
		this.listIndividual = listIndividual;
	}

	// Lay ra ca the tot nhat
	public Individual getBestIndividual() {
		Individual best = listIndividual.get(0);
		for (int noIndividual = 1; noIndividual < listIndividual.size(); noIndividual++) {
			if (best.getFitness() > listIndividual.get(noIndividual)
					.getFitness()) {
				best = listIndividual.get(noIndividual);
			}
		}
		return best;
	}

}
