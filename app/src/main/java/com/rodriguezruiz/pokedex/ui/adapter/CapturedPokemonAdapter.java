package com.rodriguezruiz.pokedex.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rodriguezruiz.firestore.model.PokemonData;

import java.util.ArrayList;

public class CapturedPokemonAdapter extends RecyclerView.Adapter<CapturedPokemonViewHolder>{

    private ArrayList<PokemonData> dataset;
    private Context context;

    public CapturedPokemonAdapter(ArrayList<PokemonData> dataset, Context context) {
        this.context = context;
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public CapturedPokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new CapturedPokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CapturedPokemonViewHolder holder, int position) {
        PokemonData p = dataset.get(position);
        holder.nombreTextView.setText(p.getName());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void adicionarListaPokemon(ArrayList<PokemonData> listaPokemon) {
        dataset.addAll(listaPokemon);
        notifyDataSetChanged();
    }
}
