package com.rodriguezruiz.pokedex.viewmodel;


import androidx.recyclerview.widget.RecyclerView;
import com.rodriguezruiz.pokedex.data.PokemonListName;
import com.rodriguezruiz.pokedex.databinding.ItemPokemonBinding;
import com.squareup.picasso.Picasso;

public class PokemonViewHolder extends RecyclerView.ViewHolder {

    private final ItemPokemonBinding binding;

    public PokemonViewHolder(ItemPokemonBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(PokemonListName pokemonListName) {

        binding.namePokemon.setText(pokemonListName.getNamePokemon());
        binding.indexPokemon.setText(pokemonListName.getIndexPokemon());
        Picasso.get()
                .load(pokemonListName.getImagePokemon())
                .into(binding.imagePokemon);
    }
}
