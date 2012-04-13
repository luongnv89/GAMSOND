package com.gamsnd.object;

public class Individual {
	private int ID;
	private double fitness;
	private Chromosome wChromosome;
	private Chromosome bChromosome;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {

		this.fitness = fitness;
	}

	public Chromosome getwChromosome() {
		return wChromosome;
	}

	public void setwChromosome(Chromosome wChromosome) {
		this.wChromosome = wChromosome;
	}

	public Chromosome getbChromosome() {
		return bChromosome;
	}

	public void setbChromosome(Chromosome bChromosome) {
		this.bChromosome = bChromosome;
	}

	public Individual(int iD, Chromosome wChromosome, Chromosome bChromosome) {
		super();
		ID = iD;
		this.wChromosome = wChromosome;
		this.bChromosome = bChromosome;
	}

}
