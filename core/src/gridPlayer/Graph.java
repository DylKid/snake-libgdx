package gridPlayer;

import com.snake.gameobjects.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    private Map<Position, List<Position>> adj;

    public Graph(){
        adj = new HashMap();
    }

    public void addPosition(Position position){
        if(adj.containsKey(position)){

        } else {
            adj.put(position, new ArrayList());
        }
    }

    public void addAllAdjPosition(Position pos1, List<Position> adjPosList){
        adj.get(pos1).addAll(adjPosList);
    }

    public void addAdjPosition(Position pos1, Position adjPos){
        adj.get(pos1).add(adjPos);
    }



    /*public List<Position> bfs(Position source, Position destination){
        Queue<Position> Q = new PriorityQueue();
        Q.add(source);
        while(!Q.isEmpty()){
            Position curr = Q.poll();

            for(Position pos : adj.get(curr)){

            }
        }
    }*/

}
