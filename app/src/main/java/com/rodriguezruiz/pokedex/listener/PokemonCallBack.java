package com.rodriguezruiz.pokedex.listener;

import com.rodriguezruiz.pokedex.data.model.PokemonData;

public interface PokemonCallBack {

    void onSuccess(PokemonData pokemon);

    void onFailure(Exception e);
}