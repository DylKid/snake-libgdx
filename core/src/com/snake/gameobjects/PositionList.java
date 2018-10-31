package com.snake.gameobjects;

//import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

public class PositionList{

    private List<Position> positionList;

    public PositionList(){
        this.positionList = new ArrayList<Position>();
    }

    public PositionList(List<Position> positions){
        this.positionList = new ArrayList<Position>();
        for(Position position : positions){
            positionList.add(position);
        }
    }

    public void addPosition(Position position){
        positionList.add(position);
    }


    public void add(Position position){
        positionList.add(position);
    }

    public Position get(int i){
        return positionList.get(i);
    }

    public int size(){
        return positionList.size();
    }

    public List<Position> getPositions(){
        return positionList;
    }

    @Override
    public String toString(){
        String ret = "[";
        for(Position position : positionList){
            ret += position.getX();
        }
        ret += "]";
        return ret;
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof PositionList){
            List<Position> otherPositions = ((PositionList) other).getPositions();
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


}
