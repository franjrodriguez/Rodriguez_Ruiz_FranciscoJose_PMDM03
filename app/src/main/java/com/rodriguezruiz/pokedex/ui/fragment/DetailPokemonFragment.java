package com.rodriguezruiz.pokedex.ui.fragment;

import static com.rodriguezruiz.pokedex.utils.Constants.TYPE_SPRITE;
import static com.rodriguezruiz.pokedex.utils.Constants.URL_SPRITE;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rodriguezruiz.pokedex.R;
import com.rodriguezruiz.pokedex.data.model.PokemonData;
import com.rodriguezruiz.pokedex.databinding.FragmentDetailPokemonBinding;
import com.rodriguezruiz.pokedex.data.model.PokedexData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailPokemonFragment extends Fragment {

    private FragmentDetailPokemonBinding binding;
    private static final String ARG_POKEMON = "pokemon";
    private PokemonData pokemonData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pokemonData = getArguments().getParcelable(ARG_POKEMON);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailPokemonBinding.inflate(inflater, container, false);

        // Titulo toolbar
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.detalle_pokemon);
        }

        // Muestra los datos del Pokemon
        if (pokemonData != null) {
            displayPokemonData();
        }

        return binding.getRoot();
    }

    private void displayPokemonData() {
        binding.pokedexNumber.setText(pokemonData.getId());
        binding.pokemonHeight.setText(String.valueOf(pokemonData.getHeight()));
        binding.pokemonWeight.setText(String.valueOf(pokemonData.getWeight()));
        binding.pokemonName.setText(pokemonData.getName());
        binding.pokemonType.setText(pokemonData.getTypes().toString());
        Picasso.get()
                .load(pokemonData.getUrlImage())
                .placeholder(R.drawable.pokemon_placeholder)
                .error(R.drawable.noimage)
                .into(binding.pokemonImage);
    }
}