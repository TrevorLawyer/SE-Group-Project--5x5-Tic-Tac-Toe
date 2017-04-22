/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;

/**
 *
 * @author TJaeckel
 */
public class GameState {
    static int gameCounter;
    private int gameStateID;
    private VirtualGameBoard board;
    private GameWinner winner;
    private int minMovesRequiredToWin;
    private boolean isXTurn;
    private boolean hasWinner;
    private boolean isDraw;
    
    public GameState(boolean isXTurn){
        this.isXTurn = isXTurn;
        gameCounter++;
        gameStateID = gameCounter;
        winner = new GameWinner();
    }
    
    public GameState(GameState gameState){
        gameCounter++;
        this.board = gameState.getBoard().cloneBoard();
        this.isXTurn = gameState.isIsXTurn();
        this.minMovesRequiredToWin = gameState.minMovesRequiredToWin;
        this.hasWinner = gameState.hasWinner;
        this.isDraw = gameState.isDraw;
        gameStateID = gameCounter;
        winner = new GameWinner();
        
        
    }
    
    public void buildBoard(VirtualGameBoard board){
        this.board = board;
        minMovesRequiredToWin = (int) ((2 * board.getWidth()) - 1);
        checkForWin();
    }
    
    public GameState makeBotMove(int spot, boolean isMe){
        GameState state = new GameState(this);
        
        state.getBoard().makeBotMove(spot, isXTurn, isMe);
        state.changeTurns();
        return state;
    }
    
    public GameState makeMove(int spot){
        GameState state = new GameState(this);
        
        state.getBoard().makeMove(spot, state.isIsXTurn());
        state.changeTurns();
        return state;
    }
    
    public ArrayList<Spot> availableMoves(){
        return board.getBlankSpaces();
    }
    
    public boolean isBoardUnplayed(){
        return board.isBoardBlank();
    }
    
    public int finalSpot(){
        if (board.getNumOfBlanks() == 1){
            for (Spot  spot : board.getBlankSpaces()){
                if (spot != null){
                    return (int) board.getBlankSpaces().indexOf(spot);
                }
            }
        }
        return -1;
    }
    
    public boolean isFinalMove(){
        return board.getNumOfBlanks() == 1;
    }
    
    public boolean gameOver(){
        checkForWin();
        return hasWinner | isDraw;
    }
    
    public boolean hasWinner(){
        return hasWinner;
    }
    
    public boolean iWon(){
        if(hasWinner){
            return winner.getGameWinner() == Winner.X;
        }
        
        return false;
    }
    
    public void checkForWin(){
        if(board.getNumOccupied() >= minMovesRequiredToWin){
            winner = checkWinningState();
        }        
    }
    
    public void opponentMove(int Spot){
        board.makeMove(Spot, isXTurn);
        changeTurns();

    }
    
    private GameWinner checkWinningState(){
        
        for (int startIndex : board.getRowStarts()){
            if(winningRow(startIndex)){
                return winner;
            }
        }
        
        for (int startIndex : board.getColumnStarts())
            if(winningColumn(startIndex)){
                return winner;
            }
        
        if(winningLRDiag(board.getDiagonalStarts().get(0))){
            return winner;
        }
        
        if(winningRLDiag(board.getDiagonalStarts().get(1))){
            return winner;
        }
        
        if(board.getNumOfBlanks() == 0 && winner.getGameWinner() == Winner.NONE){
            isDraw = true;
            winner.setGameWinner(Winner.TIE);
            return winner;
        }
        
        return new GameWinner();
    }
    
    private boolean winningRow(int startIndex){
        if(board.contentsOfSpot(startIndex).isTaken())
            if(board.contentsOfSpot((int) (startIndex + 1)).isTaken())
                if(board.contentsOfSpot((int) (startIndex + 2)).isTaken())
                    if(board.contentsOfSpot((int) (startIndex + 3)).isTaken())
                        if(board.contentsOfSpot((int) (startIndex + 4)).isTaken()){
                            int playerCount = 1;
                            if(board.contentsOfSpot(startIndex).isMe()){
                                for (int i = 1; i < 5; i++){
                                    if(board.contentsOfSpot((int)(startIndex + i)).isMe()){
                                        playerCount++;
                                    }
                                }
                            }else{
                                for (int i = 1; i < 5; i++){
                                    if(!board.contentsOfSpot((int)(startIndex + i)).isMe()){
                                        playerCount++;
                                    }
                                }
                            }
                            if(playerCount == 5){
                                if(board.contentsOfSpot(startIndex).isMe()){
                                    winner.setGameWinner(Winner.X);
                                    hasWinner = true;
                                    return true;
                                }else{
                                    winner.setGameWinner(Winner.O);
                                    hasWinner = true;
                                    return true;
                                }
                            }
                        }
        return false;
    }
  
    
    private boolean winningColumn(int startIndex){
        if(board.contentsOfSpot(startIndex).isTaken())
            if(board.contentsOfSpot((int) (startIndex + 5)).isTaken())
                if(board.contentsOfSpot((int) (startIndex + 10)).isTaken())
                    if(board.contentsOfSpot((int) (startIndex + 15)).isTaken())
                        if(board.contentsOfSpot((int) (startIndex + 20)).isTaken()){
                            int playerCount = 1;
                            if(board.contentsOfSpot(startIndex).isMe()){
                                for (int i = 1; i < 5; i++){
                                    if(board.contentsOfSpot((int)(startIndex + (5*i))).isMe()){
                                        playerCount++;
                                    }
                                }
                            }else{
                                for (int i = 1; i < 5; i++){
                                    if(!board.contentsOfSpot((int)(startIndex +(5*i))).isMe()){
                                        playerCount++;
                                    }
                                }
                            }
                            if(playerCount == 5){
                                if(board.contentsOfSpot(startIndex).isMe()){
                                    winner.setGameWinner(Winner.X);
                                    hasWinner = true;
                                    return true;
                                }else{
                                    winner.setGameWinner(Winner.O);
                                    hasWinner = true;
                                    return true;
                                }
                            }
                        }
        return false;
    }
    
    private boolean winningLRDiag(int startIndex){
        if(board.contentsOfSpot(startIndex).isTaken())
            if(board.contentsOfSpot((int) (startIndex + 6)).isTaken())
                if(board.contentsOfSpot((int) (startIndex + 12)).isTaken())
                    if(board.contentsOfSpot((int) (startIndex + 18)).isTaken())
                        if(board.contentsOfSpot((int) (startIndex + 24)).isTaken()){
                            int playerCount = 1;
                            if(board.contentsOfSpot(startIndex).isMe()){
                                for (int i = 1; i < board.getNumOfSpaces(); i =(int) (i + 6)){
                                    if(board.contentsOfSpot((int)(startIndex + (i))).isMe()){
                                        playerCount++;
                                    }
                                }
                            }else{
                                for (int i = 1; i < board.getNumOfSpaces(); i =(int) (i + 6)){
                                    if(!board.contentsOfSpot((int)(startIndex +(i))).isMe()){
                                        playerCount++;
                                    }
                                }
                            }
                            if(playerCount == 5){
                                if(board.contentsOfSpot(startIndex).isMe()){
                                    winner.setGameWinner(Winner.X);
                                    hasWinner = true;
                                    return true;
                                }else{
                                    winner.setGameWinner(Winner.O);
                                    hasWinner = true;
                                    return true;
                                }
                            }
                        }
        return false;
    }
        private boolean winningRLDiag(int startIndex){
        if(board.contentsOfSpot(startIndex).isTaken())
            if(board.contentsOfSpot((int) (startIndex + 4)).isTaken())
                if(board.contentsOfSpot((int) (startIndex + 8)).isTaken())
                    if(board.contentsOfSpot((int) (startIndex + 12)).isTaken())
                        if(board.contentsOfSpot((int) (startIndex + 16)).isTaken()){
                            int playerCount = 1;
                            if(board.contentsOfSpot(startIndex).isMe()){
                                for (int i = 1; i < 21; i =(int) (i + 4)){
                                    if(board.contentsOfSpot((int)(startIndex + (i))).isMe()){
                                        playerCount++;
                                    }
                                }
                            }else{
                                for (int i = 1; i < 21; i =(int) (i + 4)){
                                    if(!board.contentsOfSpot((int)(startIndex +(i))).isMe()){
                                        playerCount++;
                                    }
                                }
                            }
                            if(playerCount == 5){
                                if(board.contentsOfSpot(startIndex).isMe()){
                                    winner.setGameWinner(Winner.X);
                                    hasWinner = true;
                                    return true;
                                }else{
                                    winner.setGameWinner(Winner.O);
                                    hasWinner = true;
                                    return true;
                                }
                            }
                        }
        return false;
    }

    public VirtualGameBoard getBoard() {
        return board;
    }

    public GameWinner getWinner() {
        return winner;
    }

    public int getMinMovesRequiredToWin() {
        return minMovesRequiredToWin;
    }

    public boolean isIsXTurn() {
        return isXTurn;
    }
    
    public void changeTurns(){
        isXTurn = !isXTurn;
    }

    public int getGameStateID() {
        return gameStateID;
    }
    
    
}
