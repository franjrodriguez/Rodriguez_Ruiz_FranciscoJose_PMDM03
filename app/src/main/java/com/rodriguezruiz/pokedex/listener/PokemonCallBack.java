package com.rodriguezruiz.pokedex.listener;

import com.rodriguezruiz.pokedex.data.model.PokemonResponse;

public interface PokemonCallBack {

    void onSuccess(PokemonResponse pokemonGetted);

    void onFailure(Exception e);
}