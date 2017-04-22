package model;

import controller.GameState;

public abstract class Player {
    private boolean isXPlayer;
    
    public Player(boolean isXPlayer){
        this.isXPlayer = isXPlayer;
    }
    
    abstract public GameState takeTurn(GameState gameState, int selectedSpot);
    public void gameResults(){
        
    }

    public boolean isIsXPlayer() {
        return isXPlayer;
    }

    public void setIsXPlayer(boolean isXPlayer) {
        this.isXPlayer = isXPlayer;
    }
}
