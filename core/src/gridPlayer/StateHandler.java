package gridPlayer;


import com.snake.gameobjects.Position;

import java.util.concurrent.Callable;

public abstract class StateHandler {

    private StateObserver observer;

    public void setObserver(StateObserver observer){
        System.out.println("SETTING OBSERVER");
        this.observer = observer;
    }

    public boolean isObserverSet(){
        if(observer!=null){
            return true;
        } else {
            return false;
        }
    }

    public abstract Position updatePosition();

    public abstract Position updateCurrentGoal();

    public void updateNotify(){
        observer.runNotify();
    }

    public void updateSuccess() {observer.notifySuccess(); }

    public <T> T myMethod(Callable<T> myFunc) throws Exception {
       return myFunc.call();
    }

}
