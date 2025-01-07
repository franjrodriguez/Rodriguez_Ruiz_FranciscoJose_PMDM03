package com.rodriguezruiz.pokedex.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rodriguezruiz.pokedex.adapter.PokedexAdapter;
import com.rodriguezruiz.pokedex.data.PokedexListName;
import com.rodriguezruiz.pokedex.databinding.FragmentPokedexBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PokedexFragment} factory method to
 * create an instance of this fragment.
 */
public class PokedexFragment extends Fragment {

    private ArrayList<PokedexListName> listPokemon;
    private FragmentPokedexBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPokedexBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Los Pokemon deberán estar ya cargados previamente nada más cargar el MainActivity y se
        // quedan disponibles en un ArrayList para ser mostrados (listPokemon)

        // Mostramos la lista en el recycledview
//        PokedexAdapter adapter = new PokedexAdapter(listPokemon, getActivity());
//        binding.recyclerviewPokedexList.setLayoutManager(new LinearLayoutManager(getContext()));
//        binding.recyclerviewPokedexList.setAdapter(adapter);

    }
}