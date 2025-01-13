package com.rodriguezruiz.pokedex.ui.fragment;

import static com.rodriguezruiz.pokedex.utils.Constants.SETTING_DELETE;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rodriguezruiz.pokedex.databinding.FragmentCapturedBinding;
import com.rodriguezruiz.pokedex.ui.adapter.CapturedPokemonAdapter;
import com.rodriguezruiz.pokedex.ui.adapter.PokemonViewHolder;
import com.rodriguezruiz.pokedex.viewmodel.PokemonViewModel;

public class CapturedFragment extends Fragment {

    private static RecyclerView recyclerView;
    private static CapturedPokemonAdapter adapter;
    private FragmentCapturedBinding binding;
    private PokemonViewModel pokemonViewModel;

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
        boolean isEnabledDeletePokemon = sharedPreferences.getBoolean(SETTING_DELETE, false);

        // Recoge el arraylist de la lista capturedpokemon (Firestore) cargada en MainActivity
        recyclerView = binding.recyclerviewPokemonList;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new CapturedPokemonAdapter();
        recyclerView.setAdapter(adapter);

        // Almacena la lista cargada en el ViewModel
        pokemonViewModel = new ViewModelProvider(requireActivity()).get(PokemonViewModel.class);
        pokemonViewModel.getPokemonData().observe(getViewLifecycleOwner(), capturedListPokemons -> {
            adapter.updateList(capturedListPokemons);
        });
    }
}