package model;

import controller.GameState;

public class HumanPlayer extends Player{

    public HumanPlayer(boolean isXPlayer) {
        super(isXPlayer);
    }
    
    
    @Override
    public GameState takeTurn(GameState gameState, int selectedSpot) {
        gameState.opponentMove(selectedSpot);
        return gameState;
    }

   
}
