package com.rodriguezruiz.pokedex.data.model;

import java.util.ArrayList;

public class PokedexResponse {

    private ArrayList<PokedexData> results;

    public ArrayList<PokedexData> getResults() {
        return results;
    }

    public void setResults(ArrayList<PokedexData> results) {
        this.results = results;
    }
}
