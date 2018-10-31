package com.snake.score;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ScoreList {

    public ScoreList(){

    }

    @JsonProperty("scores")
    public List<Score> scoreList;

    public List<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Score> scoreList) {
        this.scoreList = scoreList;
    }
}
