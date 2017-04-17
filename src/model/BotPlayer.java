/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author micha
 */

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author TJaeckel
 */
public class BotPlayer extends Player{
    private final int INITIAL_DEPTH = 0;
    GameState gameState;
    int baseScore;
    byte currentMoveChoice;
    
    public GameState takeTurn(GameState gameState){
        if(gameState.gameOver()) return gameState;
        this.gameState = new GameState(gameState);
        byte move = chooseMove();
        System.out.println("Move Made: " + move);
        this.gameState = gameState.makeMove(move);
        return this.gameState;
    }
    
    private byte chooseMove(){
        if(gameState.isBoardUnplayed()){
            return (byte) 0;
        }
        if(gameState.isFinalMove()){
            return gameState.finalSpot();
        }
        return bestPossibleMove();
    }
    
    private byte bestPossibleMove(){
        baseScore = gameState.availableMoves().size() + 1;
        int bound = baseScore + 1;
        minMax(gameState, INITIAL_DEPTH, -bound, bound);
        return currentMoveChoice;
    }
    
    private int minMax(GameState gameState, int depth, int lowerBound, int upperBound){
        if(gameState.gameOver()){
            return evaluateState(gameState, depth);
        }
        
        ArrayList<MoveNode> moveNodes = new ArrayList();

        for (Spot spot : gameState.availableMoves()){
            if(spot != null){
                GameState childBoard = gameState.makeMove(spot.getIndex());
                System.out.println("Parent: " + gameState.getGameStateID() + " Child Board: " + childBoard.getGameStateID() + "\n" + childBoard.getBoard().toString());
                int score = minMax(childBoard, depth + 1, lowerBound, upperBound);

                MoveNode moveNode = new MoveNode(spot.getIndex(), score);

                if(!gameState.isIsMyTurn()){
                    moveNodes.add(moveNode);
                    if(moveNode.getScore() > lowerBound){
                        lowerBound = moveNode.getScore();
                    }
                }else{
                    if(moveNode.getScore() < upperBound){
                        upperBound = moveNode.getScore();
                    }
                }

                if(upperBound < lowerBound) break;
            }
            
        }
        
        if(!gameState.isIsMyTurn()){
            return upperBound;
        }
        if(!moveNodes.isEmpty()){
            MoveNode highestScore = Collections.max(moveNodes, new CompareMoves());
            currentMoveChoice = highestScore.getSpot();
        }
        return lowerBound;
    }
    
    

    private int evaluateState(GameState gameState, int depth){
        if(gameState.getWinner().getGameWinner() == Winner.X){
            return baseScore - depth;
        }else if(gameState.getWinner().getGameWinner() == Winner.O){
            return depth - baseScore;
        }else return 0;
    }
}
