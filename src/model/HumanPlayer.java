package model;

import controller.GameState;

public class HumanPlayer extends Player{

    private int moveSelected;

    public HumanPlayer(boolean isXPlayer) {
        super(isXPlayer);
    }
    
    
    @Override
    public GameState takeTurn(GameState gameState) {
        gameState.opponentMove(moveSelected);
        return gameState;
    }
    
    public void setMoveSelected(int moveSpot){
        moveSelected = moveSpot;
    }
   
}
