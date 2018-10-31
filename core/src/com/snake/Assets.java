package com.snake;

import java.io.IOException;
import java.util.Properties;

public class Assets {

    private static Assets instance;
    private static String neon_skin;

    private Assets() throws IOException {
        //ResourceBundle rb = ResourceBundle.getBundle("core/assets");
        Properties appProps = new Properties();
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println(rootPath);
        //appProps.load(new FileInputStream("../../../assets.properties"));
        //neon_skin = rb.getString("assets.neon-skin");
    }

    public static Assets getInstance(){
        if(instance != null){
            return instance;
        } else {
            try {
                instance = new Assets();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public String neonSkinPath(){
        return neon_skin;
    }

}
