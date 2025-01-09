package com.rodriguezruiz.pokedex.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rodriguezruiz.pokedex.models.Pokedex;
import com.rodriguezruiz.pokedex.databinding.FragmentCapturedBinding;

import java.util.ArrayList;

public class CapturedFragment extends Fragment {
    private final static String SETTING_DELETE = "delete_pokemon_preferences";

    private ArrayList<Pokedex> listPokemon;
    private FragmentCapturedBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCapturedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Carga del SharedPreferences el estado del permiso para borrar Pokemon capturados
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean isEnabledDeletePokemon = sharedPreferences.getBoolean("delete_pokemon_preferences", false);

        // La lista de pokemon debe estar ya cargada en memoria.

    }
}