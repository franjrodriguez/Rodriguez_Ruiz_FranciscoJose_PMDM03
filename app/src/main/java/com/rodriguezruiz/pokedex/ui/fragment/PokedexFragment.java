package com.rodriguezruiz.pokedex.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rodriguezruiz.pokedex.ui.adapter.PokedexAdapter;
import com.rodriguezruiz.pokedex.databinding.FragmentPokedexBinding;
import com.rodriguezruiz.pokedex.viewmodel.PokedexViewModel;

public class PokedexFragment extends Fragment {

   // private static ArrayList<PokedexData> pokedex;
    private static RecyclerView recyclerView;
    private static PokedexAdapter adapter;
    private FragmentPokedexBinding binding;
    private PokedexViewModel pokedexViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPokedexBinding.inflate(inflater, container, false);

        // Recoge parametro ArrayList de la Lista de Pokedex (cargada en MainActivity)
        recyclerView = binding.recyclerviewPokedexList;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PokedexAdapter();
        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pokedexViewModel = new ViewModelProvider(requireActivity()).get(PokedexViewModel.class);
        pokedexViewModel.getPokedexData().observe(getViewLifecycleOwner(), pokedex -> {
            adapter.updateList(pokedex);  // Implementar en el adaptador
        });
    }
}