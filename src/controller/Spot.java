package controller;

public class Spot {
    private boolean taken;
    private boolean isMe;
    private int index;
    private boolean isX;
    
    public Spot(int index){
        taken = false;
        isMe = false;
        this.index = index;
    }
    
    public Spot(Spot spot){
        this.taken = spot.isTaken();
        this.isMe = spot.isMe();
        this.index = spot.getIndex();
        this.isX = spot.isX;
    }
    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setIsMe(boolean isMe) {
        this.isMe = isMe;
    }

    public int getIndex() {
        return index;
    }

    public boolean isX() {
        return isX;
    }

    public void setIsX(boolean isX) {
        this.isX = isX;
    }
    
    
    

}
