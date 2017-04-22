package model;

import controller.GameState;
import controller.NetworkImplementer;
import controller.VirtualGameBoard;

public class Game {
    private final NetworkImplementer network;
    private final int SIZE = 5;
    private final char X = 'X';
    private final char Y = 'Y';
    private char[][] moves;
    private GameState gameState;
    private VirtualGameBoard gameBoard;
    
    
    public Game(int gm){
        moves = new char[SIZE][SIZE];
        if(gm == GameMode.NETWORK){
            network = new SocketNetwork();
        }
        else{
            network = new IntraSystem();
        }
        
        gameState = new GameState(true);
        gameBoard = new VirtualGameBoard(5);
        gameState.buildBoard(gameBoard);
        
    }
    
    public void makeMove(int m){
        if(moves[m/5][m%5] != X && moves[m/5][m%5] != Y){
            moves[m/5][m%5] = X;
            network.sendMove(m);            
        }
        else{
            System.out.println("This space is already ocupied");
        }
    }
    
    public int receiveMoveOverNetwork(){
        int m = network.getMove();
        if(moves[m/5][m%5] != X && moves[m/5][m%5] != Y){            
            moves[m/5][m%5] = Y;
            return m;
        }
        else{
            System.out.println("Opponent made invalid move");
            return 25;
        }
    }
    
    public boolean checkGameOver(){
        int row = 0;
        int col = 0;
        
        //Check X
        while(row < SIZE){
            if (moves[row][col] == X
                    && moves[row][col + 1] == X
                    && moves[row][col + 2] == X
                    && moves[row][col + 3] == X
                    && moves[row][col + 4] == X) {
                return true;
            }  
            row++;
        }
        row = 0;
        
        while(col < SIZE){
            if (moves[row][col] == X
                    && moves[row + 1][col] == X
                    && moves[row + 2][col] == X
                    && moves[row + 3][col] == X
                    && moves[row + 4][col] == X) {
                return true;
            }
            col++;
        }
        col = 0;
        
        if(moves[row++][col++] == X
                && moves[row++][col++] == X
                && moves[row++][col++] == X
                && moves[row++][col++] == X
                && moves[row++][col++] == X){
            return true;
        }
        row = 0;
        col = 4;
        
        if(moves[row++][col--] == X
                && moves[row++][col--] == X
                && moves[row++][col--] == X
                && moves[row++][col--] == X
                && moves[row++][col--] == X){
            return true;
        }
        row = 0;
        col = 0;
        
        //Check Y
        while(row < SIZE){
            if (moves[row][col] == Y
                    && moves[row][col + 1] == Y
                    && moves[row][col + 2] == Y
                    && moves[row][col + 3] == Y
                    && moves[row][col + 4] == Y) {
                return true;
            }  
            row++;
        }
        row = 0;
        
        while(col < SIZE){
            if (moves[row][col] == Y
                    && moves[row + 1][col] == Y
                    && moves[row + 2][col] == Y
                    && moves[row + 3][col] == Y
                    && moves[row + 4][col] == Y) {
                return true;
            }
            col++;
        }
        col = 0;
        
        if(moves[row++][col++] == Y
                && moves[row++][col++] == Y
                && moves[row++][col++] == Y
                && moves[row++][col++] == Y
                && moves[row++][col++] == Y){
            return true;
        }
        row = 0;
        col = 4;
        
        if(moves[row++][col--] == Y
                && moves[row++][col--] == Y
                && moves[row++][col--] == Y
                && moves[row++][col--] == Y
                && moves[row++][col--] == Y){
            return true;
        }
        row = 0;
        col = 0;
        
        return false;
    }
}
