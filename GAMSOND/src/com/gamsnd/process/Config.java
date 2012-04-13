package com.gamsnd.process;

public class Config {
	public static final int POPULATION = 30; // So ca the trong quan the
	public static final int GENERATION = 10; // So lan lap cua giai thuat di truyen
	public static final int NOCROSS = 10; // So ca the cha me mang ra lai ghep
	public static final int NOMUTANT = 10; // So ca the dot bien
	public static final String DATAFILE = "instances_alea/a10_8_10.txt";

	public int getPOPULATION() {
		return POPULATION;
	}

	public int getGENERATION() {
		return GENERATION;
	}

	public String getDATAFILE() {
		return DATAFILE;
	}

}
