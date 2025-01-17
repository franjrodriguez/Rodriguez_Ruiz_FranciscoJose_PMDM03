package com.rodriguezruiz.pokedex.data.model;

public class PokedexData {
    private String id;
    private String name;
    private String url;
    private boolean isCaptured;

    public PokedexData() {}

    public void setId(String id) {
        this.id = id;
    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public void setCaptured(boolean captured) {
        isCaptured = captured;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return this.id;
    }
}
