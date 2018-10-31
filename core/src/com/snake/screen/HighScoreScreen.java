package com.snake.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.snake.SnakeApplication;
import com.snake.score.Score;
import com.snake.score.ScoresManager;

import java.io.IOException;
import java.util.ArrayList;

//import com.mygdx.com.com.snake.score.Score;
//import com.mygdx.com.com.snake.score.ScoresManager;
//import com.mygdx.com.com.snake.SnakeApplication;

public class HighScoreScreen implements Screen {

    public HighScoreScreen(SnakeApplication snakeApplication){
        this.snakeApplication = snakeApplication;
    }

    private SnakeApplication snakeApplication;
    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;
    private Table table;

    @Override
    public void show() {
        Gdx.graphics.setWindowedMode(600,400);

        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));

//        skin = new Skin(Gdx.files.absolute("core/assets/neon/skin/neon-ui.json"));

//        skin = new Skin(Gdx.files.absolute("assets/neon/skin/neon-ui.json"));

        table = new Table();
        table.setFillParent(true);
        table.setSkin(skin);
        stage.addActor(table);

        Label title = new Label("HIGHSCORES" , skin);
        title.setFontScale(1.3f);
        table.add(title);
        table.row();

        List<Score> scoreList = new List(skin);
        Array<Score> scoreArray = new Array<Score>();
        java.util.List<Score> origScoreList = new ArrayList<Score>();
        try {
            origScoreList = ScoresManager.loadScores();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < origScoreList.size(); i++){
            scoreArray.add(origScoreList.get(i));
        }

        scoreList.setItems(scoreArray);
        table.add(scoreList);

        for(int i = 0; i < 5; i++){
            table.row();
        }

        TextButton button = new TextButton("Back to Menu", skin);

        button.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                snakeApplication.changeScreen(ScreenEnum.MENU);
            }
        });

        table.add(button);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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
    public void dispose() {

    }
}
