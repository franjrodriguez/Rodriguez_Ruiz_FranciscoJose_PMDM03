package com.rodriguezruiz.pokedex.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rodriguezruiz.pokedex.R;
import com.rodriguezruiz.pokedex.data.model.PokemonData;
import com.rodriguezruiz.pokedex.databinding.CardviewPokemonBinding;
import com.squareup.picasso.Picasso;

public class CapturedPokemonViewHolder extends RecyclerView.ViewHolder {

    public final CardviewPokemonBinding binding;

    public CapturedPokemonViewHolder(@NonNull View itemView, CardviewPokemonBinding binding) {
        super(itemView);
        this.binding = binding;
    }

    public void bind(PokemonData pokemonCardView) {
        binding.tvPokedexIndex.setText(pokemonCardView.getName());
        binding.tvPokemonName.setText(pokemonCardView.getName());
        binding.tvPokemonHeight.setText(pokemonCardView.getHeight().toString());
        binding.tvPokemonWeight.setText(pokemonCardView.getWeight().toString());
        Picasso.get()
                .load(pokemonCardView.getUrlImage())
                .into(binding.imagePokemon);
    }
}
