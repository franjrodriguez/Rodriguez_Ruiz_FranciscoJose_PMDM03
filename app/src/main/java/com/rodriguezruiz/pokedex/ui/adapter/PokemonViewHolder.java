package com.rodriguezruiz.pokedex.ui.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rodriguezruiz.pokedex.data.model.PokemonData;
import com.rodriguezruiz.pokedex.databinding.CardviewPokemonBinding;
import com.squareup.picasso.Picasso;

public class PokemonViewHolder extends RecyclerView.ViewHolder {

    public final CardviewPokemonBinding binding;

    public PokemonViewHolder(@NonNull View itemView, CardviewPokemonBinding binding) {
        super(itemView);
        this.binding = binding;
    }

    public void bind(PokemonData pokemonListCaptured) {
        binding.tvPokemonWeight.setText(pokemonListCaptured.getWeight().toString());
        binding.tvPokemonHeight.setText(pokemonListCaptured.getHeight().toString());
        binding.tvPokemonName.setText(pokemonListCaptured.getName());
        binding.tvPokedexIndex.setText(pokemonListCaptured.getId());
     //   binding.tvPokemonTypes.setText(pokemonListCaptured.getTypes());
        Picasso.get()
                .load(pokemonListCaptured.getUrlImage())
                .into(binding.imagePokemon);
    }
}
