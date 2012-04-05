/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gasn;

import data.Cast;
import data.Common;
import data.Connection;
import data.Edge;
import data.Graph;
import data.Individual;
import data.Node;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Dragoon
 */
public class ProcessUnit {
    public Connection shortestPath(int origin,int destination,Graph graph){
        Connection tempConnection=new Connection();
        ArrayList<Graph> graphList=new ArrayList<Graph>();
        Graph tempGraph=new Graph(graph);
        ArrayList<Node> edgeList = graph.getNodeList();
        int[] dist= new int[tempGraph.getNodeList().size()];
        int[] pred= new int[tempGraph.getNodeList().size()];
        int edgeID=-1,
	    choice=origin,
	    alt=0,
	    mincost=Common.MAXCOST,
	    usable=tempGraph.getNodeList().size();
       	if (origin==destination){
            return tempConnection;
	}
	for (int k=0;k<graph.getNodeList().size();k++){
		dist[k]=Common.MAXCOST;
		pred[k]=-1;
	}
	for(int k=0;k<tempGraph.getNodeList().size();k++){
            edgeID=tempGraph.searchEdge(origin,k);
            if ((edgeID==-1)||(!tempGraph.getEdgeList().get(edgeID).isVisible())) dist[k]=Common.MAXCOST;
            else dist[k]=tempGraph.getEdgeList().get(edgeID).getCost();
            if (dist[k]<Common.MAXCOST) pred[k]=origin;
            else pred[k]=-1;
	}
       	dist[origin]=0;
	tempGraph.getNodeList().get(origin).setUsable(false);
	usable--;
	while (usable>0){
            mincost=Common.MAXCOST;
            for (int k=0;k<tempGraph.getNodeList().size();k++)	{
                if ((dist[k]<mincost)&&(tempGraph.getNodeList().get(k).isUsable())){
                    mincost=dist[k];
                    choice=k;
                }
            }
            if (dist[choice]>=Common.MAXCOST) {
                tempConnection.setExist(false);
                //for(int i=0;i<graph.getNodeList().size();i++){
                //    graph.getNodeList().get(i).setUsable(true);
                //}
                return tempConnection;
            }
            if (choice==origin){
                tempConnection.setExist(false);
                //for(int i=0;i<graph.getNodeList().size();i++){
                //    graph.getNodeList().get(i).setUsable(true);
                //}
                return tempConnection;
            }
            tempGraph.getNodeList().get(choice).setUsable(false);
            usable--;
            for (int k=0;k<tempGraph.getNodeList().size();k++){
                edgeID=tempGraph.searchEdge(choice,k);
                if ((edgeID!=-1)&&(tempGraph.getNodeList().get(k).isUsable())){
                    alt=dist[choice]+tempGraph.getEdgeList().get(edgeID).getCost();
                    if (alt<dist[k]){
                        dist[k]=alt;
                        pred[k]=choice;
                    }
                }
            }
            if (choice==destination) break;
        }
	tempConnection.setOrigin(origin);
	tempConnection.setDestination(destination);
	if (dist[destination]<Common.MAXCOST)
	{
            tempConnection.setExist(true);
            int t=destination;
            int[] next=new int[tempGraph.getNodeList().size()];
            while(t!=origin){
		if (pred[t]!=-1){
                    next[pred[t]]=t;
                    t=pred[t];
		} else {
                    next[pred[t]]=t;
                    t=pred[t];
                    System.out.println("ERR: pred"+t+"=NULL\n");
                }
            }
            t=origin;
            tempConnection.getWorkingPath().add(t);
            while(t!=destination){
		if (next[t]!=-1){
                    t=next[t];
                    tempConnection.getWorkingPath().add(t);
		} else 
                    System.out.println("ERR: next"+t+"=NULL\n");
            }
            
	}
        //for(int i=0;i<graph.getNodeList().size();i++){
        //    graph.getNodeList().get(i).setUsable(true);
        //}
        return tempConnection;
    }
    public ArrayList<Connection> findOtherWays(Graph graph,ArrayList<Connection> currentConnectionList,Cast cast, int status){
        Random randomGenerator=new Random();
        ArrayList<Connection> tempConnectionList=new ArrayList<Connection>(currentConnectionList);
        Graph tempGraph=new Graph(graph);
        Connection tempConnection=new Connection();
        int retry=0;
        if (tempConnectionList.isEmpty()) System.out.println("\nERROR: not init connection list");
        else {
            if (currentConnectionList.size()>Common.MAX_PATH_NUMBER){
                return currentConnectionList;
            }
            ///////////////////////////////
            //Change graph and find a way//
            while(retry<Common.RETRY){
                tempConnectionList=currentConnectionList;
		tempGraph=new Graph(graph);
		retry++;
		/////////////////////////////////////////////////////////////
		//chose randomly an edge in each connection, then disable it//
		for (int i=0;i<tempConnectionList.size();i++){
                    int random=randomGenerator.nextInt(tempConnectionList.get(i).getWorkingPath().size()-1);
                    int nextmove=tempConnectionList.get(i).getWorkingPath().get(random);
                    int edgeID=tempGraph.searchEdge(nextmove,tempConnectionList.get(i).getWorkingPath().get(random+1));
                    tempGraph.getEdgeList().get(edgeID).setVisible(false);
                    tempGraph.setNoDisableEdge(tempGraph.getNoDisableEdge()+1);
                    tempGraph.getEdgeList().get(edgeID).setCost(Common.MAXCOST);
                    edgeID=tempGraph.searchEdge(tempConnectionList.get(i).getWorkingPath().get(random+1),nextmove);
                    tempGraph.getEdgeList().get(edgeID).setVisible(false);
                    tempGraph.setNoDisableEdge(tempGraph.getNoDisableEdge()+1);
                    tempGraph.getEdgeList().get(edgeID).setCost(Common.MAXCOST);
                }
                ///////////////////////////////////////
		//After disable some edge, find a way//
		if ((cast.getReplicaDestination()!=-1)&&(status==1)){
                    tempConnection=shortestPath(tempConnectionList.get(0).getOrigin(),cast.getReplicaDestination(),tempGraph);
		} else tempConnection=shortestPath(tempConnectionList.get(0).getOrigin(),tempConnectionList.get(0).getDestination(),tempGraph);
		if (tempConnection.isExist())	{
                    tempConnection.setId(tempConnectionList.size());
                    tempConnectionList.add(tempConnection);
                    retry=Common.RETRY+1;
                    tempConnectionList=findOtherWays(graph,tempConnectionList,cast,0);
		}
            }
        }
        return tempConnectionList;
    }
    public ArrayList<Connection> findBackupPath(Graph graph, ArrayList<Connection> connectionList,Cast cast){
        ArrayList<Connection> tempConnectionList=new ArrayList<Connection>(connectionList);
	Graph tempGraph=new Graph(graph);
	Connection tempConnection=new Connection();
	int nextmove;
        if (tempConnectionList.isEmpty()){
            System.out.println("ERR not init yet");
            return tempConnectionList;
	}
       	//////////////////////////////////////
	//Find back up for each connection//
	for (int i=0;i<tempConnectionList.size();i++)
	{
            //nextmove=tempConnectionList.get(i).getOrigin();
            tempGraph=new Graph(graph);
            ArrayList<Connection> tempBackupList=new ArrayList<Connection>();
            /////////////////////////////////////////
            //Totally delete the current connection//
            for (int j=0;j<tempConnectionList.get(i).getWorkingPath().size()-1;j++) {
            	int edgeID=tempGraph.searchEdge(tempConnectionList.get(i).getWorkingPath().get(j),tempConnectionList.get(i).getWorkingPath().get(j+1));
            	tempGraph.getEdgeList().get(edgeID).setVisible(false);
            	tempGraph.setNoDisableEdge(tempGraph.getNoDisableEdge()+1);
            	tempGraph.getEdgeList().get(edgeID).setCost(Common.MAXCOST);
        	edgeID=tempGraph.searchEdge(tempConnectionList.get(i).getWorkingPath().get(j+1),tempConnectionList.get(i).getWorkingPath().get(j));
		tempGraph.getEdgeList().get(edgeID).setVisible(false);
            	tempGraph.setNoDisableEdge(tempGraph.getNoDisableEdge()+1);
            	tempGraph.getEdgeList().get(edgeID).setCost(Common.MAXCOST);
		//nextmove=tempConnectionList.get(i).getWorkingPath().get(nextmove);
            }
            //Init tempBackupList
            if (cast.getReplicaDestination()==-1)
            {
            	tempConnection=shortestPath(tempConnectionList.get(i).getOrigin(),tempConnectionList.get(i).getDestination(),tempGraph);
            }
            else tempConnection=shortestPath(tempConnectionList.get(i).getOrigin(),cast.getReplicaDestination(),tempGraph);		
            if (tempConnection.isExist())
            {
            	tempConnection.setId(tempBackupList.size());
            	tempBackupList.add(tempConnection);
            }		
            //Find BackupList
            tempBackupList=findOtherWays(tempGraph,tempBackupList,cast,1);
            //Add backup list into connection list
            for(int j=0;j<tempBackupList.size();j++)
            {
            	//nextmove=tempBackupList.get(j).getOrigin();
                tempConnectionList.get(i).getBackupPath().add(new ArrayList<Integer>());
                tempConnectionList.get(i).getBackupPath().set(j, tempBackupList.get(j).getWorkingPath());
            	//for (int k=1;k<tempBackupList.get(j).getWorkingPath().size();k++)
            	//{            		
                //    tempConnectionList.get(i).getBackupPath().get(j).add(tempBackupList.get(j).getWorkingPath().get(k));
                //    //nextmove=tempBackupList.working[j].path[nextmove];
            	//}
                //tempConnectionList.working[i].noBackupStep[j]=tempBackupList.working[j].noStep;
            }
            //tempConnectionList.working[i].noBackupPath=tempBackupList.noConnection;
            if (tempConnectionList.get(i).getBackupPath().isEmpty())
            {
                tempConnectionList.remove(i);
                System.out.println("Connection "+i+" deleted cause have no Backup");
		i--;
            }
            else System.out.println("Connection "+i+" found "+tempConnectionList.get(i).getBackupPath().size()+" ways");
	}
        return tempConnectionList;
    }
    public int fitness(Graph graph, Individual individual,ArrayList<ArrayList<Connection>> connectionDB, ArrayList<Cast> castList){
        int fit=0;
	Individual tempInvidual=individual;
	Graph subTempGraph=new Graph(graph);
	Connection tempConnection1;
	int edgeID,
            totalCost=0;
	for (int k=0;k<tempInvidual.getWorkingChromosome().getGenList().size();k++)
	{	
            //if there is no need to have any edge to make the connection, just pass the current gen
            //else begin
            if (tempInvidual.getWorkingChromosome().getGenList().get(k).getConnectionID()!=-1)
            {
            	tempConnection1=connectionDB.get(k).get(tempInvidual.getConnectionID(1, k));
		//process each step of working connection 
		int origin=tempConnection1.getOrigin();
		//while (origin!=tempConnection1.getDestination())
                for(int t=0;t<tempConnection1.getWorkingPath().size()-1;t++)
		{
                    //search for edge from origin to t, then add more bandwidth to edge
                    origin=tempConnection1.getWorkingPath().get(t);
                    edgeID=subTempGraph.searchEdge(origin,tempConnection1.getWorkingPath().get(t+1));
                    subTempGraph.getEdgeList().get(edgeID).setBandwidth(subTempGraph.getEdgeList().get(edgeID).getBandwidth()+castList.get(k).getBandwidth());
                    edgeID=subTempGraph.searchEdge(tempConnection1.getWorkingPath().get(t+1),origin);
                    subTempGraph.getEdgeList().get(edgeID).setBandwidth(subTempGraph.getEdgeList().get(edgeID).getBandwidth()+castList.get(k).getBandwidth());
                    
		}
                //process each step of back up connection 
                //origin=tempConnection1.getOrigin();
		int destination;
		if (castList.get(k).getReplicaDestination()!=-1) destination=castList.get(k).getReplicaDestination();
		else destination=castList.get(k).getDestination();
		//while (origin!=destination)
                //System.out.println("cast "+k+" | "+tempInvidual.getConnectionID(2, k));
                try{
                    for (int t=0;t<tempConnection1.getBackupPath().get(tempInvidual.getConnectionID(2, k)).size()-1;t++){	
                        //search for edge from origin to t, then add more bandwidth to edge
                        //System.out.println(t);
                        origin=tempConnection1.getBackupPath().get(tempInvidual.getConnectionID(2, k)).get(t);
                        edgeID=subTempGraph.searchEdge(origin,tempConnection1.getBackupPath().get(tempInvidual.getConnectionID(2, k)).get(t+1));
                        subTempGraph.getEdgeList().get(edgeID).setBandwidth(subTempGraph.getEdgeList().get(edgeID).getBandwidth()+castList.get(k).getBandwidth());
                        edgeID=subTempGraph.searchEdge(tempConnection1.getBackupPath().get(tempInvidual.getConnectionID(2, k)).get(t+1),origin);
                        subTempGraph.getEdgeList().get(edgeID).setBandwidth(subTempGraph.getEdgeList().get(edgeID).getBandwidth()+castList.get(k).getBandwidth());
                    }
                } catch (Exception E){
                    E.printStackTrace();
                }
                
            }
	}
	//process through the graph, calculate cost for each edge base on bandwidth requirement
	for (int k=0;k<subTempGraph.getEdgeList().size();k++)
	{
            //bandwidth>Max bandwidth
            subTempGraph.getEdgeList().get(k).setCost(0);
            while (subTempGraph.getEdgeList().get(k).getBandwidth()>subTempGraph.getEdgeList().get(k).getBandwidthList().get(subTempGraph.getEdgeList().get(k).getBandwidthList().size()-1)){
                subTempGraph.getEdgeList().get(k).setCost(subTempGraph.getEdgeList().get(k).getCost()+subTempGraph.getEdgeList().get(k).getCostList().get(subTempGraph.getEdgeList().get(k).getBandwidthList().size()-1));
                subTempGraph.getEdgeList().get(k).setBandwidth(subTempGraph.getEdgeList().get(k).getBandwidth()-subTempGraph.getEdgeList().get(k).getBandwidthList().get(subTempGraph.getEdgeList().get(k).getBandwidthList().size()-1));
            }
            //bandwidth = 0
            if (subTempGraph.getEdgeList().get(k).getBandwidth()==0)
                subTempGraph.getEdgeList().get(k).setCost(subTempGraph.getEdgeList().get(k).getCost());
            //cost for each level of bandwidth
            else for (int t=0;t<subTempGraph.getEdgeList().get(k).getBandwidthList().size();t++){
            //
                if (subTempGraph.getEdgeList().get(k).getBandwidth()<=subTempGraph.getEdgeList().get(k).getBandwidthList().get(t)) {
                    subTempGraph.getEdgeList().get(k).setCost(subTempGraph.getEdgeList().get(k).getCost()+subTempGraph.getEdgeList().get(k).getCostList().get(t));
                    break;
                }
            }
            //subTempGraph.getEdgeList().get(k).setCost(subTempGraph.getEdgeList().get(k).getBandwidth());
            totalCost=totalCost+subTempGraph.getEdgeList().get(k).getCost();
	}
        return totalCost;
    }
    public boolean fineTune(int postion,Individual individual,Graph graph,ArrayList<ArrayList<Connection>> connectionDB, ArrayList<Cast> castList){
        Graph tempGraph=new Graph(graph);
        //add bandwidth required
        for(int i=0;i<individual.getBackupChromosome().getGenList().size();i++){
            
            for(int j=0;j<connectionDB.get(i).get(individual.getConnectionID(1, i)).getWorkingPath().size()-1;j++){
               int edgeID=tempGraph.searchEdge(connectionDB.get(i).get(individual.getConnectionID(1, i)).getWorkingPath().get(j), connectionDB.get(i).get(individual.getConnectionID(1, i)).getWorkingPath().get(j+1));
               tempGraph.getEdgeList().get(edgeID).setBandwidth(castList.get(i).getBandwidth()+tempGraph.getEdgeList().get(edgeID).getBandwidth());
               edgeID=tempGraph.searchEdge(connectionDB.get(i).get(individual.getConnectionID(1, i)).getWorkingPath().get(j+1),connectionDB.get(i).get(individual.getConnectionID(1, i)).getWorkingPath().get(j));
               tempGraph.getEdgeList().get(edgeID).setBandwidth(castList.get(i).getBandwidth()+tempGraph.getEdgeList().get(edgeID).getBandwidth());
            }
            if(i!=postion){
                for(int j=0;j<connectionDB.get(i).get(individual.getConnectionID(1, i)).getBackupPath().get(individual.getConnectionID(2, i)).size()-1;j++){
                    int edgeID=tempGraph.searchEdge(connectionDB.get(i).get(individual.getConnectionID(1, i)).getBackupPath().get(individual.getConnectionID(2, i)).get(j),connectionDB.get(i).get(individual.getConnectionID(1, i)).getBackupPath().get(individual.getConnectionID(2, i)).get(j+1));
                    tempGraph.getEdgeList().get(edgeID).setBandwidth(castList.get(i).getBandwidth()+tempGraph.getEdgeList().get(edgeID).getBandwidth());
                    edgeID=tempGraph.searchEdge(connectionDB.get(i).get(individual.getConnectionID(1, i)).getBackupPath().get(individual.getConnectionID(2, i)).get(j+1),connectionDB.get(i).get(individual.getConnectionID(1, i)).getBackupPath().get(individual.getConnectionID(2, i)).get(j));
                    tempGraph.getEdgeList().get(edgeID).setBandwidth(castList.get(i).getBandwidth()+tempGraph.getEdgeList().get(edgeID).getBandwidth());
                }    
            }
        }
        for(int i=0;i<tempGraph.getEdgeList().size();i++){
            
            while(tempGraph.getEdgeList().get(i).getBandwidth()>tempGraph.getEdgeList().get(i).getBandwidthList().get(tempGraph.getEdgeList().get(i).getBandwidthList().size()-1)){
                tempGraph.getEdgeList().get(i).setBandwidth(tempGraph.getEdgeList().get(i).getBandwidth()-tempGraph.getEdgeList().get(i).getBandwidthList().get(tempGraph.getEdgeList().get(i).getBandwidthList().size()-1));
            }
            tempGraph.getEdgeList().get(i).setCost(1);
            if (tempGraph.getEdgeList().get(i).getBandwidth()==0) tempGraph.getEdgeList().get(i).setCost(tempGraph.getEdgeList().get(i).getCostList().get(0)*tempGraph.getEdgeList().size());
            else for(int j=0;j<tempGraph.getEdgeList().get(i).getBandwidthList().size()-1;j++){
                if((castList.get(postion).getBandwidth()+tempGraph.getEdgeList().get(i).getBandwidth()>tempGraph.getEdgeList().get(i).getBandwidthList().get(j))
                        &&(tempGraph.getEdgeList().get(i).getBandwidth()<=tempGraph.getEdgeList().get(i).getBandwidthList().get(j) )){
                    tempGraph.getEdgeList().get(i).setCost((tempGraph.getEdgeList().get(i).getCostList().get(j+1)-tempGraph.getEdgeList().get(i).getCostList().get(j))*tempGraph.getEdgeList().size());
                }
            }
        }
        Connection tempConnect=new Connection();
        Individual tempIndividual=new Individual(individual);
        tempConnect=shortestPath(castList.get(postion).getOrigin(), castList.get(postion).getDestination(), tempGraph);
        connectionDB.get(postion).get(individual.getConnectionID(1, postion)).getBackupPath().add(tempConnect.getWorkingPath());
        tempIndividual.setConnectionID(2, postion, connectionDB.get(postion).get(individual.getConnectionID(1, postion)).getBackupPath().size()-1);
        tempIndividual.setFitness(fitness(graph, individual, connectionDB, castList));
        if (tempIndividual.getFitness()<individual.getFitness()) {
            individual=new Individual(tempIndividual);
            return true;
        }
        else return false;
        
    }
}
