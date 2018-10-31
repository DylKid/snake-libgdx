import captureReplay.FakeInput;
import captureReplay.InputReader;
import captureReplay.InputRunner;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import gridPlayer.GridPlayer;
import com.snake.SnakeApplication;
import com.snake.gameobjects.*;
import com.snake.screen.GameScreen;
import com.snake.screen.ScreenEnum;
import junitx.framework.FileAssert;
import org.junit.*;
import org.lwjgl.openal.AL;

import java.io.File;

public class tests {

    private static LwjglApplicationConfiguration config;
    private static SnakeApplication snakeApplication;
    private static LwjglApplication application;

    @BeforeClass
    public static void setup(){
        config = new LwjglApplicationConfiguration();
        snakeApplication = new SnakeApplication();
        application = new LwjglApplication(snakeApplication, config);
    }

    @Test
    public void snakeFunctionalTest(){
        Snake snake = new Snake(6,6,15,15);
        snake.getPositions();
        //should be 7 6 5
        PositionList expectedListPre = new PositionList();
        expectedListPre.add(new Position(6,6));
        expectedListPre.add(new Position(6,5));
        expectedListPre.add(new Position(6,4));
        Assert.assertEquals(expectedListPre.getPositions(), snake.getPositions());
        //com.snake should be facing up so an update should move each y pos up one
        snake.updateSnake();
        snake.getPositions();
        PositionList expectedListPost = new PositionList();
        expectedListPost.add(new Position(6,7));
        expectedListPost.add(new Position(6,6));
        expectedListPost.add(new Position(6,5));
        Assert.assertEquals(expectedListPost.getPositions(),snake.getPositions());
        //Gdx.input = new InputReader()
        Gdx.app.postRunnable(new Runnable(){
            @Override
            public void run() {
                snakeApplication.changeScreen(ScreenEnum.MENU);
                snakeApplication.moveToPlayScreen(20, 20,
                        SpeedEnum.SLOW,
                        "test_user_"+generate_test_user_number());
            }
        });
        sleep(8000);
        InputRunner inputRunner = new InputRunner();
        InputProcessor originalProcessor = Gdx.input.getInputProcessor();
        Gdx.input = inputRunner;
        Gdx.input.setInputProcessor(originalProcessor);
        inputRunner.setEventType(FakeInput.KEY_DOWN);
        inputRunner.setValue(Input.Keys.RIGHT);
        inputRunner.run();
    }

    @Ignore
    @Test
    public void capture(){
        snakeApplication.setRecording();
        Gdx.app.postRunnable(new Runnable(){
            @Override
            public void run() {
                snakeApplication.changeScreen(ScreenEnum.MENU);
                snakeApplication.moveToPlayScreen(15, 15,
                        SpeedEnum.SLOW,
                        "test_user_"+generate_test_user_number());
            }
        });

        while(true){
            sleep(20);
        }
    }

    @Test
    public void testSnakeReplay() {
        snakeApplication.setTest();
        //Move past the intro screen
        Gdx.app.postRunnable(new Runnable(){
            @Override
            public void run() {
                snakeApplication.changeScreen(ScreenEnum.MENU);
                snakeApplication.moveToPlayScreen(15, 15,
                        SpeedEnum.SLOW,
                        "test_user_"+generate_test_user_number());
            }
        });

        sleep(2000);

        InputReader inputGenerator = new InputReader(snakeApplication);
        Gdx.input = inputGenerator;
        inputGenerator.run();

        sleep(2000);

        FileAssert.assertEquals(new File("stateRecording.txt"), new File("testStateRecording.txt"));
    }

    @Test
    public void testSnakeFinder() {
        Gdx.app.postRunnable(new Runnable(){
            @Override
            public void run() {
                snakeApplication.changeScreen(ScreenEnum.MENU);
                snakeApplication.moveToPlayScreen(15, 15,
                        SpeedEnum.FAST,
                        "test_user_"+generate_test_user_number());
            }
        });

        sleep(2000);
        while(snakeApplication.getCurrentScreen() instanceof GameScreen == false){
            //System.out.println("sleeping");
            sleep(100);
        }

        GameScreen gameScreen = (GameScreen) snakeApplication.getCurrentScreen();
        Grid grid = gameScreen.getGrid();
        GridPlayer gridPlayer = new GridPlayer(grid.getCellHeight(), grid.getCellWidth(), gameScreen.getSnake(), gameScreen);
        gameScreen.setObserver(gridPlayer);
        gridPlayer.blindlySearchUntil(10);
        gridPlayer.waitUntilSuccess();
    }


    @AfterClass
    public static void closeShop(){
        AL.destroy();
        Gdx.app.exit();
    }

    private static String generate_test_user_number(){
        String str1 = String.valueOf(Math.ceil(Math.random() * 100));
        String str2 = String.valueOf(Math.ceil(Math.random() * 100));
        return (str1 + str2);
    }

    private static void sleep(long milis){
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
