/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.swing.JFrame;
import view.GameBoard;

/**
 *
 * @author micha
 */
public class Main {
    public static final int WIN_WIDTH = 800;
    public static final int WIN_HEIGHT = 800;
    
    public static void main(String[] args){
        
        JFrame gameBoard = new GameBoard();
        gameBoard.setTitle("The Tica Tacs: 5 X 5 Tic-tac-toe");
        gameBoard.setSize(WIN_WIDTH, WIN_HEIGHT);
        gameBoard.setLocation(100, 0);
        gameBoard.setResizable(false);
        gameBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameBoard.setVisible(true);
        
        //new Thread(someThread).start();
    }
}
