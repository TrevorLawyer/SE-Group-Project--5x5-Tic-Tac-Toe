/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author TJaeckel
 */
public class Spot {
    private boolean taken;
    private boolean xPlayer;
    private byte index;
    
    public Spot(byte index){
        taken = false;
        xPlayer = false;
        this.index = index;
    }
    
    public Spot(Spot spot){
        this.taken = spot.isTaken();
        this.xPlayer = spot.isxPlayer();
        this.index = spot.getIndex();
    }
    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public boolean isxPlayer() {
        return xPlayer;
    }

    public void setxPlayer(boolean xPlayer) {
        this.xPlayer = xPlayer;
    }

    public byte getIndex() {
        return index;
    }
    
    

}
