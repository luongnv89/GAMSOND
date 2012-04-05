/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author Dragoon
 */
public class Node {
    private int id;
    private boolean usable;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isUsable() {
        return usable;
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }

    public Node(int id) {
        this.id = id;
        this.usable=true;
    }
    public Node(){
        this.id=0;
        this.usable=true;
    }

    
}
