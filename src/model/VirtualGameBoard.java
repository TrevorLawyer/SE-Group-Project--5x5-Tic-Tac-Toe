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
public class VirtualGameBoard {
    private byte width;
    private byte numOfSpaces;
    private byte numOfBlanks;
    private byte numOccupied;
    private ArrayList<Spot> board;
    private ArrayList<Spot> blankSpaces;
    private ArrayList<Byte> columnStarts;
    private ArrayList<Byte> rowStarts;
    private ArrayList<Byte> diagonalStarts;
    
    public VirtualGameBoard(byte width){
        this.width = width;
        numOfSpaces = numOfBlanks = (byte) (width * width);
        board = new ArrayList(numOfSpaces);
        blankSpaces = new ArrayList(width);
        for (byte i = 0; i < numOfSpaces; i++){
            board.add(new Spot(i));
            blankSpaces.add(new Spot(i));
        }
        setStarts();
    }
    
    public VirtualGameBoard(ArrayList<Spot> board, ArrayList<Spot> blankSpaces, 
            byte width){
        
        this.width = width;
        numOfSpaces = (byte) (width * width);
        this.numOccupied = numOccupied;
        this.board = board;
        this.blankSpaces = blankSpaces;
        for(byte i = 0; i < blankSpaces.size(); i++){
            if(blankSpaces.get(i) != null){
                numOfBlanks ++;
            }else{
                numOccupied++;
            }
        }
        setStarts();
    }
    
    private void setStarts(){
        columnStarts = new ArrayList<>();
        rowStarts = new ArrayList<>();
        diagonalStarts = new ArrayList<>();
        
        for (byte i = 0; i < width; i++){
            columnStarts.add(i);
            rowStarts.add((byte) (i * width));
        }
        diagonalStarts.add((byte) 0);
        diagonalStarts.add((byte) (width - 1));
        
    }
    
    public void makeMove(byte spot, boolean isMyTurn){
        board.get(spot).setTaken(true);
        board.get(spot).setxPlayer(isMyTurn);
        numOfBlanks--;
        numOccupied++;
        blankSpaces.set(spot, null);
    }
    
    public Spot contentsOfSpot(byte spot){
        return board.get(spot);
    }
    
    public VirtualGameBoard cloneBoard(){
        ArrayList<Spot> cloneBoard = new ArrayList<Spot>(board.size());
        for(Spot spot : board) cloneBoard.add(spot.getIndex(), new Spot(spot));
        
        ArrayList<Spot> cloneBlankSpaces = new ArrayList<Spot>(blankSpaces.size());
        
        for(byte i = 0; i < blankSpaces.size(); i++){
            if(blankSpaces.get(i) != null){
                cloneBlankSpaces.add(i, new Spot(blankSpaces.get(i)));
            }else{
                cloneBlankSpaces.add(i, null);
            }
        }
        
        return new VirtualGameBoard(cloneBoard, cloneBlankSpaces, width);
        
    }
    
    public boolean isBoardBlank(){
        return numOfBlanks == numOfSpaces;
    }
    
    public ArrayList<Spot> getBlankSpaces(){
        return blankSpaces;
    }

    public byte getWidth() {
        return width;
    }

    public byte getNumOfSpaces() {
        return numOfSpaces;
    }

    public byte getNumOfBlanks() {
        return numOfBlanks;
    }

    public byte getNumOccupied() {
        return numOccupied;
    }

    public ArrayList<Byte> getColumnStarts() {
        return columnStarts;
    }

    public ArrayList<Byte> getRowStarts() {
        return rowStarts;
    }

    public ArrayList<Byte> getDiagonalStarts() {
        return diagonalStarts;
    }

    public ArrayList<Spot> getBoard() {
        return board;
    }
    
    @Override
    public String toString(){
        String gameBoard="";
        
        for (Spot spot : board){
            if(spot.getIndex() % 5 == 0){
                gameBoard += "\n";
            }
            if(spot.isTaken()){
                if(spot.isxPlayer()){
                    gameBoard += " |x| ";
                }else{
                    gameBoard += " |0| ";
                }
            }else{
                gameBoard += " | | ";
            }
            
        }
        return gameBoard;
    }
}
