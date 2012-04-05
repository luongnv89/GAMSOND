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
public class Connection {
    private int destination;
    private int origin;
    private int replicaDestination;
    private boolean exist;
    private int id;
    private ArrayList<ArrayList<Integer>> backupPath;
    private ArrayList<Integer> workingPath;

    public ArrayList<ArrayList<Integer>> getBackupPath() {
        return backupPath;
    }

    public void setBackupPath(ArrayList<ArrayList<Integer>> backupPath) {
        this.backupPath = backupPath;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
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

    public int getReplicaDestination() {
        return replicaDestination;
    }

    public void setReplicaDestination(int replicaDestination) {
        this.replicaDestination = replicaDestination;
    }

    public ArrayList<Integer> getWorkingPath() {
        return workingPath;
    }

    public void setWorkingPath(ArrayList<Integer> workingPath) {
        this.workingPath = workingPath;
    }

    public Connection() {
        this.backupPath=new ArrayList<ArrayList<Integer>>();
        this.workingPath=new ArrayList<Integer>();
        this.destination=-1;
        this.origin=-1;
        this.exist=false;
        this.id=0;
        this.replicaDestination=-1;
    }
    
}
