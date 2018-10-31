package com.snake;

import captureReplay.Recorded;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.snake.gameobjects.PositionList;
import com.snake.gameobjects.SpeedEnum;
import com.snake.screen.GameScreen;
import com.snake.screen.HighScoreScreen;
import com.snake.screen.MenuScreen;
import com.snake.screen.ScreenEnum;
//import com.mygdx.com.com.snake.gameobjects.SpeedEnum;
//import com.mygdx.com.com.snake.screen.GameScreen;
//import com.mygdx.com.com.snake.screen.HighScoreScreen;
//import com.mygdx.com.com.snake.screen.MenuScreen;
//import com.mygdx.com.com.snake.screen.ScreenEnum;

public class SnakeApplication extends Game implements Recorded {

    private ScreenEnum currentScreen;

    public SnakeApplication()
    {
        super();
        //Gdx.input = new Recorder();

    }

    private boolean record;
    private boolean test;

    @Override
    public void create() {
       changeScreen(ScreenEnum.MENU);
    }

    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    private HighScoreScreen highScoreScreen;

    public void moveToPlayScreen(int cellWidth, int cellHeight, SpeedEnum speed, String playerName){
        gameScreen = new GameScreen(cellWidth, cellHeight, speed,this, playerName, test, record);
        this.setScreen(gameScreen);
        this.currentScreen = ScreenEnum.GAMESCREEN;
    }

    public void changeScreen(ScreenEnum screen){
        this.currentScreen = screen;
        switch(screen){
            case MENU:
                if(menuScreen == null){
                    menuScreen = new MenuScreen(this);
                }
                this.setScreen(menuScreen);
                break;
            case GAMESCREEN:
                if(gameScreen == null){
                    gameScreen = new GameScreen(20,20,SpeedEnum.FAST,this, "", test, record);
                }
                this.setScreen(gameScreen);
                break;
            case HIGHSCORES:
                if(highScoreScreen == null){
                    highScoreScreen = new HighScoreScreen(this);
                }
                this.setScreen(highScoreScreen);
                break;
        }
    }

    public Screen getCurrentScreen(){
        switch(currentScreen){
            case MENU:
                return menuScreen;
            case GAMESCREEN:
                return gameScreen;
            case HIGHSCORES:
                return highScoreScreen;
            case ENDSCREEN:
                return null;
        }
        return null;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }


    @Override
    public Object getState() throws NoSuchFieldException, IllegalAccessException {
        return new PositionList(gameScreen.getSnake().getPositions());
    }

    @Override
    public void setRecording() {
        record = true;
        test = true;
    }

    public void setTest(){
        test = true;
    }
}
