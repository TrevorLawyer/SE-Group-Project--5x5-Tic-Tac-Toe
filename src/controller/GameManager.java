/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import model.BotPlayer;
import model.GameMode;
import model.HumanPlayer;
import model.Player;
import model.SocketNetwork;
import controller.GameState;
import controller.VirtualGameBoard;
import model.IntraSystem;
import view.GameBoard;


public class GameManager{
    private static GameState gameState;
    private static VirtualGameBoard gameBoard;
    private static NetworkImplementer network;
    public static int selectedMove = 0;
    public static int gameMode;
    private static Player playerOne;   
    private static Player playerTwo;
    private static String turn = "p1";
    
    public static void selectGameMode(int gm){
        gameMode = gm;
    }
    
    public static void connectToNetwork(){
        network = new SocketNetwork();
    }
    
    public static void startGame(){        
        if(gameMode != GameMode.NETWORK){
            network = new IntraSystem();
        }        
        
        gameState = new GameState(true);
        gameBoard = new VirtualGameBoard(5);
        gameState.buildBoard(gameBoard);
        
        switch (gameMode) {        
            case GameMode.ONE_PLAYER:
                playerOne = new HumanPlayer(false);
                playerTwo = new BotPlayer(true);
                break;        
            case GameMode.TWO_PLAYER:
                playerOne = new HumanPlayer(false);
                playerTwo = new HumanPlayer(true);
                break;        
            case GameMode.NETWORK:
                playerOne = new BotPlayer(false);
                break;            
        }        
        if(gameMode == GameMode.NETWORK){
           makeMove();
        }
    }    
    
    private static int receiveMoveOverNetwork(){
        int m = network.getMove();   
        GameBoard.displayMove(m);
        return m;
    }
    
    public static void makeMove(){
        if(gameMode == GameMode.ONE_PLAYER){
            gameState = playerOne.takeTurn(gameState, selectedMove);
            GameBoard.displayMove(selectedMove);
            gameState = playerTwo.takeTurn(gameState, selectedMove);
            GameBoard.displayMove(selectedMove);
        }
        else if(gameMode == GameMode.TWO_PLAYER){
            if(turn == "p1"){
                gameState = playerOne.takeTurn(gameState, selectedMove);
                turn = "p2";
            }
            else{
                gameState = playerTwo.takeTurn(gameState, selectedMove);
                turn = "p1";
            }
            GameBoard.displayMove(selectedMove);
        }                
        else{
            gameState = playerOne.takeTurn(gameState, selectedMove);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            GameBoard.displayMove(selectedMove);
            network.sendMove(selectedMove);
            int selectedMove = receiveMoveOverNetwork();
            gameState.opponentMove(selectedMove);
            GameBoard.displayMove(selectedMove);
            
        }                
        
    }
}
