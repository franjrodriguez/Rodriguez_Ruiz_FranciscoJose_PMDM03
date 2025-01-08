package com.rodriguezruiz.pokedex.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rodriguezruiz.pokedex.models.Pokedex;
import com.rodriguezruiz.pokedex.databinding.FragmentCapturedBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CapturedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CapturedFragment extends Fragment {

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

        // La lista de pokemon debe estar ya cargada en memoria.

    }
}