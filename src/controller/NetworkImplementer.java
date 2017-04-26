/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author micha
 */
public abstract class NetworkImplementer {
    public boolean isFirstPlayer;
    public abstract void sendMove(int m);
    //public abstract int getMove();
}
