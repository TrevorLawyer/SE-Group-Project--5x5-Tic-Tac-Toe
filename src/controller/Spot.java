/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author TJaeckel
 */
public class Spot {
    private boolean taken;
    private boolean isMe;
    private int index;
    private boolean isX;
    
    public Spot(int index){
        taken = false;
        isMe = false;
        this.index = index;
    }
    
    public Spot(Spot spot){
        this.taken = spot.isTaken();
        this.isMe = spot.isMe();
        this.index = spot.getIndex();
        this.isX = spot.isX;
    }
    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setIsMe(boolean isMe) {
        this.isMe = isMe;
    }

    public int getIndex() {
        return index;
    }

    public boolean isX() {
        return isX;
    }

    public void setIsX(boolean isX) {
        this.isX = isX;
    }
    
    
    

}
