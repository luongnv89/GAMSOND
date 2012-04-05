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
public class Chromosome {
    private int finess;
    private ArrayList<Gen> genList;

    public int getFiness() {
        return finess;
    }

    public void setFiness(int finess) {
        this.finess = finess;
    }

    public ArrayList<Gen> getGenList() {
        return genList;
    }

    public void setGenList(ArrayList<Gen> genList) {
        this.genList = genList;
    }

    public Chromosome() {
        this.finess=Common.MAXCOST;
        this.genList=new ArrayList<Gen>();
    }
    
}
