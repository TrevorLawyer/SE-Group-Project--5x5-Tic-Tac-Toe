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
public class MoveNode{
    private byte spot;
    private int score;

    public MoveNode(){
        spot = 0;
        score = 0;
    }
    
    public MoveNode(byte spot, int score){
        this.spot = spot;
        this.score = score;
    }
    
    public byte getSpot() {
        return spot;
    }

    public void setSpot(byte spot) {
        this.spot = spot;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    
}
