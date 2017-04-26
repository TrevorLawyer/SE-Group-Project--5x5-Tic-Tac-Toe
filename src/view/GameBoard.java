/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.GameManager;
import controller.MenuListener;
import controller.Winner;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import model.Player;

public class GameBoard extends JFrame{    
    private final JMenuBar menuBar;
    private final JMenu optionsMenu, gameModeSubMenu, localSubMenu;
    public static JMenuItem onePlayerItem, twoPlayerItem, 
            networkItem, startGameItem, restartGameItem;
    public static ArrayList<JButton> tiles;
    private static final int MAX_TILES = 25;
    private Font buttonFont= new Font("SansSerif", Font.BOLD, 60);
    private static String currentTurn;
    
    public GameBoard(){
        Container c = getContentPane();
        
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        
        currentTurn = "X";
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
        
        restartGameItem = new JMenuItem("New Game");
        restartGameItem.setEnabled(false);
        
        //add items to Options menu
        optionsMenu.add(gameModeSubMenu);                
        optionsMenu.add(startGameItem);
        optionsMenu.add(restartGameItem);
        
        //add menu bar to view
        this.setJMenuBar(menuBar);  
        
        ActionListener menuListener = new MenuListener();
        onePlayerItem.addActionListener(menuListener);
        twoPlayerItem.addActionListener(menuListener);
        networkItem.addActionListener(menuListener);
        startGameItem.addActionListener(menuListener);
        restartGameItem.addActionListener(menuListener);
        
        //Add button panel for gameplay 
        JPanel gameArea = new JPanel();
        ButtonListener buttonListener = new ButtonListener();
        gameArea.setLayout(new GridLayout(5,5));
        tiles = new ArrayList<>();
        for(int i = 0; i< MAX_TILES; i++ ){
            tiles.add(new JButton());
            gameArea.add(tiles.get(i));
            tiles.get(i).addActionListener(buttonListener);
            tiles.get(i).setFont(buttonFont);
            tiles.get(i).setEnabled(false);
        }
        c.add(gameArea);
                
    }
    public static void startHumanGame(){
        for(int i = 0; i < MAX_TILES;i++){
            tiles.get(i).setEnabled(true);
        }
    }
    
    //Wipes the board and returns it to default un-clickable state. Call before startHumanGame();
    public static void resetBoard(){
        for (JButton i : tiles){
            i.setText("");
            i.setEnabled(false);
        }
    }
    
    public static void showGameResults(Winner winner) {
        String winMessage;
        if (winner.name().equals("TIE")){
            winMessage = "The Game Ended in a Tie!";
        }
        else if(winner.name().equals("X")){
            winMessage = "X Player is the Winner!";
        }
        else if(winner.name().equals("O")){
            winMessage = "O Player is the Winner!";
        }
        else{
            winMessage = "Something went wrong and no one Won!";
        }
        JOptionPane.showMessageDialog(null, winMessage);
    }
    public static void displayMove(int m, boolean playerIsX){
        //
        if (playerIsX){
            currentTurn = "X";
        }
        else{
            currentTurn = "O";
        }
        
        tiles.get(m).setText(currentTurn);
        tiles.get(m).setEnabled(false);

    }
    public class ButtonListener implements ActionListener {

    @Override
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton)e.getSource();
            for (int i = 0; i < MAX_TILES; i++){
                if(source == tiles.get(i)){
                    GameManager.selectedMove = i;
                    GameManager.makeMove();
                }
            }
        }
    
}

}
