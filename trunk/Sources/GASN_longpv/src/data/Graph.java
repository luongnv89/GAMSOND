/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;

/**
 *
 * @author Dragoon
 */
public class Graph {
    private ArrayList<Edge> edgeList;
    private ArrayList<Node> nodeList;
    private int noDisableEdge;

    public Graph(ArrayList<Edge> edgeList, ArrayList<Node> nodeList) {
        this.edgeList = edgeList;
        this.nodeList = nodeList;
        this.noDisableEdge=0;
    }

    public Graph(Graph graph) {
        this.edgeList=new ArrayList<Edge>();
        this.nodeList=new ArrayList<Node>();
        for(int i=0;i<graph.getEdgeList().size();i++){
            Edge tempEdge=new Edge();
            tempEdge.setBandwidth(graph.getEdgeList().get(i).getBandwidth());
            tempEdge.setCost(graph.getEdgeList().get(i).getCost());
            tempEdge.setDestination(graph.getEdgeList().get(i).getDestination());
            tempEdge.setId(i);
            tempEdge.setOrigin(graph.getEdgeList().get(i).getOrigin());
            for(int j=0;j<graph.getEdgeList().get(i).getBandwidthList().size();j++){
                tempEdge.getBandwidthList().add(graph.getEdgeList().get(i).getBandwidthList().get(j));
                tempEdge.getCostList().add(graph.getEdgeList().get(i).getCostList().get(j));                
            }
            tempEdge.setVisible(graph.getEdgeList().get(i).isVisible());
            this.edgeList.add(tempEdge);            
        }
        for(int i=0;i<graph.getNodeList().size();i++){
            Node tempNode=new Node();
            tempNode.setId(i);
            tempNode.setUsable(graph.getNodeList().get(i).isUsable());
            this.nodeList.add(tempNode);
        }
        this.noDisableEdge=graph.noDisableEdge;
    }

    public Graph() {
        this.edgeList=new ArrayList<Edge>();
        this.nodeList=new ArrayList<Node>();
        this.noDisableEdge=0;
    }
     
    public int searchEdge(int origin,int destination){
        for (int i=0;i<this.edgeList.size();i++){
            if ((this.edgeList.get(i).getOrigin()==origin)&&(this.edgeList.get(i).getDestination()==destination))
                return i;
        }
        return -1;
    }

    public ArrayList<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(ArrayList<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    public int getNoDisableEdge() {
        return noDisableEdge;
    }

    public void setNoDisableEdge(int noDisableEdge) {
        this.noDisableEdge = noDisableEdge;
    }

    public ArrayList<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(ArrayList<Node> nodeList) {
        this.nodeList = nodeList;
    }
    
}
