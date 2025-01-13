package com.rodriguezruiz.pokedex.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rodriguezruiz.pokedex.data.model.PokemonData;
import java.util.ArrayList;

public class PokemonViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<PokemonData>> capturedListPokemons = new MutableLiveData<>();

    public LiveData<ArrayList<PokemonData>> getPokemonData() {
        return capturedListPokemons;
    }

    public void setPokemonData(ArrayList<PokemonData> data) {
        capturedListPokemons.setValue(data);
    }
}
