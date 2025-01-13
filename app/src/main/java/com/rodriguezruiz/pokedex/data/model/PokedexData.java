package com.rodriguezruiz.pokedex.data.model;

public class PokedexData {
    private int id;
    private String name;
    private String url;
    private boolean isCaptured;

    public PokedexData() {
        this.isCaptured = false;
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

    public int getId() {
        String[] urlPartes = url.split("/");
        return Integer.parseInt(urlPartes[urlPartes.length - 1]);
    }

    public void setId(int number) {
        this.id = number;
    }
}
