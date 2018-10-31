package com.snake.gameobjects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import java.util.List;

public class Grid {

    private int height;
    private int width;

    //The number of cells long
    private int cellHeight;
    //The number of cells wide
    private int cellWidth;

    private ShapeRenderer shapeRenderer;

    //The length of one of the square cell's sides.
    private final int CELL_SIZE = 20;

    public Grid(int height, int width, ShapeRenderer shapeRenderer){
        this.height = height;
        this.width = width;
        this.shapeRenderer = shapeRenderer;
        cellHeight = height/CELL_SIZE;
        cellWidth = width/CELL_SIZE;
    }

    public void drawGrid(){
        shapeRenderer.begin(ShapeType.Line);
        shapeRenderer.setColor(255,255,255,1);
        for(int i = 0; i < cellWidth; i++){
            shapeRenderer.line(i*CELL_SIZE,0,i*CELL_SIZE, height);
        }
        for(int i = 0; i < cellHeight+1; i++){
            shapeRenderer.line(0, i*CELL_SIZE, width, i*CELL_SIZE);
        }
        shapeRenderer.end();
    }

    public void drawPosition(Position position){
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(0,255,0,1);
        float x = (position.getX()*CELL_SIZE);
        float y = (position.getY()*CELL_SIZE);
        shapeRenderer.rect(x, y , CELL_SIZE-0.5f, CELL_SIZE-0.5f);
        shapeRenderer.end();
    }

    public void drawPositions(List<Position> positions){
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(255,0,0,1);
        for(Position position : positions){
            float x = (position.getX()*CELL_SIZE);
            float y = (position.getY()*CELL_SIZE+1.2f);
            shapeRenderer.rect(x, y , CELL_SIZE-1f, CELL_SIZE-1.5f);
        }
        shapeRenderer.end();
    }

    //Test for conflict BEFORE the new screen is drawn but AFTER the values are updated
    public boolean testForCollision(List<Position> snakePositions, Position otherPosition){
        for(Position snakePosition : snakePositions){
            if(snakePosition.equals(otherPosition)){
                return true;
            }
        }
        return false;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public int getCellWidth() {
        return cellWidth;
    }

}
