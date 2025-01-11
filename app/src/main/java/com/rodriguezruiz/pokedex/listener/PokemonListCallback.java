package com.rodriguezruiz.pokedex.listener;

import com.rodriguezruiz.pokedex.data.model.PokemonData;

import java.util.ArrayList;

public interface PokemonListCallback {
    void onSuccess(ArrayList<PokemonData> pokemons);
    void onFailure(Exception e);
}
