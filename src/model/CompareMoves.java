/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Comparator;

/**
 *
 * @author TJaeckel
 */
public class CompareMoves implements Comparator<MoveNode>{

    @Override
    public int compare(MoveNode o1, MoveNode o2) {
        if(o1.getScore() > o2.getScore()){
            return -1;
        }
        if(o1.getScore() == o2.getScore()){
            return 0;
        }
        return 1;
    }
    
}
