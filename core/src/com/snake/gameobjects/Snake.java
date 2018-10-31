package com.snake.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import gridPlayer.GridHandler;

import java.util.ArrayList;
import java.util.List;

import static com.snake.gameobjects.DirectionEnum.*;

//import com.mygdx.com.com.snake.gameobjects.DirectionEnum;
//import com.mygdx.com.com.snake.gameobjects.Position;
//import static com.mygdx.com.com.snake.gameobjects.DirectionEnum.*;


public class Snake implements GridHandler {

    private int size;

    private int gridWidth;
    private int gridHeight;

    private PositionList positions;

    private DirectionEnum direction;
    private DirectionEnum tailDirection;

    public Snake(int startX, int startY, int gridWidth, int gridHeight){
        this.size = 3;
        positions = new PositionList();
        positions.add(new Position(startX, startY));
        positions.add(new Position(startX, startY-1));
        positions.add(new Position(startX, startY-2));
        direction = UP;
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
    }

    public void updateSnake(){
        ArrayList<Position> newPositions = new ArrayList<Position>();
        int headX = positions.get(0).getX();
        int headY = positions.get(0).getY();
        switch(direction){
            case UP:
                if(headY == gridHeight-1){
                    headY = 0;
                } else {
                    headY++;
                }
                break;
            case DOWN:
                if(headY == 0){
                    headY = gridHeight-1;
                } else {
                    headY--;
                }
                break;
            case LEFT:
                if(headX == 0){
                    headX = gridWidth-1;
                    //direction = RIGHT;
                } else {
                    headX--;
                }
                break;
            case RIGHT:
                if(headX == gridWidth-1){
                    headX = 0;
                    //direction = LEFT;
                } else {
                    headX++;
                }
                break;
        }
        newPositions.add(new Position(headX,headY));
        for(int i = 1; i < positions.size(); i++){
            newPositions.add(new Position(positions.get(i-1).getX(),positions.get(i-1).getY()));
        }
        updateTailDirection(newPositions);
        this.positions = new PositionList(newPositions);
        //System.out.println(positions);
    }

    public void updateTailDirection(List<Position> newPositions){
        int oldTailX = positions.get((positions.size()-1)).getX();
        int oldTailY = positions.get((positions.size()-1)).getY();
        int newTailX = newPositions.get(newPositions.size()-1).getX();
        int newTailY = newPositions.get(newPositions.size()-1).getY();
        int xDiff = newTailX - oldTailX;
        int yDiff = newTailY - oldTailY;
        //moving up
        if(yDiff == 1){
            //System.out.println("Tail up");
            tailDirection = UP;
            //moving down
        } else if(yDiff == -1){
            //System.out.println("Tail down");
            tailDirection = DOWN;
            //moving right
        } else if(xDiff == 1){
            //System.out.println("Tail right");
            tailDirection = RIGHT;
            //moving left
        } else if(xDiff == -1){
            //System.out.println("Tail left");
            tailDirection = LEFT;
        }
    }

    //increase size if collected treat adding onto the end of the tail
    public void collectedTreat(){
        int currentTailX = positions.get(positions.size()-1).getX();
        int currentTailY = positions.get(positions.size()-1).getY();
        switch(tailDirection){
            case UP:
                positions.add(new Position(currentTailX, currentTailY-1));
                break;
            case DOWN:
                positions.add(new Position(currentTailX, currentTailY+1));
                break;
            case LEFT:
                positions.add(new Position(currentTailX+1, currentTailY));
                break;
            case RIGHT:
                positions.add(new Position(currentTailX-1, currentTailY));
                break;
        }
        size++;
    }

    public void handleInput(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            if(direction != RIGHT) direction = LEFT;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            if(direction != LEFT) direction = RIGHT;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            if(direction != DOWN) direction = UP;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            if(direction != UP) direction = DOWN;
        }
    }

    public boolean testSelfCollision(){
        for(int i = 0; i < positions.size(); i++){
            for(int j = 0; j < positions.size(); j++){
                if(i == j){
                    continue;
                }
                if(positions.get(i).equals(positions.get(j))){
                    System.out.println("SELF COLLISION");
                    System.out.println(positions);
                    return true;
                }
            }
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Position> getPositions() {
        return positions.getPositions();
    }

    /*public void setPositions(List<Position> positions) {
        this.positions = positions;
    }*/

    @Override
    public boolean equals(Object other){
        if(other instanceof Snake){
            List<Position> otherPositions = ((Snake) other).getPositions();
            List<Position> thisPositions = this.getPositions();
            if(otherPositions.size() != thisPositions.size()){
                return false;
            } else {
                for(int i = 0; i < otherPositions.size(); i++){
                    if(!otherPositions.get(i).equals(thisPositions.get(i))){
                        return false;
                    }
                }
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public void moveUp() {
        if(direction != DOWN) direction = UP;
    }

    @Override
    public void moveDown() {
        if(direction != UP) direction = DOWN;
    }

    @Override
    public void moveLeft() {
        if(direction != RIGHT) direction = LEFT;
    }

    @Override
    public void moveRight() {
        if(direction != LEFT) direction = RIGHT;
    }
}
