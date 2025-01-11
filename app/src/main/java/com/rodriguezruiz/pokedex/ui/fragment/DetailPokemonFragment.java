package com.rodriguezruiz.pokedex.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rodriguezruiz.pokedex.databinding.FragmentDetailPokemonBinding;
import com.rodriguezruiz.pokedex.data.model.PokedexData;

import java.util.ArrayList;

public class DetailPokemonFragment extends Fragment {

    private ArrayList<PokedexData> listPokemon;
    private FragmentDetailPokemonBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailPokemonBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}