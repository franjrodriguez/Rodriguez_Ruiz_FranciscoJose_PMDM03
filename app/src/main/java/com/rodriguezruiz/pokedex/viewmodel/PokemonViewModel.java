package com.rodriguezruiz.pokedex.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rodriguezruiz.pokedex.data.model.PokemonData;

import java.util.ArrayList;

public class PokemonViewModel  extends ViewModel {
    private final MutableLiveData<ArrayList<PokemonData>> pokemonData = new MutableLiveData<>();

    public LiveData<ArrayList<PokemonData>> getPokedexData() {
        return pokemonData;
    }

    public void setPokedexData(ArrayList<PokemonData> data) {
        pokemonData.setValue(data);
    }
}
