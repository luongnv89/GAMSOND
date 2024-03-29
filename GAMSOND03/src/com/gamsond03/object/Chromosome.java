package com.gamsond03.object;


public class Chromosome {

	int id;
	private Gen workingGen;
	private Gen backupGen;

	public final Gen getWorkingGen() {
		return workingGen;
	}

	public final void setWorkingGen(Gen workingGen) {
		this.workingGen = workingGen;
	}

	public final Gen getBackupGen() {
		return backupGen;
	}

	public final void setBackupGen(Gen backupGen) {
		this.backupGen = backupGen;
	}

	public Chromosome(int id , Gen workingGen, Gen backupGen) {
		super();
		this.workingGen = workingGen;
		this.backupGen = backupGen;
		this.id = id;
	}

}
