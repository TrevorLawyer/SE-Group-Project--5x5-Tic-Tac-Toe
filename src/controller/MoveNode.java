package controller;

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
