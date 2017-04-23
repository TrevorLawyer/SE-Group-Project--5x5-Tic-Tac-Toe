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
    
    public static void makeMove(){
        if(gameMode == GameMode.ONE_PLAYER){
            gameState = playerOne.takeTurn(gameState, selectedMove);
            GameBoard.displayMove(selectedMove);
            if(gameState.gameOver()) GameBoard.showGameResults();              
            gameState = playerTwo.takeTurn(gameState, selectedMove);
            GameBoard.displayMove(selectedMove);
            if(gameState.gameOver()) GameBoard.showGameResults();            
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
            if(gameState.gameOver()) GameBoard.showGameResults();  
        }                
        else{
            while(true){
                long t1 = System.currentTimeMillis();
                gameState = playerOne.takeTurn(gameState, selectedMove);
                long t2 = System.currentTimeMillis();
                
                if(t2 - t1 < 5000){                
                    try {
                        Thread.sleep(5000 - (t2 - t1));
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
                GameBoard.displayMove(selectedMove);
                network.sendMove(selectedMove);
                if (gameState.gameOver()) break;
                selectedMove = network.getMove();
                gameState.opponentMove(selectedMove);
                GameBoard.displayMove(selectedMove);
                if (gameState.gameOver()) break;
            }
            GameBoard.showGameResults();            
        }                
        
    }
}
