package com.rodriguezruiz.pokedex.models;

import java.util.ArrayList;

public class PokedexResponse {

    private ArrayList<PokemonResponse> results;

    public ArrayList<PokemonResponse> getResults() {
        return results;
    }

    public void setResults(ArrayList<PokemonResponse> results) {
        this.results = results;
    }
}
