package com.rodriguezruiz.pokedex.viewmodel;


import androidx.recyclerview.widget.RecyclerView;
import com.rodriguezruiz.pokedex.data.PokedexListName;
import com.rodriguezruiz.pokedex.databinding.ItemPokemonBinding;
import com.squareup.picasso.Picasso;

public class PokedexViewHolder extends RecyclerView.ViewHolder {

    private final ItemPokemonBinding binding;

    public PokedexViewHolder(ItemPokemonBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(PokedexListName pokemonListName) {

        binding.namePokemon.setText(pokemonListName.getNamePokemon());
        binding.indexPokemon.setText(pokemonListName.getIndexPokemon());
        Picasso.get()
                .load(pokemonListName.getImagePokemon())
                .into(binding.imagePokemon);
    }
}
