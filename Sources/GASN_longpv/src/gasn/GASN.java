/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gasn;

import java.util.ArrayList;
import data.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;
import java.util.StringTokenizer;
/**
 *
 * @author Dragoon
 */
public class GASN {
    private static Graph graph=new Graph(),
            tempGraph=new Graph();
    private static ArrayList<ArrayList<Connection>> connectionDB=new ArrayList<ArrayList<Connection>>();
    private static ArrayList<Individual> population=new ArrayList<Individual>(),
            nextgen=new ArrayList<Individual>();
    private static ArrayList<Cast> castList=new ArrayList<Cast>();
    private static ProcessUnit processUnit=new ProcessUnit();
    private static Random randomGenerator=new Random();
    /**
     * @param args the command line arguments
     */
    private static void readGraph(){
        try{
            String tempStr=null;
            BufferedReader br=new BufferedReader(new FileReader(Common.TEST_DATA+".txt"));
            int tempInt, noNode, noEdge;
            StringTokenizer st=null;
            tempStr=br.readLine();
            st=new StringTokenizer(tempStr," ");
            noEdge=Integer.parseInt(st.nextToken());
            //System.out.println(tempInt);
            noNode=Integer.parseInt(st.nextToken());
            //System.out.println(tempInt);
            for(int i=0;i<noEdge;i++){
                System.out.println(i);
                tempStr=br.readLine();
                while (tempStr.equals("")){
                    tempStr=br.readLine();    
                }
                st=new StringTokenizer(tempStr," ");
                Edge tempEdge=new Edge();
                tempEdge.setOrigin(Integer.parseInt(st.nextToken())-1);
                tempEdge.setDestination(Integer.parseInt(st.nextToken())-1);
                tempStr=br.readLine();
                tempInt=Integer.parseInt(tempStr);
                tempStr=br.readLine();
                st=new StringTokenizer(tempStr," ");
                for(int j=0;j<tempInt;j++){
                    tempEdge.getBandwidthList().add(Integer.parseInt(st.nextToken()));
                }
                tempStr=br.readLine();
                if(tempInt==4) st=new StringTokenizer(tempStr,"\t");
                else st=new StringTokenizer(tempStr," ");
                for(int j=0;j<tempInt;j++){
                    tempEdge.getCostList().add(Integer.parseInt(st.nextToken()));
                }
                tempEdge.setCost(1);
                tempEdge.setId(i);
                tempEdge.setVisible(true);
                tempEdge.setBandwidth(0);
                //System.out.println(i+" "+tempEdge.toString());
                graph.getEdgeList().add(tempEdge);
            }
            for (int i=0;i<noNode;i++){
                Node tempNode=new Node();
                tempNode.setId(i);
                graph.getNodeList().add(tempNode);
            }
        } catch (Exception E){            
            System.out.println(E.toString());
        }
        try{
            BufferedReader br=new BufferedReader(new FileReader(Common.TEST_DATA+"//"+Common.TEST_DATA_NO+"//unicast.txt"));
            int tempInt;
            String tempStr;
            StringTokenizer st=null;
            tempInt=Integer.parseInt(br.readLine());
            br.readLine();
            for(int i=0;i<tempInt;i++){
                tempStr=br.readLine();
                st=new StringTokenizer(tempStr, " ");
                Cast tempCast=new Cast();
                tempCast.setId(i);
                tempCast.setOrigin(Integer.parseInt(st.nextToken())-1);
                tempCast.setDestination(Integer.parseInt(st.nextToken())-1);
                tempCast.setBandwidth(Integer.parseInt(st.nextToken()));
                
                tempCast.setType(Cast.UNICAST);
                castList.add(tempCast);
                //System.out.println(i+" "+tempCast.toString());
            }           
        }catch (Exception E){
            System.out.println(E.toString());
        }
        try {
            BufferedReader br=new BufferedReader(new FileReader(Common.TEST_DATA+"//"+Common.TEST_DATA_NO+"//anycast.txt"));
            int tempInt;
            String tempStr;
            StringTokenizer st=null;
            tempInt=Integer.parseInt(br.readLine());
            br.readLine();
            for(int i=0;i<tempInt;i++){
                tempStr=br.readLine();
                st=new StringTokenizer(tempStr, " ");
                Cast tempCast=new Cast();
                tempCast.setId(i);
                tempCast.setOrigin(Integer.parseInt(st.nextToken())-1);
                tempCast.setDestination(Integer.parseInt(st.nextToken())-1);
                tempCast.setBandwidth(Integer.parseInt(st.nextToken()));
                tempCast.setBandwidth(3);
                tempCast.setType(Cast.ANYCAST);
                castList.add(tempCast);
                //System.out.println(i+" "+tempCast.toString());
            }
        } catch(Exception E){
            System.out.println(E.toString());
        }
        try {
            BufferedReader br=new BufferedReader(new FileReader(Common.TEST_DATA+"//"+Common.TEST_DATA_NO+"//replica.txt"));
            int tempInt;
            String tempStr;
            StringTokenizer st=null;
            int[] replica=new int[graph.getNodeList().size()];
            for(int i=0;i<graph.getNodeList().size();i++){
                replica[i]=-1;
            }
            tempInt=Integer.parseInt(br.readLine());
            for(int i=0;i<tempInt;i++){
                int temp1,temp2;
                tempStr=br.readLine();
                st=new StringTokenizer(tempStr," ");
                temp1=Integer.parseInt(st.nextToken())-1;
                temp2=Integer.parseInt(st.nextToken())-1;
                replica[temp1]=temp2;
                replica[temp2]=temp1;
            }         
            for(int i =0;i<castList.size();i++){
                if (castList.get(i).isType()){
                    castList.get(i).setReplicaDestination(replica[castList.get(i).getDestination()]);
                    if (castList.get(i).getOrigin()==castList.get(i).getReplicaDestination()){
                        castList.get(i).setReplicaDestination(-1);
                    }
                }
            }
        } catch(Exception E){
            E.printStackTrace();
        }
    }
   
    private static void initConnectionList(){
        for (int noCast=0;noCast<castList.size();noCast++){
            connectionDB.add(new ArrayList<Connection>());
        }
	//////////////////////////////////////////////////////////
	//With every cast, find a way from origin to destination//
	//	then remove some edge to find a new one				//
	//	  Last, with each way found, find backups for it		//
	//////////////////////////////////////////////////////////
	for (int noCast=0;noCast<castList.size();noCast++)
	{
            Connection tempConnection=new Connection();
            tempGraph=new Graph(graph);
            if ((castList.get(noCast).getOrigin()<graph.getNodeList().size())&&(castList.get(noCast).getOrigin()>=0)&&(castList.get(noCast).getDestination()<graph.getNodeList().size())&&(castList.get(noCast).getDestination()>=0)){
                tempConnection=processUnit.shortestPath(castList.get(noCast).getOrigin(),castList.get(noCast).getDestination(),tempGraph);
		if (tempConnection.isExist()){
                    tempConnection.setId(connectionDB.get(noCast).size());
                    connectionDB.get(noCast).add(tempConnection);
                    connectionDB.set(noCast, processUnit.findOtherWays(tempGraph, connectionDB.get(noCast), castList.get(noCast), 0));
                    connectionDB.set(noCast, processUnit.findBackupPath(tempGraph, connectionDB.get(noCast), castList.get(noCast)));
		} else {
                    System.out.println("ERR: Cast no "+noCast+" No Path Found");
                    //break;
		}
            } else {
		System.out.println("Invalid cast "+noCast);
            }
            System.out.println("Cast "+noCast+" is done");
	}
    }
    
    private static void initPopulation(){
        //create perfect creation
	Individual tempInvidual=new Individual();
        for (int j=0;j<castList.size();j++){
            //if there is no choice
            if (connectionDB.get(j).isEmpty()){
                Gen gen=new Gen();
                gen.setId(j);
                gen.setConnectionID(-1);
                tempInvidual.getWorkingChromosome().getGenList().add(gen);
                tempInvidual.getBackupChromosome().getGenList().add(gen);
            }else {
                //if there is a choice
		Gen gen=new Gen();
                gen.setId(j);
                gen.setConnectionID(0);
                tempInvidual.getWorkingChromosome().getGenList().add(gen);
                tempInvidual.getBackupChromosome().getGenList().add(gen);
            }
        }
        tempInvidual.setId(0);
        tempInvidual.setFitness(processUnit.fitness(tempGraph, tempInvidual, connectionDB, castList));
        population.add(tempInvidual);
        //for (int i=1;i<=Common.POPULATION;i++){
        //    population.add(new Individual(i));
        //    nextgen.add(new Individual(i));
        //    population.get(i).setFitness(Common.MAXCOST);
        //    nextgen.get(i).setFitness(Common.MAXCOST);
       	//}
	//create POPULATION individuals in the population
	for (int i=1;i<Common.POPULATION;i++){
//            tempInvidual=new Individual();
//            for (int j=0;j<castList.size();j++){
//                int temp=0;
//                Gen tempGen1=new Gen(),
//                        tempGen2=new Gen();
//		//if there is no choice
//		if (connectionDB.get(j).isEmpty()){
//                    tempGen1.setId(j);
//                    tempGen2.setId(j);
//                    tempGen1.setConnectionID(-1);
//                    tempGen2.setConnectionID(-1);
//                    tempInvidual.getWorkingChromosome().getGenList().add(tempGen1);
//                    tempInvidual.getBackupChromosome().getGenList().add(tempGen2);
//                } else {
//                    //if there is a choice
//                    temp = randomGenerator.nextInt(connectionDB.get(j).size());
//                    tempGen1.setId(j);
//                    tempGen1.setConnectionID(temp);
//                    tempInvidual.getWorkingChromosome().getGenList().add(tempGen1);
//                    temp = randomGenerator.nextInt(connectionDB.get(j).get(temp).getBackupPath().size());                    
//                    tempGen2.setId(j);
//                    tempGen2.setConnectionID(temp);
//                    tempInvidual.getBackupChromosome().getGenList().add(tempGen2);
//                }
//            }
//            tempInvidual.setId(i);
//            tempInvidual.setFitness(processUnit.fitness(tempGraph, tempInvidual, connectionDB, castList));
            population.add(new Individual(i, processUnit, connectionDB, castList, graph));
	}
	
    }
   
    private static void selection(){       
	for (int i=0;i<Common.POPULATION;i++){
            for (int j=i;j<Common.POPULATION;j++){
                if (population.get(i).getFitness()>population.get(j).getFitness()){
                    Individual temp;
                    temp=population.get(i);
                    population.set(i,population.get(j));
                    population.set(j,temp);
		}
            }
	}
    }
   
    private static void evolution(){
        nextgen=new ArrayList<Individual>();
        for (int i=0;i<Common.POPULATION/4;i++){
            int [] p=new int[4];
            p[1]=randomGenerator.nextInt(population.size());
            p[2]=p[1];p[3]=p[1];p[0]=p[1];
            while(population.get(p[1]).isUsed()){
                p[1]=randomGenerator.nextInt(population.size());
            }
            while ((p[2]==p[1])||(population.get(p[2]).isUsed())){
		p[2]=randomGenerator.nextInt(population.size());
            }
            while ((p[3]==p[1])||(p[3]==p[2])||(population.get(p[3]).isUsed())){
                p[3]=randomGenerator.nextInt(population.size());
            }
            while ((p[0]==p[1])||(p[0]==p[2])||(p[0]==p[3])||(population.get(p[0]).isUsed())){
                p[0]=randomGenerator.nextInt(population.size());
            }
            Individual  child1=new Individual(),
                        child2=new Individual();
            if (population.get(p[2]).getFitness()<population.get(p[1]).getFitness()){
                int temp=p[1];			
                p[1]=p[2];
		p[2]=temp;
            }
            if (population.get(p[0]).getFitness()<population.get(p[3]).getFitness()){
                int temp=p[3];
                p[3]=p[0];
                p[0]=temp;
            }
            child1=new Individual(population.get(p[1]).combineType2(population.get(p[3]),population.get(p[1]),connectionDB));
            child2=new Individual(population.get(p[2]).combineType2(population.get(p[0]),population.get(p[2]),connectionDB));
            child1.setId(population.get(p[2]).getId());
            child1.setFitness(processUnit.fitness(tempGraph, child1, connectionDB, castList));
            child1.setUsed(true);
            nextgen.add(child1);
            child2.setId(population.get(p[0]).getId());
            child2.setUsed(true);
            child2.setFitness(processUnit.fitness(tempGraph, child2, connectionDB, castList));
            nextgen.add(child2);
            population.get(p[1]).setUsed(true);
            population.get(p[3]).setUsed(true);
            nextgen.add(new Individual(population.get(p[1])));
            nextgen.add(new Individual(population.get(p[3])));
            for(int j=0;j<4;j++){
                for(int k=j;k<4;k++){
                    if (p[k]<p[j]){
                        int temp=p[k];
                        p[k]=p[j];
                        p[j]=temp;
                    }
                }
            }
            population.remove(p[0]);
            population.remove(p[1]-1);
            population.remove(p[2]-2);
            population.remove(p[3]-3);
            
	}
        for(int i=0;i<nextgen.size();i++){
            Individual tempIndividual=new Individual(nextgen.get(i));
            population.add(tempIndividual);
        }
	for (int i=0;i<Common.POPULATION;i++)
	{
		population.get(i).setUsed(false);
	}
    }
   
    private static void showResult(){
        System.out.println("Best results are:");
	for(int i=0;i<10;i++){
            System.out.println(i+". "+population.get(i).getFitness());
	}
	//System.out.println("The best result is:");
	//for (int i=0;i<population.individual[0].working.length;i++)
	//{
	//	printf("%d. %d --- %d\n",i,population.individual[0].working.genList[i].connectionID,population.individual[0].backup.genList[i].connectionID);
	//}
    }
   
    private static void printResult(Individual individual,int count){
        try{
            PrintWriter pw=new PrintWriter(new FileWriter("[Test]"+Common.TEST_DATA+"//"+Common.TEST_DATA_NO+"//output"+count+" "+population.get(0).getFitness()+".txt"));
            String tempStr=null;
            
            pw.println(castList.size());
            pw.println();
            for(int i=0;i<castList.size();i++){
                if (castList.get(i).isType()){
                    pw.println((i+1)+" 1");
                } else {
                    pw.println((i+1)+" 0");
                }
                pw.println((castList.get(i).getOrigin()+1)+" "+(castList.get(i).getDestination()+1));
                for(int j=0;j<connectionDB.get(i).get(individual.getConnectionID(1, i)).getWorkingPath().size();j++){
                    pw.print((connectionDB.get(i).get(individual.getConnectionID(1, i)).getWorkingPath().get(j)+1)+" ");                    
                }
                pw.println();
//                System.out.println(i);
                for(int j=0;j<connectionDB.get(i).get(individual.getConnectionID(1, i)).getBackupPath().get(individual.getConnectionID(2, i)).size();j++){
                    pw.print(((connectionDB.get(i).get(individual.getConnectionID(1, i)).getBackupPath().get(individual.getConnectionID(2, i)).get(j))+1)+" ");                    
                }
                pw.println();
                pw.println();
            }
            pw.close();
        } catch (Exception E){
            E.printStackTrace();
        }        
    }
   
    public static void main(String[] args) {
        // TODO code application logic here
        for(int k=1;k<=10;k++)
        {
            Common.TEST_DATA_NO=k;
            graph=new Graph();
            tempGraph=new Graph();
            castList=new ArrayList<Cast>();
            readGraph();
            for(int count=Common.START;count<=Common.END;count++){
                connectionDB=new ArrayList<ArrayList<Connection>>();
                population=new ArrayList<Individual>();
                nextgen=new ArrayList<Individual>();
                initConnectionList();
                initPopulation();
                selection();
    //        for(int i=0;i<castList.size();i++){
    //            if(processUnit.fineTune(i, population.get(0), graph, connectionDB, castList)) break;
    //        }
                System.out.println("Init complete!");
                for(int i=0;i<Common.NO_GENERATION;i++){
                    System.out.println("Generation "+i);
                    evolution();
                    //selection();
                    //System.out.println("Generation "+i+" complete!");
                    //population.get(0).setFitness(processUnit.fitness(graph, population.get(0), connectionDB, castList));
                    //System.out.println(i+"/"+Common.NO_GENERATION+"  Best:"+population.get(0).getFitness());
                }
                showResult();
                printResult(population.get(0),count);
            }
        }
    }
}
