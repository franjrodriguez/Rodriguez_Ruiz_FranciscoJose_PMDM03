package com.rodriguezruiz.pokedex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rodriguezruiz.pokedex.MainActivity;
import com.rodriguezruiz.pokedex.data.PokedexListName;
import com.rodriguezruiz.pokedex.databinding.ItemPokemonBinding;
import com.rodriguezruiz.pokedex.viewmodel.PokedexViewHolder;

import java.util.ArrayList;

public class PokedexAdapter extends RecyclerView.Adapter<PokedexViewHolder> {

    private ArrayList<PokedexListName> listPokemon;
    private Context context;

    public PokedexAdapter(ArrayList<PokedexListName> listPokemon, Context context) {
        this.context = context;
        this.listPokemon = listPokemon;
    }

    @NonNull
    @Override
    public PokedexViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPokemonBinding binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PokedexViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PokedexViewHolder holder, int position) {
        PokedexListName currentPokemon = this.listPokemon.get(position);
        holder.bind(currentPokemon);

        // llama al metodo onClick
        holder.itemView.setOnClickListener(view->itemClicked(currentPokemon, view));
    }

    private void itemClicked(PokedexListName currentPokemon, View view) {
        ((MainActivity) context).userClickedListPokemon(currentPokemon, view);
    }

    @Override
    public int getItemCount() {
        return (!listPokemon.isEmpty())? listPokemon.size() : 0;
    }
}
