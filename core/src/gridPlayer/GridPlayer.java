package gridPlayer;

import com.snake.SearchEnum;
import com.snake.gameobjects.Position;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GridPlayer implements StateObserver {

    private int height;
    private int width;
    private GridHandler gridHandler;
    private StateHandler stateHandler;
    private SearchEnum search;
    private int count;
    private int successes;
    private int neededSucceses;
    private Graph g;
    private Position currentGoal;
    private int currX;
    private int currY;

    public GridPlayer(int height, int width, GridHandler gridHandler, StateHandler stateHandler){
        this.height = height;
        this.width = width;
        this.gridHandler = gridHandler;
        this.stateHandler = stateHandler;
        count = 1;
        g = new Graph();
    }

    public void blindlySearchUntil(int successes) {
        this.neededSucceses = successes;
        search = SearchEnum.BLIND;
    }

    public List<Position> getPathTo(int x, int y){
        int xCount = currX;
        int yCount = currY;

        List<Position> l = new ArrayList();
        while(xCount != x){
            xCount = ((xCount + 1) % width);
            l.add(new Position(xCount, yCount));
        }
        while(yCount != y){
            yCount = ((yCount + 1) % height);
            l.add(new Position(xCount, yCount));
        }
        return l;
    }

    //Create graph with full access in the grid
    public void createGraph(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                List l = new ArrayList();
                Position newPos = new Position(i,j);
                g.addPosition(newPos);
                if(i > 0){ l.add(new Position(i-1, j));}
                if(j > 0){ l.add(new Position(i, j-1));}
                if(i < (height-1)){ l.add(new Position(i+1, j));}
                if(j < (width-1)){ l.add(new Position(i, j+1));}
                g.addAllAdjPosition(newPos, l);
            }
        }
    }


    @Override
    public void runNotify() {
        //System.out.println("Run notify");
        //List<Position> path = getPathTo(currentGoal.getX(), currentGoal.getY());
        switch(search){
            case BLIND:
                blindSearch();
            case BFS:
                break;
            case DIJKSTRA:
                break;
            case ASTAR:
                break;
        }

    }

    @Override
    public void notifySuccess() {
        successes++;
    }

    private void blindSearch(){
        currentGoal = stateHandler.updateCurrentGoal();
        if(currentGoal != null) {
            moveToward(currentGoal);
            currX = stateHandler.updatePosition().getX();
            currY = stateHandler.updatePosition().getY();
        }
    }

    private void moveToward(Position moveTo){
        if(moveTo.getX() > currX) {
            gridHandler.moveRight();
        } else if(moveTo.getX() < currX){
            gridHandler.moveLeft();
        } else if(moveTo.getY() > currY){
            gridHandler.moveUp();
        } else if(moveTo.getY() < currY){
            gridHandler.moveDown();
        }
    }

    private void goNuts(){
                switch(count%4){
            case 0:
                System.out.println("LEFT");
                gridHandler.moveLeft();
                break;
            case 1:
                System.out.println("UP");
                gridHandler.moveUp();
                break;
            case 2:
                System.out.println("RIGHT");
                gridHandler.moveRight();
                break;
            case 3:
                System.out.println("DOWN");
                gridHandler.moveDown();
                break;
            default:
                System.out.println("Do nothing");
                break;
        }
        count += (int) (Math.random()*4);
    }

    public void waitUntilSuccess(){
        while(true){
            sleep(550);
            if (successes >= neededSucceses) {
                break;
            }
        }
        assertTrue(true);
    }

    private static void sleep(long milis){
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
