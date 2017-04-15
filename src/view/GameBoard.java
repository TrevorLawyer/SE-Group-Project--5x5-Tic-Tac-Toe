/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.GameManager;
import controller.MenuListener;
import java.awt.Container;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author micha
 */
public class GameBoard extends JFrame{
    private final JMenuBar menuBar;
    private final JMenu optionsMenu, gameModeSubMenu, localSubMenu;
    public static JMenuItem onePlayerItem, twoPlayerItem, 
            networkItem, startGameItem;
    
    public GameBoard(){
        Container c = getContentPane();
        
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        
        //create menu bar
        menuBar = new JMenuBar();
        
        //add options menu to menu bar
        optionsMenu = new JMenu("Options");
        menuBar.add(optionsMenu);
                
        //local sub-menu
        localSubMenu = new JMenu("Local");
        onePlayerItem = new JMenuItem("1 Player");
        localSubMenu.add(onePlayerItem);
        twoPlayerItem = new JMenuItem("2 Player");
        localSubMenu.add(twoPlayerItem);
        
        
        //network Item
        networkItem = new JMenuItem("Network (AI vs AI)");
        
        //add items to Game Mode sub-menu
        gameModeSubMenu = new JMenu("Game Mode");
        gameModeSubMenu.add(localSubMenu);
        gameModeSubMenu.add(networkItem); 
        
        //Start Game item
        startGameItem = new JMenuItem("Start Game");
        startGameItem.setEnabled(false);
        
        //add items to Options menu
        optionsMenu.add(gameModeSubMenu);                
        optionsMenu.add(startGameItem);
        
        //add menu bar to view
        this.setJMenuBar(menuBar);  
        
        ActionListener menuListener = new MenuListener();
        onePlayerItem.addActionListener(menuListener);
        twoPlayerItem.addActionListener(menuListener);
        networkItem.addActionListener(menuListener);
        startGameItem.addActionListener(menuListener);
                
    }
    
    public static void displayMove(){
        
    }
    public static void opponentMoveDisplay(){
        
    }
    public static void showGameResults(){

    }
}
