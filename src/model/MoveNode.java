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
    private int spot;
    private int score;
    private final int NULL_SCORE = 0;

    public MoveNode(){
        spot = -1;
        score = NULL_SCORE;
    }
    
    public MoveNode(int spot){
        this.spot = spot;
        score = NULL_SCORE;
    }
    
    public MoveNode(int spot, int score){
        this.spot = spot;
        this.score = score;
    }
    
    public MoveNode(MoveNode move){
        this.score = move.getScore();
        this.spot = move.getSpot();
    }
    
    public int getSpot() {
        return spot;
    }

    public void setSpot(int spot) {
        this.spot = spot;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public void addScore(int score){
        if(score < 0){
            score = 0;
        }
        this.score += score;
    }
    
    @Override
    public String toString(){
        String moveNode="";
        

        if(spot % 5 == 0){
            moveNode += "\n";
        }
        
        moveNode+= " | " + score + " | ";
            

        return moveNode;
    }
    
}
