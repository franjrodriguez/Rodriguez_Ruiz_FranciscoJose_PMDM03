package com.rodriguezruiz.pokedex.adapter;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rodriguezruiz.pokedex.MainActivity;
import com.rodriguezruiz.pokedex.databinding.ItemPokemonBinding;
import com.rodriguezruiz.pokedex.models.Pokemon;
import com.rodriguezruiz.pokedex.viewmodel.PokedexViewHolder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PokedexAdapter extends RecyclerView.Adapter<PokedexViewHolder> {

    private ArrayList<Pokemon> listPokemon;
    private Context context;
    private Set<Integer> selectedPosition = new HashSet<>();

    public PokedexAdapter(ArrayList<Pokemon> listPokemon, Context context) {
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
        Pokemon currentPokemon = this.listPokemon.get(position);
        holder.bind(currentPokemon);

        if (selectedPosition.contains(position)) {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_blue_light));
            holder.binding.namePokemon.setTypeface(null, Typeface.ITALIC);  // Negrita
            holder.binding.namePokemon.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.white));
            holder.binding.indexPokemon.setTypeface(null, Typeface.ITALIC);  // Negrita
            holder.binding.indexPokemon.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.white));
            holder.binding.imagePokemon.setAlpha(1.0f);
            holder.binding.imagePokemon.setClickable(false);
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            holder.binding.imagePokemon.setColorFilter(filter);
            holder.itemView.setEnabled(false);
        } else {
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(android.R.color.transparent));
            holder.binding.namePokemon.setTypeface(null, Typeface.NORMAL);  // Negrita
            holder.binding.namePokemon.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.black));
            holder.binding.indexPokemon.setTypeface(null, Typeface.NORMAL);  // Negrita
            holder.binding.indexPokemon.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.black));
            holder.binding.imagePokemon.setAlpha(0.5f);
            holder.binding.imagePokemon.setClickable(true);
            holder.itemView.setEnabled(true);
        }
        holder.itemView.setOnClickListener(view -> {
            if (!selectedPosition.contains(position)) {
                selectedPosition.add(position);
                notifyItemChanged(position);
                itemClicked(currentPokemon, view);
            }
        });
    }

    private void itemClicked(Pokemon currentPokemon, View view) {
        ((MainActivity) context).userClickedListPokemon(currentPokemon, view);
    }

    @Override
    public int getItemCount() {
        return (!listPokemon.isEmpty())? listPokemon.size() : 0;
    }
}
