/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import gasn.ProcessUnit;
import java.util.ArrayList;
import java.util.Random;
/**
 *
 * @author Dragoon
 */
public class Individual {
    private Chromosome workingChromosome;
    private Chromosome backupChromosome;
    private int fitness;
    private int id;
    private boolean used;

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public Chromosome getBackupChromosome() {
        return backupChromosome;
    }

    public void setBackupChromosome(Chromosome backupChromosome) {
        this.backupChromosome = backupChromosome;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Chromosome getWorkingChromosome() {
        return workingChromosome;
    }

    public void setWorkingChromosome(Chromosome workingChromosome) {
        this.workingChromosome = workingChromosome;
    }
  
    public Individual combineType1(Individual dad,Individual mom, ArrayList<ArrayList<Connection>> connectionDB){
        Random randomGenerator=new Random();
        Individual tempIndividual=new Individual();
        tempIndividual.setWorkingChromosome(dad.getWorkingChromosome());
        tempIndividual.setBackupChromosome(mom.getBackupChromosome());
        for (int i=1;i<=tempIndividual.getWorkingChromosome().getGenList().size();i++){
            if (tempIndividual.getConnectionID(2, i) > connectionDB.get(i).get(tempIndividual.getConnectionID(1, i)).getBackupPath().size()){
            	tempIndividual.setConnectionID(2, i,randomGenerator.nextInt(connectionDB.get(i).get(tempIndividual.getConnectionID(1, i)).getBackupPath().size()-1)+1);
            }
	}
        return tempIndividual;
    }
    
    public Individual combineType3(Individual dad,Individual mom, ArrayList<ArrayList<Connection>> connectionDB){
        Individual tempIndividual=new Individual();
        Random random=new Random();
        int temp=random.nextInt(dad.getWorkingChromosome().getGenList().size());
        
        return tempIndividual;
    }
    
    public Individual combineType2(Individual dad, Individual mom, ArrayList<ArrayList<Connection>> connectionDB){
        Individual tempIndividual=new Individual();
        Random randomGenerator=new Random();
	int temp=randomGenerator.nextInt(dad.getWorkingChromosome().getGenList().size());
	for (int i=0;i<temp;i++){
            tempIndividual.getWorkingChromosome().getGenList().add(dad.getWorkingChromosome().getGenList().get(i));
            tempIndividual.getBackupChromosome().getGenList().add(mom.getBackupChromosome().getGenList().get(i));
            int temp2=randomGenerator.nextInt(Common.MUTANT_RATE);
            if ((temp2==0)&&(!connectionDB.get(i).isEmpty())){
                tempIndividual.mutant(connectionDB,i);
            }
	}
	for (int i=temp;i<dad.getWorkingChromosome().getGenList().size();i++){
            tempIndividual.getWorkingChromosome().getGenList().add(mom.getWorkingChromosome().getGenList().get(i));
            tempIndividual.getBackupChromosome().getGenList().add(dad.getBackupChromosome().getGenList().get(i));
            int temp2=randomGenerator.nextInt(Common.MUTANT_RATE);
            if ((temp2==0)&&(!connectionDB.get(i).isEmpty())){
                tempIndividual.mutant(connectionDB,i);
            }
	}
	for (int i=0;i<tempIndividual.getBackupChromosome().getGenList().size();i++)
	{
            //System.out.println(i);
            //If there're connections avaiable
            if (tempIndividual.getConnectionID(1, i)!=-1){
//                If backupID>= size of back up path
                if (tempIndividual.getConnectionID(2, i) >=connectionDB.get(i).get(tempIndividual.getConnectionID(1, i)).getBackupPath().size()){
                    tempIndividual.getBackupChromosome().getGenList().get(i).setConnectionID(randomGenerator.nextInt(connectionDB.get(i).get(tempIndividual.getConnectionID(1, i)).getBackupPath().size()));
                }
            } else {
                tempIndividual.getBackupChromosome().getGenList().get(i).setConnectionID(-1);
            }                
	}        
        return tempIndividual;       
    }
   
    public void mutant(ArrayList<ArrayList<Connection>> connectionDB,int temp){
	Random randomGenerator=new Random();
        int temp2=randomGenerator.nextInt(connectionDB.get(temp).size());
	this.setConnectionID(1, temp, temp2);
	if (this.getConnectionID(2, temp) >=connectionDB.get(temp).get(this.getConnectionID(1, temp)).getBackupPath().size()){
            this.getBackupChromosome().getGenList().get(temp).setConnectionID(randomGenerator.nextInt(connectionDB.get(temp).get(this.getConnectionID(1, temp)).getBackupPath().size()));
	}
    }
   
    public int getConnectionID(int parents,int i){
        if (parents==1) return this.workingChromosome.getGenList().get(i).getConnectionID();        
        else return this.backupChromosome.getGenList().get(i).getConnectionID();
    }
  
    public void setConnectionID(int choice,int i,int value){
        if (choice==1) this.workingChromosome.getGenList().get(i).setConnectionID(value);
        else this.backupChromosome.getGenList().get(i).setConnectionID(value);
    }
  
    public Individual(int id) {
        this.id = id;
        this.backupChromosome=new Chromosome();
        this.workingChromosome=new Chromosome();
        this.fitness=Common.MAXCOST;
    }

    public Individual() {
        this.id=0;
        this.backupChromosome=new Chromosome();
        this.workingChromosome=new Chromosome();
        this.fitness=Common.MAXCOST;
    }
   
    public Individual(Individual individual){
        this.fitness=individual.fitness;
        this.id=individual.id;
        this.used=individual.used;
        this.workingChromosome=new Chromosome();
        for(int i=0;i<individual.workingChromosome.getGenList().size();i++){
            Gen tempGen=new Gen();
            tempGen.setConnectionID(individual.workingChromosome.getGenList().get(i).getConnectionID());
            tempGen.setId(i);
            this.workingChromosome.getGenList().add(tempGen);
        }
        this.backupChromosome=new Chromosome();
        for(int i=0;i<individual.backupChromosome.getGenList().size();i++){
            Gen tempGen=new Gen();
            tempGen.setConnectionID(individual.backupChromosome.getGenList().get(i).getConnectionID());
            tempGen.setId(i);
            this.backupChromosome.getGenList().add(tempGen);
        }
    }
  
    public Individual(int i,ProcessUnit processUnit,ArrayList<ArrayList<Connection>> connectionDB,ArrayList<Cast> castList,Graph graph){
        Graph tempGraph=new Graph(graph);
        
        this.backupChromosome=new Chromosome();
        this.workingChromosome=new Chromosome();
        
        for (int j=0;j<castList.size();j++){
        int temp=0;
        Gen tempGen1=new Gen(),
                        tempGen2=new Gen();
		//if there is no choice
		if (connectionDB.get(j).isEmpty()){
                    tempGen1.setId(j);
                    tempGen2.setId(j);
                    tempGen1.setConnectionID(-1);
                    tempGen2.setConnectionID(-1);
                    this.getWorkingChromosome().getGenList().add(tempGen1);
                    this.getBackupChromosome().getGenList().add(tempGen2);
                } else {
                    //if there is a choice
                    temp = new Random().nextInt(connectionDB.get(j).size());
                    tempGen1.setId(j);
                    tempGen1.setConnectionID(temp);
                    this.getWorkingChromosome().getGenList().add(tempGen1);
                    temp = new Random().nextInt(connectionDB.get(j).get(temp).getBackupPath().size());                    
                    tempGen2.setId(j);
                    tempGen2.setConnectionID(temp);
                    this.getBackupChromosome().getGenList().add(tempGen2);
                }
            }
            this.setId(i);
            this.setFitness(processUnit.fitness(tempGraph, this, connectionDB, castList));
    }
    
}
