package com.rodriguezruiz.pokedex.ui.adapter;

import static com.rodriguezruiz.pokedex.utils.Constants.TYPE_SPRITE;
import static com.rodriguezruiz.pokedex.utils.Constants.URL_SPRITE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rodriguezruiz.pokedex.R;
import com.rodriguezruiz.pokedex.data.model.PokemonData;
import com.rodriguezruiz.pokedex.databinding.CardviewPokemonBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CapturedPokemonAdapter extends RecyclerView.Adapter<CapturedPokemonAdapter.ViewHolder> {

    //public Context context;
    private ArrayList<PokemonData> pokemonDataCaptured;
    public CapturedPokemonAdapter() {
        pokemonDataCaptured = new ArrayList<>();
    }

    @NonNull
    @Override
    public CapturedPokemonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cardview_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CapturedPokemonAdapter.ViewHolder holder, int position) {
        PokemonData pokemonData = pokemonDataCaptured.get(position);
        holder.indexTextView.setText(pokemonData.getId());
        holder.nameTextView.setText(pokemonData.getName());
        holder.heightTextView.setText(pokemonData.getHeight().toString());
        holder.weightTextView.setText(pokemonData.getWeight().toString());
        holder.typesTextView.setText(pokemonData.getTypes().toString());
        Picasso.get().load(URL_SPRITE + pokemonData.getId() + TYPE_SPRITE)
                .placeholder(R.drawable.pokemon_placeholder)
                .error(R.drawable.noimage)
                .into(holder.fotoImageView);
    }

    @Override
    public int getItemCount() {
        return pokemonDataCaptured.size();
    }

    public void updateList(ArrayList<PokemonData> newList) {
        this.pokemonDataCaptured.clear();
        this.pokemonDataCaptured.addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView fotoImageView;
        private TextView indexTextView, nameTextView, typesTextView, weightTextView, heightTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            fotoImageView = (ImageView) itemView.findViewById(R.id.imagePokemon);
            nameTextView = itemView.findViewById(R.id.tv_pokemon_name);
            indexTextView = itemView.findViewById(R.id.tv_pokedex_index);
            weightTextView = itemView.findViewById(R.id.tv_pokemon_weight);
            heightTextView = itemView.findViewById(R.id.tv_pokemon_height);
            typesTextView = itemView.findViewById(R.id.tv_pokemon_types);
        }
    }
}
