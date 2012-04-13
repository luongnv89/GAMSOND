package com.gamsnd.object;

import java.util.ArrayList;

public class Chromosome {
//	private ArrayList<Gen> workingPaths;
//	private ArrayList<Gen> backupPahts;
	private ArrayList<Gen> listGens;
//	private double fitness;
	public ArrayList<Gen> getListGens() {
		return listGens;
	}
	public void setListGens(ArrayList<Gen> listGens) {
		this.listGens = listGens;
	}
//	public double getFitness() {
//		return fitness;
//	}
//	public void setFitness(double fitness) {
//		this.fitness = fitness;
//	}
	public Chromosome(ArrayList<Gen> listGens) {
		super();
		this.listGens = listGens;
//		this.fitness = fitness;
	}
	
//	public ArrayList<Gen> getWorkingPaths() {
//		return workingPaths;
//	}
//
//	public void setWorkingPaths(ArrayList<Gen> workingPaths) {
//		this.workingPaths = workingPaths;
//	}
//
//	public ArrayList<Gen> getBackupPahts() {
//		return backupPahts;
//	}
//
//	public void setBackupPahts(ArrayList<Gen> backupPahts) {
//		this.backupPahts = backupPahts;
//	}
//
//	public Chromosome(ArrayList<Gen> workingPaths, ArrayList<Gen> backupPahts) {
//		super();
//		this.workingPaths = workingPaths;
//		this.backupPahts = backupPahts;
//	}
	
	

}
