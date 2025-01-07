package com.rodriguezruiz.pokedex.models;

import com.rodriguezruiz.pokedex.data.PokedexListName;

import java.util.ArrayList;

public class PokedexResponse {

    private ArrayList<PokedexListName> results;

    public ArrayList<PokedexListName> getResults() {
        return results;
    }

    public void setResults(ArrayList<PokedexListName> results) {
        this.results = results;
    }
}
