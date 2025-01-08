package com.rodriguezruiz.pokedex.ui.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rodriguezruiz.pokedex.adapter.PokedexAdapter;
import com.rodriguezruiz.pokedex.models.Pokemon;
import com.rodriguezruiz.pokedex.databinding.FragmentPokedexBinding;

import java.util.ArrayList;

public class PokedexFragment extends Fragment {

    private static ArrayList<Pokemon> pokedex;
    private FragmentPokedexBinding binding;
    private static RecyclerView recyclerView;
    private static PokedexAdapter adapter;
    private final ColorDrawable background = new ColorDrawable(Color.RED);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPokedexBinding.inflate(inflater, container, false);
        pokedex = new ArrayList<>();
        // Recoge parametro ArrayList de la Lista de Pokedex (cargada en MainActivity)
        recyclerView = binding.recyclerviewPokedexList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PokedexAdapter(pokedex, getActivity());
        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}