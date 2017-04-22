/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import model.BotPlayer;
import model.Game;
import model.GameMode;
import model.HumanPlayer;
import model.Player;
import model.SocketNetwork;

/**
 *
 * @author micha
 */
public class GameManager{
    private static int gameMode;
    public static Game game;
    public static ArrayList<Player> player;   
    public static NetworkImplementer network;
    
    public static void selectGameMode(int gm){
        gameMode = gm;
    }
    
    public static void connectToNetwork(){
        network= new SocketNetwork();
    }
    
    public static void startGame(){        
        game = new Game(gameMode);
        
        switch (gameMode) {        
            case GameMode.ONE_PLAYER:
                player.add(new HumanPlayer());
                player.add(new BotPlayer());
                break;
        
            case GameMode.TWO_PLAYER:
                player.add(new HumanPlayer());
                player.add(new HumanPlayer());
                break;        
            case GameMode.NETWORK:
                player.add(new BotPlayer());
                break;            
        }
    }
}
