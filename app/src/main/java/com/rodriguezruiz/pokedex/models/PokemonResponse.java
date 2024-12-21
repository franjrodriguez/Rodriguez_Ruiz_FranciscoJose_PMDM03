package com.rodriguezruiz.pokedex.models;

import com.rodriguezruiz.pokedex.data.PokemonListName;

import java.util.ArrayList;

public class PokemonResponse {

    private ArrayList<PokemonListName> results;

    public ArrayList<PokemonListName> getResults() {
        return results;
    }

    public void setResults(ArrayList<PokemonListName> results) {
        this.results = results;
    }
}
