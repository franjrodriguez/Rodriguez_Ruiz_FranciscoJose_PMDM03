package com.rodriguezruiz.pokedex.ui.adapter;

import androidx.recyclerview.widget.RecyclerView;
import com.rodriguezruiz.pokedex.databinding.ItemPokemonBinding;
import com.rodriguezruiz.pokedex.data.model.PokedexData;
import com.squareup.picasso.Picasso;

public class PokedexViewHolder extends RecyclerView.ViewHolder {

    public final ItemPokemonBinding binding;

    public PokedexViewHolder(ItemPokemonBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(PokedexData pokemonListName) {
        binding.namePokemon.setText(pokemonListName.getName());
        Picasso.get()
                .load(pokemonListName.getUrl())
                .into(binding.imagePokemon);
    }
}
