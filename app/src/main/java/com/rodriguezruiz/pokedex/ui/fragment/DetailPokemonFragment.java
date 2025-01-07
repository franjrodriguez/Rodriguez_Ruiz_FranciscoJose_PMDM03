package com.rodriguezruiz.pokedex.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rodriguezruiz.pokedex.R;
import com.rodriguezruiz.pokedex.data.PokedexListName;
import com.rodriguezruiz.pokedex.databinding.FragmentCapturedBinding;
import com.rodriguezruiz.pokedex.databinding.FragmentDetailPokemonBinding;

import java.util.ArrayList;

public class DetailPokemonFragment extends Fragment {

    private ArrayList<PokedexListName> listPokemon;
    private FragmentDetailPokemonBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailPokemonBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}