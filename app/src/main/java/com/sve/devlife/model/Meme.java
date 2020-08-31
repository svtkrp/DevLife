package com.sve.devlife.model;

public class Meme {

    private Long id;
    private String description;
    private String gifURL;

    public Meme(Long id, String description, String gifURL) {
        this.id = id;
        this.description = description;
        this.gifURL = gifURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGifURL() {
        return gifURL;
    }

    public void setGifURL(String gifURL) {
        this.gifURL = gifURL;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Meme)) return false;
        Meme meme = (Meme) obj;
        return id.equals(meme.id);
    }
}
