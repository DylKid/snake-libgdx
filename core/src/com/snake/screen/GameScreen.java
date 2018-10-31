package com.snake.screen;

import captureReplay.InputRecorder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.snake.SnakeApplication;
import gridPlayer.StateHandler;
import com.snake.gameobjects.Grid;
import com.snake.gameobjects.Position;
import com.snake.gameobjects.Snake;
import com.snake.gameobjects.SpeedEnum;
import com.snake.score.ScoresManager;

import java.io.IOException;
import java.util.Random;

//import com.mygdx.com.com.snake.score.ScoresManager;
//import com.mygdx.com.com.snake.SnakeApplication;
//import com.mygdx.com.com.snake.gameobjects.Grid;
//import com.mygdx.com.com.snake.gameobjects.Position;
//import com.mygdx.com.com.snake.gameobjects.Snake;
//import com.mygdx.com.com.snake.gameobjects.SpeedEnum;

public class GameScreen extends StateHandler implements Screen {

    private SnakeApplication parent;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private Grid grid;
	private Snake snake;
	private int count;
	private Position currentTreat;
	private boolean treat;
	private boolean test;
	private boolean record;

	private InputRecorder inputRecorder;

    private BitmapFont numberFont = new BitmapFont(Gdx.files.internal("numberFont.fnt"), false);
    private BitmapFont headerFont = new BitmapFont(Gdx.files.internal("headerFont.fnt"), false);

	private int speed;
	private int cellWidth;
	private int cellHeight;
	private int speedMod;
	private String playerName;

	public GameScreen(int cellWidth, int cellHeight, SpeedEnum speedEnum, SnakeApplication snakeApplication, String playerName,
                      boolean test, boolean record){
	    com.badlogic.gdx.InputProcessor currentProcessor = Gdx.input.getInputProcessor();
	    InputMultiplexer multiplexer = new InputMultiplexer();

	    this.record = record;
	    this.test = test;

        //If it's not the test, record the game
        if(this.record) {
            inputRecorder = InputRecorder.getInstance(snakeApplication, true);
            multiplexer.addProcessor(inputRecorder);
        }

        multiplexer.addProcessor((currentProcessor));
        Gdx.input.setInputProcessor(multiplexer);


        this.parent = snakeApplication;
	    this.cellWidth = cellWidth;
	    this.cellHeight = cellHeight;
	    this.playerName = playerName;
	    switch(speedEnum){
            case SLOW:
                speed = 15;
                break;
            case MEDIUM:
                speed = 12;
                break;
            case FAST:
                speed = 8;
                break;
            case LIGHTNING:
                speed = 4;
                break;
        }
    }

	public void create () {
        createWindow(cellWidth,cellHeight);
		batch = new SpriteBatch();
		shapeRenderer=new ShapeRenderer();
		grid = new Grid(Gdx.graphics.getHeight()-40, Gdx.graphics.getWidth(), shapeRenderer);
		snake = new Snake((int) Math.ceil(cellWidth/2), (int) Math.ceil(10/2), grid.getCellWidth(), grid.getCellHeight());
		count = 0;
		if(!test) {
            generateTreat();
        }
	}

	public void createWindow(int cellWidth, int cellHeight){
        Gdx.graphics.setWindowedMode(cellWidth * 20,cellHeight * 20);
        Gdx.graphics.setResizable(false);
    }

	private final int WAIT_TIME = 50;

	private boolean started = false;
	private boolean gameOver = false;

	public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Handle the count down
        if(!started){
            String text = "";
            if(count < WAIT_TIME){
                text = "3";
            } else if(count < (2 * WAIT_TIME)){
                text = "2";
            } else if(count < (3 * WAIT_TIME)){
                text = "1";
            } else {
                started = true;
            }
            renderCenterFont(text);
        }
        //the game actual
        else if(!gameOver){
            collisionHandling();
            if(this.isObserverSet()) {
                this.updateNotify();
            } else {
                snake.handleInput();
            }
            if (count % speed == 0) {
                snake.updateSnake();
            }
            drawEverything();
        } else if(gameOver){
            renderCenterFont("LOSS");
            if(count > 80){
                String scoreString = score + " Treats " + adjective;
                try {
                    ScoresManager.saveScore(playerName, scoreString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                parent.changeScreen(ScreenEnum.HIGHSCORES);
            }
        }
		count++;
	}

    private int score = 0;
	private String adjective = "Eaten";

	public void renderScore(){
        batch.begin();
        final GlyphLayout layout = new GlyphLayout(headerFont, String.format("Treats %s:%s",adjective,score));
        int fontX = 15;
        int fontY = (int) (Gdx.graphics.getHeight() - (layout.height/2));
        //int fontY = 15;
        headerFont.draw(batch, layout, fontX, fontY);
        batch.end();
    }

    public void getNewAdjective(){
        Random rn1 = new Random();
        String[] adjList = new String[]{"Hungrily Eaten", "Fangoriously Devoured", "Gobbled", "Osmosis'd"
                                        ,"Asborbed", "Nibbled", "Yum'd","Got'd","Had","Stolen",
                                        "Robbed", "Snatched", "Taken", "Destroyed", "Ruined", "Consumed",
                                        "Dissolved","Digested"};
        int index = rn1.nextInt(adjList.length);
        adjective = adjList[index];
    }

	public void renderCenterFont(String text){
        batch.begin();
        final GlyphLayout layout = new GlyphLayout(numberFont, text);
        float fontX = Gdx.graphics.getWidth()/2 - (layout.width/2);
        float fontY = Gdx.graphics.getHeight()/2 + (layout.height/2);
        numberFont.draw(batch, layout, fontX, fontY);
        batch.end();
    }

	public void collisionHandling(){
	    if(treat){
	        if(grid.testForCollision(snake.getPositions(),currentTreat)){
	            snake.collectedTreat();
	            if(this.isObserverSet()){
	                this.updateSuccess();
                }
	            score++;
	            if(!test) {
                    generateTreat();
                    getNewAdjective();
                    renderScore();
                }
            }
        }
        if(snake.testSelfCollision()){
	        gameOver = true;
	        count = 0;
//            try {
//                inputRecorder.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

	public void drawEverything(){
        grid.drawGrid();
        grid.drawPositions(snake.getPositions());
        if(treat){
            grid.drawPosition(currentTreat);
        }
        renderScore();
    }

    private void generateTreat(){
        Random rn1 = new Random();
        Random rn2 = new Random();
        int treatX = rn1.nextInt(grid.getCellWidth());
        int treatY = rn2.nextInt(grid.getCellHeight());
        currentTreat = new Position(treatX, treatY);
        treat = true;
    }

    public Snake getSnake(){
	    return this.snake;
    }

    public Grid getGrid(){
	    return this.grid;
    }

    public Position getCurrentTreat() { return this.currentTreat;}

    @Override
    public void show() {
        create();
    }

    @Override
    public void render(float delta) {
        render();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
	public void dispose () {
		batch.dispose();
	}

    @Override
    public Position updatePosition() {
        return snake.getPositions().get(0);
    }

    @Override
    public Position updateCurrentGoal() {
        return currentTreat;
    }

    public void setRecord(){
	    record = true;
    }

    public void setTest(){
	    test = true;
    }
}
