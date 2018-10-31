package com.snake.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.snake.SnakeApplication;
import com.snake.gameobjects.SpeedEnum;
//import com.mygdx.com.com.snake.SnakeApplication;
//import com.mygdx.com.com.snake.gameobjects.SpeedEnum;


public class MenuScreen implements Screen {

    Stage stage;
    SpriteBatch batch;

    private Skin skin;
    private Table table;


    private SnakeApplication parent;

    public MenuScreen(SnakeApplication parent){
        this.parent = parent;
    }


    @Override
    public void show() {
        Gdx.graphics.setWindowedMode(600,400);

        batch = new SpriteBatch();
        stage = new Stage();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        /*try {
            multiplexer.addProcessor(new InputRecorder("snakeRecord.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //multiplexer.addProcessor(InputRecorder.getInstance());
        Gdx.input.setInputProcessor(multiplexer);

//        FileHandle file = Gdx.files.internal("neon/skin/neon-ui.json");

        System.out.println(Gdx.files.getLocalStoragePath());

        System.out.println(Gdx.files.internal("").path());

        skin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));

//        skin = new Skin(Gdx.files.absolute("core/assets/neon/skin/neon-ui.json"));
//        skin = new Skin(Gdx.files.absolute("assets/neon/skin/neon-ui.json"));

        table = new Table();
        table.setFillParent(true);
        table.setSkin(skin);
        stage.addActor(table);

        Label title = new Label("SNAKE", skin);
        title.setFontScale(1.3f);
        table.add(title);
        table.row();

        final SelectBox cellWidthSelect = createSelectBox(new String[]{"15","20","25","30"}, "Cell Width:");
        final SelectBox cellHeightSelect = createSelectBox(new String[]{"15","20","25","30"}, "Cell Height:");
        final SelectBox speedSelect = createSelectBox(new String[]{"SLOW","MEDIUM","FAST","LIGHTNING"}, "Speed:");

        Label nameFieldLabel = new Label("Player Name:", skin);
        final TextField nameField = new TextField("",skin);
        nameField.setMaxLength(20);
        table.add(nameFieldLabel);
        table.add(nameField).colspan(2);
        table.row();

        TextButton playButton = new TextButton("PLAY", skin);
        table.add(playButton);

        playButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                int cellWidth = Integer.parseInt(cellWidthSelect.getSelected().toString());
                int cellHeight = Integer.parseInt(cellHeightSelect.getSelected().toString());
                SpeedEnum speedEnum = SpeedEnum.valueOf(speedSelect.getSelected().toString());
                parent.moveToPlayScreen(cellWidth, cellHeight, speedEnum, nameField.getText());

                //parent.moveToPlayScreen(cellWidthSelect.getSelected().toString())
            }
        });

        table.row();
    }

    public SelectBox createSelectBox(String[] values, String labelText){
        SelectBox selectBox = new SelectBox(skin);
        selectBox.setItems(values);
        Label label = new Label(labelText, skin);
        table.add(label);
        table.add(selectBox);
        table.row();
        return selectBox;
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
        stage.dispose();
        skin.dispose();
    }


}
