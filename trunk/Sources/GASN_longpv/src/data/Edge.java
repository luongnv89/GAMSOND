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
public class Edge {
    private int bandwidth;
    private ArrayList<Integer> bandwidthList;
    private int cost;
    private ArrayList<Integer> costList;
    private int destination;
    private int origin;
    private int id;
    private boolean visible;

    public int getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }

    public ArrayList<Integer> getBandwidthList() {
        return bandwidthList;
    }

    public void setBandwidthList(ArrayList<Integer> bandwidthList) {
        this.bandwidthList = bandwidthList;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public ArrayList<Integer> getCostList() {
        return costList;
    }

    public void setCostList(ArrayList<Integer> costList) {
        this.costList = costList;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Edge() {
        this.bandwidth=0;
        this.bandwidthList=new ArrayList<Integer>();
        this.cost=0;
        this.costList=new ArrayList<Integer>();
        this.destination=-1;
        this.id=0;
        this.origin=-1;
        this.visible=true;
    }
}
