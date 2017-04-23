/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.GameManager;
import controller.GameState;
import controller.MoveNode;
import controller.Spot;
import java.util.ArrayList;

/**
 *
 * @author TJaeckel
 */
public class BotPlayer extends Player{
    private final int INITIAL_DEPTH = 0;
    GameState gameState;
    int baseScore;
    int currentMoveChoice;

    public BotPlayer(boolean isXPlayer) {
        super(isXPlayer);
    }
    
    @Override
    public GameState takeTurn(GameState gameState, int selectedSpot){
        if(gameState.gameOver()) {
            return gameState;
        }
        this.gameState = new GameState(gameState);
        int move = chooseMove();
        System.out.println("Move Made: " + move);
        this.gameState = gameState.makeBotMove(move, true);
        GameManager.selectedMove = move;
        return this.gameState;
    }
    
    private int chooseMove(){
        if(gameState.isBoardUnplayed()){
            return (int) 0;
        }
        if(gameState.isFinalMove()){
            return gameState.finalSpot();
        }
        return bestPossibleMove();
    }
    
    private int bestPossibleMove(){
        baseScore = gameState.availableMoves().size() + 1;
        
        int bound = baseScore + 1;
        currentMoveChoice = findMove(gameState);
        return currentMoveChoice;
    }
    
   
    private int findMove(GameState gameState){
        ArrayList<MoveNode> moves = new ArrayList();
        
        for (Spot spot : gameState.getBoard().getBoard()){
            moves.add(new MoveNode(spot.getIndex()));
            
        }

        scoreOnOpponent(moves);
        String moveBoard = "";
        for (MoveNode move : moves){
            moveBoard += move.toString();
        }
        System.out.print(moveBoard);
        System.out.println();
        scoreOnMe(moves);
        moveBoard = "";
        for (MoveNode move : moves){
            moveBoard += move.toString();
        }
        System.out.print(moveBoard);
        
        
        return findHighestScore(moves);
    }
    
    private void scoreOnOpponent( ArrayList<MoveNode> moves){
        int[] lrDiagonal = new int[]{0,6,12,18,24};
        int[] rlDiagonal = new int[]{4,8,12,16,20};
        int scoreToAdd = 0;
        boolean iHaveSpot = false;
        
        

        for(int rowIndex : gameState.getBoard().getRowStarts()){
            iHaveSpot = false;
            scoreToAdd = 0;
            for(int i = rowIndex; i < (rowIndex + 5); i++){
                if(!gameState.getBoard().getBoard().get(i).isMe() && gameState.getBoard().getBoard().get(i).isTaken()){
                    scoreToAdd++;
                }
                
                if(gameState.getBoard().getBoard().get(i).isMe()){
                    iHaveSpot = true;
                }
                
            }
            
            if(iHaveSpot){
                scoreToAdd = 0;
            }
            //if(scoreToAdd > 3) scoreToAdd *= 2;
            for(int i = rowIndex; i < (rowIndex + 5); i++){
                if(!gameState.getBoard().getBoard().get(i).isTaken()){
                    moves.get(i).addScore(scoreToAdd);
                }
            }
        }
        scoreToAdd = 0;
        for(int columnIndex : gameState.getBoard().getColumnStarts()){
            iHaveSpot = false;
            scoreToAdd = 0;
            for(int i = columnIndex; i < columnIndex + 21; i+= 5){
                if(!gameState.getBoard().getBoard().get(i).isMe()  && gameState.getBoard().getBoard().get(i).isTaken()){
                    scoreToAdd++;
                }
                if(gameState.getBoard().getBoard().get(i).isMe()){
                    iHaveSpot = true;
                }
            }
            
            if(iHaveSpot){
                scoreToAdd = 0;
            }
            //if(scoreToAdd > 3) scoreToAdd *= 2;
            for(int i = columnIndex; i < columnIndex + 21; i+= 5){
                if(!gameState.getBoard().getBoard().get(i).isTaken()){
                    moves.get(i).addScore(scoreToAdd);
                }
            }
        }

        //Check LR Diagonal
        scoreToAdd = 0;
        iHaveSpot = false;
        for (int spotIndex : lrDiagonal){
            if(!gameState.getBoard().getBoard().get(spotIndex).isMe()  && gameState.getBoard().getBoard().get(spotIndex).isTaken()){
                    scoreToAdd++;
            }
            if(gameState.getBoard().getBoard().get(spotIndex).isMe()){
                    iHaveSpot = true;
            }
        }
        
        if(iHaveSpot){
            scoreToAdd = 0;
        }
        //if(scoreToAdd > 3) scoreToAdd *= 2;
        for (int spotIndex : lrDiagonal){
            if(!gameState.getBoard().getBoard().get(spotIndex).isTaken()){
                moves.get(spotIndex).addScore(scoreToAdd);
            }
            
        }

        //Check rlDiagonal
        scoreToAdd = 0;
        iHaveSpot = false;
        for (int spotIndex : rlDiagonal){
            if(!gameState.getBoard().getBoard().get(spotIndex).isMe() && gameState.getBoard().getBoard().get(spotIndex).isTaken()){
                    scoreToAdd++;
                }
            if(gameState.getBoard().getBoard().get(spotIndex).isMe()){
                    iHaveSpot = true;
            }
        }
        
        if(iHaveSpot){
            scoreToAdd = 0;
        }
        //if(scoreToAdd > 3) scoreToAdd *= 2;
        for (int spotIndex : rlDiagonal){
            if(!gameState.getBoard().getBoard().get(spotIndex).isTaken()){
                moves.get(spotIndex).addScore(scoreToAdd);
            }
        }
            

    }
    
    private void scoreOnMe(ArrayList<MoveNode> moves){
        int[] lrDiagonal = new int[]{0,6,12,18,24};
        int[] rlDiagonal = new int[]{4,8,12,16,20};
        int scoreToAdd;
        
        

        for(int rowIndex : gameState.getBoard().getRowStarts()){
            scoreToAdd = 0;
            for(int i = rowIndex; i < (rowIndex + 5); i++){
                if(gameState.getBoard().getBoard().get(i).isMe()){
                    scoreToAdd++;
                }
            }
            for(int i = rowIndex; i < (rowIndex + 5); i++){
                if(!gameState.getBoard().getBoard().get(i).isTaken()){
                    moves.get(i).addScore(scoreToAdd);
                }
            }
        }
        
        for(int columnIndex : gameState.getBoard().getColumnStarts()){
            scoreToAdd = 0;
            for(int i = columnIndex; i < columnIndex + 21; i+= 5){
                if(gameState.getBoard().getBoard().get(i).isMe()){
                    scoreToAdd++;
                }
            }
            for(int i = columnIndex; i < columnIndex + 21; i+= 5){
                if(!gameState.getBoard().getBoard().get(i).isTaken()){
                    moves.get(i).addScore(scoreToAdd);
                }
            }
        }

        //Check LR Diagonal
        scoreToAdd = 0;
        for (int spotIndex : lrDiagonal){
            if(gameState.getBoard().getBoard().get(spotIndex).isMe()){
                    scoreToAdd++;
                }
        }
        for (int spotIndex : lrDiagonal){
            if(!gameState.getBoard().getBoard().get(spotIndex).isTaken()){
                moves.get(spotIndex).addScore(scoreToAdd);
            }
        }

        //Check rlDiagonal
        scoreToAdd = 0;
        for (int spotIndex : rlDiagonal){
            if(gameState.getBoard().getBoard().get(spotIndex).isMe()){
                    scoreToAdd++;
                }
        }
        for (int spotIndex : rlDiagonal){
            if(!gameState.getBoard().getBoard().get(spotIndex).isTaken()){
                moves.get(spotIndex).addScore(scoreToAdd);
            }
        }
    }
    
    private int findHighestScore(ArrayList<MoveNode> moves){
        int highestScore = -1;
        int bestSpot = -1;
        
        for (MoveNode move : moves){
            if(move.getScore() > highestScore){
                bestSpot = move.getSpot();
                highestScore = move.getScore();
            }
        }
        
        return bestSpot;
    }

}
