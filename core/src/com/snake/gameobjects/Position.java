package com.snake.gameobjects;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString(){
        return String.format("PosX:%s PosY:%s\n",x,y);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Position){
            Position otherPosition = (Position) o;
            if(this.x == otherPosition.getX() && this.y == otherPosition.getY()){
                return true;
            }
        }
        return false;
    }
}
