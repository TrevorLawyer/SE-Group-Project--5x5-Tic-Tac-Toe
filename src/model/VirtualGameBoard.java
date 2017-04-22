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
    private int width;
    private int numOfSpaces;
    private int numOfBlanks;
    private int numOccupied;
    private ArrayList<Spot> board;
    private ArrayList<Spot> blankSpaces;
    private ArrayList<Integer> columnStarts;
    private ArrayList<Integer> rowStarts;
    private ArrayList<Integer> diagonalStarts;
    
    public VirtualGameBoard(int width){
        this.width = width;
        numOfSpaces = numOfBlanks = (int) (width * width);
        board = new ArrayList(numOfSpaces);
        blankSpaces = new ArrayList(width);
        for (int i = 0; i < numOfSpaces; i++){
            board.add(new Spot(i));
            blankSpaces.add(new Spot(i));
        }
        setStarts();
    }
    
    public VirtualGameBoard(ArrayList<Spot> board, ArrayList<Spot> blankSpaces, 
            int width){
        
        this.width = width;
        numOfSpaces = (int) (width * width);
        this.numOccupied = numOccupied;
        this.board = board;
        this.blankSpaces = blankSpaces;
        for(int i = 0; i < blankSpaces.size(); i++){
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
        
        for (int i = 0; i < width; i++){
            columnStarts.add(i);
            rowStarts.add((int) (i * width));
        }
        diagonalStarts.add((int) 0);
        diagonalStarts.add((int) (width - 1));
        
    }
    
    public void makeMove(int spot, boolean isMyTurn){
        board.get(spot).setTaken(true);
        board.get(spot).setxPlayer(isMyTurn);
        numOfBlanks--;
        numOccupied++;
        blankSpaces.set(spot, null);
    }
    
    public Spot contentsOfSpot(int spot){
        return board.get(spot);
    }
    
    public VirtualGameBoard cloneBoard(){
        ArrayList<Spot> cloneBoard = new ArrayList<Spot>(board.size());
        for(Spot spot : board) cloneBoard.add(spot.getIndex(), new Spot(spot));
        
        ArrayList<Spot> cloneBlankSpaces = new ArrayList<Spot>(blankSpaces.size());
        
        for(int i = 0; i < blankSpaces.size(); i++){
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

    public int getWidth() {
        return width;
    }

    public int getNumOfSpaces() {
        return numOfSpaces;
    }

    public int getNumOfBlanks() {
        return numOfBlanks;
    }

    public int getNumOccupied() {
        return numOccupied;
    }

    public ArrayList<Integer> getColumnStarts() {
        return columnStarts;
    }

    public ArrayList<Integer> getRowStarts() {
        return rowStarts;
    }

    public ArrayList<Integer> getDiagonalStarts() {
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
