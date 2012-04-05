/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author Dragoon
 */
public class Cast {
    private int bandwidth;
    private int destination;
    private int id;
    private int origin;
    private int replicaDestination;
    private boolean type;
    static final public boolean UNICAST=false;
    static final public boolean ANYCAST=true;
    static private int noAnycast;
    static private int noUnicast;
    static private int noCast;

    public int getBandwidth() {
        return bandwidth;
    }

    public int getDestination() {
        return destination;
    }

    public int getId() {
        return id;
    }

    public static int getNoAnycast() {
        return noAnycast;
    }

    public static int getNoCast() {
        return noCast;
    }

    public static int getNoUnicast() {
        return noUnicast;
    }

    public int getOrigin() {
        return origin;
    }

    public int getReplicaDestination() {
        return replicaDestination;
    }

    public boolean isType() {
        return type;
    }

    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void setNoAnycast(int noAnycast) {
        Cast.noAnycast = noAnycast;
    }

    public static void setNoCast(int noCast) {
        Cast.noCast = noCast;
    }

    public static void setNoUnicast(int noUnicast) {
        Cast.noUnicast = noUnicast;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public void setReplicaDestination(int replicaDestination) {
        this.replicaDestination = replicaDestination;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public Cast() {
        this.bandwidth=0;
        this.destination=-1;
        this.id=0;
        this.origin=-1;
        this.replicaDestination=-1;
        this.type=UNICAST;
    }
    
}
