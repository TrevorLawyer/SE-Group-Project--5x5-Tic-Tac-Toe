package controller;

import model.BotPlayer;
import model.GameMode;
import model.HumanPlayer;
import model.Player;
import model.SocketNetwork;
import controller.GameState;
import controller.VirtualGameBoard;
import view.GameBoard;


public class GameManager{
    private static GameState gameState;
    private static VirtualGameBoard gameBoard;
    private static SocketNetwork network;
    public static int selectedMove = 0;
    public static int gameMode;
    private static Player playerOne;   
    private static Player playerTwo;
    private static String turn = "p1";
    private static boolean timeout = false;
    private static Thread t; 
    
    public static void selectGameMode(int gm){
        gameMode = gm;
    }
    
    public static void connectToNetwork(){
        network = new SocketNetwork();
    }
    
    public static void startGame(){        
        
        gameState = new GameState(true);
        gameBoard = new VirtualGameBoard(5);
        gameState.buildBoard(gameBoard);
        
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                Thread thisThread = Thread.currentThread();
                while (t == thisThread) {
                    if (network.isFirstPlayer) {
                        localTurn();
                        if (gameState.gameOver()) {
                            showResults();
                        }
                        if(t == thisThread){
                            networkTurn();
                        }
                        if (gameState.gameOver() || timeout) {
                            showResults();
                        }
                    } else {
                        networkTurn();
                        if (gameState.gameOver() || timeout) {
                            showResults();
                        }
                        if (t == thisThread) {
                            localTurn();
                        }
                        if (gameState.gameOver()) {
                            showResults();
                        }
                    }
                }
            }
        });

        switch (gameMode) {        
            case GameMode.ONE_PLAYER:
                playerOne = new HumanPlayer(true);
                playerTwo = new BotPlayer(false);
                break;        
            case GameMode.TWO_PLAYER:
                playerOne = new HumanPlayer(false);
                playerTwo = new HumanPlayer(true);
                break;        
            case GameMode.NETWORK:
                playerOne = new BotPlayer(false);   
                t.start();
                break;            
        }        
    }         
    
    public static void makeMove(){
        if(gameMode == GameMode.ONE_PLAYER){
            gameState = playerOne.takeTurn(gameState, selectedMove);
            GameBoard.displayMove(selectedMove, playerOne.isIsXPlayer());
            if(gameState.gameOver()){
                System.out.println(gameState.getWinner().getGameWinner());
                GameBoard.showGameResults(gameState.getWinner().getGameWinner());
            }
            else{
                gameState = playerTwo.takeTurn(gameState, selectedMove);
                GameBoard.displayMove(selectedMove, playerTwo.isIsXPlayer());
                if (gameState.gameOver()) {
                    System.out.println(gameState.getWinner().getGameWinner());
                    GameBoard.showGameResults(gameState.getWinner().getGameWinner());
                }
            }            
        }
        else if(gameMode == GameMode.TWO_PLAYER){
            Player currentPlayer;
            if(turn == "p1"){
                gameState = playerOne.takeTurn(gameState, selectedMove);
                turn = "p2";
                currentPlayer = playerOne;
            }
            else{
                gameState = playerTwo.takeTurn(gameState, selectedMove);
                turn = "p1";
                currentPlayer = playerTwo;
            }
            GameBoard.displayMove(selectedMove, currentPlayer.isIsXPlayer());
            if(gameState.gameOver()) GameBoard.showGameResults(gameState.getWinner().getGameWinner());  
        }                                
        
    }
    private static void localTurn(){
        long t1 = System.currentTimeMillis();
        gameState = playerOne.takeTurn(gameState, selectedMove);
        long t2 = System.currentTimeMillis();            
        if (t2 - t1 < 5000) {
            try {
                Thread.sleep(5000 - (t2 - t1));
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        displayMove(selectedMove, playerOne.isIsXPlayer());        
        network.sendMove(selectedMove);        
    }
    
    private static void networkTurn(){
        selectedMove = Main.host.getMove();
        if(selectedMove >= 0){
            gameState.opponentMove(selectedMove);
            displayMove(selectedMove, !playerOne.isIsXPlayer());
        }
        else{
            timeout = true;
        }
        
    }

    private static void showResults() {
        GameBoard.showGameResults(gameState.getWinner().getGameWinner());            
    }

    private static void displayMove(int selectedMove, boolean XPlayer) {        
        GameBoard.displayMove(selectedMove, XPlayer);
    }
    
    public static void endNetworkPlay(){
        t = null;
    }
}
