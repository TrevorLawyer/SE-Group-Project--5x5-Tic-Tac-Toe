/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.GameMode;
import view.GameBoard;

public class MenuListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == GameBoard.onePlayerItem){
            GameManager.selectGameMode(GameMode.ONE_PLAYER);
            GameBoard.startGameItem.setEnabled(true);
            GameBoard.restartGameItem.setEnabled(false);
        }
        else if(e.getSource() == GameBoard.twoPlayerItem){
            GameManager.selectGameMode(GameMode.TWO_PLAYER);
            GameBoard.startGameItem.setEnabled(true);
            GameBoard.restartGameItem.setEnabled(false);
        }
        else if(e.getSource() == GameBoard.networkItem){
            GameManager.selectGameMode(GameMode.NETWORK);
            GameManager.connectToNetwork();
            GameBoard.startGameItem.setEnabled(true);
            GameBoard.restartGameItem.setEnabled(false);
        } 
        else if(e.getSource() == GameBoard.startGameItem){
            GameBoard.resetBoard();
            GameManager.startGame();
            if(GameManager.gameMode == GameMode.ONE_PLAYER || GameManager.gameMode == GameMode.TWO_PLAYER){
                GameBoard.startHumanGame();
            }
            GameBoard.startGameItem.setEnabled(false);
            GameBoard.restartGameItem.setEnabled(true);
        }
        else if(e.getSource() == GameBoard.restartGameItem){            
            if(GameManager.gameMode == GameMode.NETWORK){
                GameManager.connectToNetwork();
            }
            GameBoard.resetBoard();
            GameManager.startGame();
            if(GameManager.gameMode == GameMode.ONE_PLAYER || GameManager.gameMode == GameMode.TWO_PLAYER){
                GameBoard.startHumanGame();
            }
        }
    }
    
}
