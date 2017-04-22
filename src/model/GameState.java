/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
    private byte minMovesRequiredToWin;
    private boolean isMyTurn;
    private boolean hasWinner;
    private boolean isDraw;
    
    public GameState(boolean isMyTurn){
        this.isMyTurn = isMyTurn;
        gameCounter++;
        gameStateID = gameCounter;
        winner = new GameWinner();
    }
    
    public GameState(GameState gameState){
        gameCounter++;
        this.board = gameState.getBoard().cloneBoard();
        this.isMyTurn = gameState.isIsMyTurn();
        this.minMovesRequiredToWin = gameState.minMovesRequiredToWin;
        this.hasWinner = gameState.hasWinner;
        this.isDraw = gameState.isDraw;
        gameStateID = gameCounter;
        winner = new GameWinner();
        
        
    }
    
    public void buildBoard(VirtualGameBoard board){
        this.board = board;
        minMovesRequiredToWin = (byte) ((2 * board.getWidth()) - 1);
        checkForWin();
    }
    
    public GameState makeMove(byte spot){
        GameState state = new GameState(this);
        
        state.getBoard().makeMove(spot, state.isIsMyTurn());
        state.changeTurns();
        return state;
    }
    
    public ArrayList<Spot> availableMoves(){
        return board.getBlankSpaces();
    }
    
    public boolean isBoardUnplayed(){
        return board.isBoardBlank();
    }
    
    public byte finalSpot(){
        if (board.getNumOfBlanks() == 1){
            for (Spot  spot : board.getBlankSpaces()){
                if (spot != null){
                    return (byte) board.getBlankSpaces().indexOf(spot);
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
    
    public void opponentMove(byte Spot){
        board.makeMove(Spot, isMyTurn);
        changeTurns();

    }
    
    private GameWinner checkWinningState(){
        
        for (Byte startIndex : board.getRowStarts()){
            if(winningRow(startIndex)){
                return winner;
            }
        }
        
        for (Byte startIndex : board.getColumnStarts())
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
    
    private boolean winningRow(byte startIndex){
        if(board.contentsOfSpot(startIndex).isTaken())
            if(board.contentsOfSpot((byte) (startIndex + 1)).isTaken())
                if(board.contentsOfSpot((byte) (startIndex + 2)).isTaken())
                    if(board.contentsOfSpot((byte) (startIndex + 3)).isTaken())
                        if(board.contentsOfSpot((byte) (startIndex + 4)).isTaken()){
                            byte playerCount = 1;
                            if(board.contentsOfSpot(startIndex).isxPlayer()){
                                for (byte i = 1; i < 5; i++){
                                    if(board.contentsOfSpot((byte)(startIndex + i)).isxPlayer()){
                                        playerCount++;
                                    }
                                }
                            }else{
                                for (byte i = 1; i < 5; i++){
                                    if(!board.contentsOfSpot((byte)(startIndex + i)).isxPlayer()){
                                        playerCount++;
                                    }
                                }
                            }
                            if(playerCount == 5){
                                if(board.contentsOfSpot(startIndex).isxPlayer()){
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
  
    
    private boolean winningColumn(byte startIndex){
        if(board.contentsOfSpot(startIndex).isTaken())
            if(board.contentsOfSpot((byte) (startIndex + 5)).isTaken())
                if(board.contentsOfSpot((byte) (startIndex + 10)).isTaken())
                    if(board.contentsOfSpot((byte) (startIndex + 15)).isTaken())
                        if(board.contentsOfSpot((byte) (startIndex + 20)).isTaken()){
                            byte playerCount = 1;
                            if(board.contentsOfSpot(startIndex).isxPlayer()){
                                for (byte i = 1; i < 5; i++){
                                    if(board.contentsOfSpot((byte)(startIndex + (5*i))).isxPlayer()){
                                        playerCount++;
                                    }
                                }
                            }else{
                                for (byte i = 1; i < 5; i++){
                                    if(!board.contentsOfSpot((byte)(startIndex +(5*i))).isxPlayer()){
                                        playerCount++;
                                    }
                                }
                            }
                            if(playerCount == 5){
                                if(board.contentsOfSpot(startIndex).isxPlayer()){
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
    
    private boolean winningLRDiag(byte startIndex){
        if(board.contentsOfSpot(startIndex).isTaken())
            if(board.contentsOfSpot((byte) (startIndex + 6)).isTaken())
                if(board.contentsOfSpot((byte) (startIndex + 12)).isTaken())
                    if(board.contentsOfSpot((byte) (startIndex + 18)).isTaken())
                        if(board.contentsOfSpot((byte) (startIndex + 24)).isTaken()){
                            byte playerCount = 1;
                            if(board.contentsOfSpot(startIndex).isxPlayer()){
                                for (byte i = 1; i < board.getNumOfSpaces(); i =(byte) (i + 6)){
                                    if(board.contentsOfSpot((byte)(startIndex + (i))).isxPlayer()){
                                        playerCount++;
                                    }
                                }
                            }else{
                                for (byte i = 1; i < board.getNumOfSpaces(); i =(byte) (i + 6)){
                                    if(!board.contentsOfSpot((byte)(startIndex +(i))).isxPlayer()){
                                        playerCount++;
                                    }
                                }
                            }
                            if(playerCount == 5){
                                if(board.contentsOfSpot(startIndex).isxPlayer()){
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
        private boolean winningRLDiag(byte startIndex){
        if(board.contentsOfSpot(startIndex).isTaken())
            if(board.contentsOfSpot((byte) (startIndex + 4)).isTaken())
                if(board.contentsOfSpot((byte) (startIndex + 8)).isTaken())
                    if(board.contentsOfSpot((byte) (startIndex + 12)).isTaken())
                        if(board.contentsOfSpot((byte) (startIndex + 16)).isTaken()){
                            byte playerCount = 1;
                            if(board.contentsOfSpot(startIndex).isxPlayer()){
                                for (byte i = 1; i < 21; i =(byte) (i + 4)){
                                    if(board.contentsOfSpot((byte)(startIndex + (i))).isxPlayer()){
                                        playerCount++;
                                    }
                                }
                            }else{
                                for (byte i = 1; i < 21; i =(byte) (i + 4)){
                                    if(!board.contentsOfSpot((byte)(startIndex +(i))).isxPlayer()){
                                        playerCount++;
                                    }
                                }
                            }
                            if(playerCount == 5){
                                if(board.contentsOfSpot(startIndex).isxPlayer()){
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

    public byte getMinMovesRequiredToWin() {
        return minMovesRequiredToWin;
    }

    public boolean isIsMyTurn() {
        return isMyTurn;
    }
    
    public void changeTurns(){
        isMyTurn = !isMyTurn;
    }

    public int getGameStateID() {
        return gameStateID;
    }
    
    
}
