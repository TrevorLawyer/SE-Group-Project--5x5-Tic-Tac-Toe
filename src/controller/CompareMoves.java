package controller;

import java.util.Comparator;

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
