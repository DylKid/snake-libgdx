package com.snake.score;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class Score implements Comparable<Score>{

    public Score(){

    }

    @JsonProperty("player")
    private String playerName;
    @JsonProperty("score")
    private String score;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public int compareTo(Score otherScore) {

        int myScore = getScoreFromString(this.score);
        int theirScore = getScoreFromString(otherScore.getScore());
        System.out.printf("Myscore:%s, theirScore:%s",myScore,theirScore);
        System.out.println("Comparing " + this + " to " + otherScore + " returning " + (myScore-theirScore));
        return theirScore - myScore;
    }

    private int getScoreFromString(String scoreString){
        InputStream stream = new ByteArrayInputStream(scoreString.getBytes());
        Scanner scanner = new Scanner(stream);
        return scanner.nextInt();
    }

    @Override
    public String toString() {
        return String.format("%s ---- %s", playerName, score);
    }
}
