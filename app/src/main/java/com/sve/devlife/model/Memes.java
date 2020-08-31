package com.sve.devlife.model;

import java.util.List;

public class Memes {

    private List<Meme> result;

    public Memes(List<Meme> result) {
        this.result = result;
    }

    public List<Meme> getResult() {
        return result;
    }

    public void setResult(List<Meme> result) {
        this.result = result;
    }
}
