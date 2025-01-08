package com.rodriguezruiz.pokedex.viewmodel;


import androidx.recyclerview.widget.RecyclerView;
import com.rodriguezruiz.pokedex.databinding.ItemPokemonBinding;
import com.rodriguezruiz.pokedex.models.Pokemon;
import com.squareup.picasso.Picasso;

public class PokedexViewHolder extends RecyclerView.ViewHolder {

    public final ItemPokemonBinding binding;

    public PokedexViewHolder(ItemPokemonBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Pokemon pokemonListName) {
        binding.namePokemon.setText(pokemonListName.getName());
        binding.indexPokemon.setText(pokemonListName.getId());
        Picasso.get()
                .load(pokemonListName.getFront_default())
                .into(binding.imagePokemon);
    }
}
