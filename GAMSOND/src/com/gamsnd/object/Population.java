package com.gamsnd.object;

import java.util.ArrayList;
import java.util.Random;

import com.gamsnd.process.RandomInteger;

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

	// Thuc hien phep lai giua 2 phan tu
//	public Individual cross(Individual a, Individual b) {
//		Individual c = null;
//		return c;
//	}

	// Thuc hien dot bien
	/****
	 * Chi thuc hien dot bien tren working path
	 * Do viec dot bien tren backup khong co nhieu y nghia nhu viec dot bien tren working path
	 */
//	private Individual mutant(Individual a) {
//		// TODO Auto-generated method stub
//		Individual c = null;
//		Random random = new Random();
//		// Vi tri dot bien ngau nhien
//		int mutantPosition = random.nextInt(a.getwChromosome().getListGens().size());
//		
//		// Thay gia tri gen se dot bien bang 1 gen khac
//		// kich thuoc bo gen ung voi gen se dot bien
//		int lisGenSize = a.getwChromosome().getListGens().size();
//		// Chi so gen se dot bien
//		int mutantGen = a.getwChromosome().getListGens().get(mutantPosition).getId();
//		
//		// Chi so gen thay the
//		RandomInteger replaceGen = new RandomInteger(lisGenSize, mutantGen);
//		int replaceGenid = replaceGen.RandomGenerator();
//		// Thay doi gia tri gen o vi tri dot bien tren working path
//		a.getwChromosome().getListGens().get(mutantPosition).setId(replaceGenid);
//		// Chi so gen thay the tuong ung o backup
//		a.getbChromosome().getListGens().get(mutantGen).getId();
//		int replaceBackupid = random.nextInt();
//		
//		
//		return c;
//	}

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

	// Sinh quan the moi tu quan the hien tai
	public Population newPopulation() {
		Population newPopulation = null;
		return newPopulation;
	}

}
