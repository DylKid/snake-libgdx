package com.snake.score;


import com.badlogic.gdx.Gdx;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//import com.fasterxml.jackson.databind.ObjectMapper;

public class ScoresManager {

    //loads top 10 scores
    public static List<Score> loadScores() throws IOException {
        ScoreList scoreList = loadAllScores();
        int count = 0;
        List<Score> top10Scores = new ArrayList<Score>();
        for(Score score : scoreList.getScoreList()){
            count++;
            if(count > 10) break;
            top10Scores.add(score);
        }
        return top10Scores;
    }

    private static ScoreList loadAllScores() throws IOException {
        //String s = String.valueOf(Gdx.files.internal("scores.json"));
        //System.out.println(s);
        InputStream is = Gdx.files.local("scores.json").read();
//        InputStream is = new FileInputStream(String.valueOf(Gdx.files.absolute("core/assets/scores.json")));
    //    InputStream is = new FileInputStream(String.valueOf(Gdx.files.absolute("assets/scores.json")));
        String jsonText = IOUtils.toString(is, "UTF-8");
        System.out.println(jsonText);
        ObjectMapper mapper = new ObjectMapper();
        ScoreList scoreList = mapper.readValue(jsonText, ScoreList.class);
        System.out.println(scoreList);
        Collections.sort(scoreList.getScoreList());
        System.out.println("Sorted list\n " + scoreList.getScoreList());
        return scoreList;
    }

    public static void saveScore(String playerName, String scoreString) throws IOException {
        Score saveScore = new Score();
        saveScore.setPlayerName(playerName);
        saveScore.setScore(scoreString);

        ObjectMapper mapper = new ObjectMapper();

        ScoreList scoreList = loadAllScores();

        List<Score> scoreArrayList = scoreList.getScoreList();
        scoreArrayList.add(saveScore);

        String jsonString = mapper.writeValueAsString(scoreList);

        //BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(Gdx.files.internal("../android/assets/scores.json"))));

        BufferedWriter writer = new BufferedWriter(Gdx.files.local("scores.json").writer(false));

        writer.write(jsonString);
        writer.close();

    }

}
