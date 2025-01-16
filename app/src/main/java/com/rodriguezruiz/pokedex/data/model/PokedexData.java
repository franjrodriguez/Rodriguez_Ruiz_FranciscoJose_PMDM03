package com.rodriguezruiz.pokedex.data.model;

public class PokedexData {
    private String id;
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
        // Toma la ultima parte de la URL para coger el numero del Pokemon en la lista Pokédex
        String[] urlPartes = this.url.split("/");
        this.id = urlPartes[urlPartes.length - 1];
    }

    public String getId() {
        return this.id;
    }
}
