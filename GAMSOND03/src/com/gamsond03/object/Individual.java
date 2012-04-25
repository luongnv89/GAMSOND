package com.gamsond03.object;

import java.util.ArrayList;

public class Individual {
	private int ID;
	private double fitness;
	ArrayList<Chromosome> listChromosomes;
	public final int getID() {
		return ID;
	}
	public final void setID(int iD) {
		ID = iD;
	}
	public final double getFitness() {
		return fitness;
	}
	public final void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public final ArrayList<Chromosome> getListChromosomes() {
		return listChromosomes;
	}
	public final void setListChromosomes(ArrayList<Chromosome> listChromosomes) {
		this.listChromosomes = listChromosomes;
	}
	public Individual(int iD, double fitness,
			ArrayList<Chromosome> listChromosomes) {
		super();
		ID = iD;
		this.fitness = fitness;
		this.listChromosomes = listChromosomes;
	}
	
	
	
}
