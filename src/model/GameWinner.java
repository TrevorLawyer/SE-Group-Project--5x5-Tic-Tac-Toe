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
