package com.rodriguezruiz.pokedex.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rodriguezruiz.pokedex.data.model.PokedexData;
import java.util.ArrayList;

public class PokedexViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<PokedexData>> pokedexData = new MutableLiveData<>();

    public LiveData<ArrayList<PokedexData>> getPokedexData() {
        return pokedexData;
    }

    public void setPokedexData(ArrayList<PokedexData> data) {
        pokedexData.setValue(data);
    }
}
