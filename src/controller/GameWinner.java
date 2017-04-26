package controller;

public class GameWinner {
    private Winner gameWinner;
    
    public GameWinner(){
        gameWinner = Winner.NONE;
    }
    
    public GameWinner(GameWinner winner){
        gameWinner = winner.getGameWinner();
    }
    
    public void setGameWinner(Winner gameWinner){
        this.gameWinner = gameWinner;
    }
    
    public Winner getGameWinner(){
        return gameWinner;
    }
}
