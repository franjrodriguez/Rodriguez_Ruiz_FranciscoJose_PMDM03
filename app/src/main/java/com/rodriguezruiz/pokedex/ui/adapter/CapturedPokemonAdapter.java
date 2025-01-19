package com.rodriguezruiz.pokedex.ui.adapter;

import static com.rodriguezruiz.pokedex.utils.Constants.TAG;
import static com.rodriguezruiz.pokedex.utils.Constants.TYPE_SPRITE;
import static com.rodriguezruiz.pokedex.utils.Constants.URL_SPRITE;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rodriguezruiz.pokedex.R;
import com.rodriguezruiz.pokedex.data.model.PokemonData;
import com.rodriguezruiz.pokedex.ui.activities.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CapturedPokemonAdapter extends RecyclerView.Adapter<CapturedPokemonAdapter.ViewHolder> {

    private ArrayList<PokemonData> pokemonDataCaptured;
    public Context context;

    public CapturedPokemonAdapter(ArrayList<PokemonData> pokemonDataCaptured, Context context) {
        this.pokemonDataCaptured = pokemonDataCaptured;
        this.context = context;
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
        PokemonData itemPokemonData = pokemonDataCaptured.get(position);
//        Log.i(TAG, "onBindViewHolder in CapturedPokemonAdapter -> id:" + itemPokemonData.getId());
//        Log.i(TAG, "onBindViewHolder in CapturedPokemonAdapter -> name:" + itemPokemonData.getName());
//        Log.i(TAG, "onBindViewHolder in CapturedPokemonAdapter -> weight:" + itemPokemonData.getWeight());
//        Log.i(TAG, "onBindViewHolder in CapturedPokemonAdapter -> height:" + itemPokemonData.getHeight());
//        Log.i(TAG, "onBindViewHolder in CapturedPokemonAdapter -> types:" + itemPokemonData.getTypes());
//        Log.i(TAG, "onBindViewHolder in CapturedPokemonAdapter -> urlImage:" + itemPokemonData.getUrlImage());
        holder.indexTextView.setText(itemPokemonData.getId());
        holder.nameTextView.setText(itemPokemonData.getName());
        holder.heightTextView.setText(itemPokemonData.getHeight().toString());
        holder.weightTextView.setText(itemPokemonData.getWeight().toString());
        holder.typesTextView.setText(itemPokemonData.getTypes().toString());
        Picasso.get()
                .load(itemPokemonData.getUrlImage())
                .placeholder(R.drawable.pokemon_placeholder)
                .error(R.drawable.noimage)
                .into(holder.fotoImageView);

        // Manejamos el evento click del usuario sobre un item
        holder.itemView.setOnClickListener(view -> {
            itemClicked(itemPokemonData, position);
        });
    }

    private void itemClicked(PokemonData itemPokemonData, int position) {
        // Se pasa la informacion del pokemon clicado a DetailPokemonFragment y se lanza
        ((MainActivity) context).userClickedDetailPokemon(itemPokemonData);
    }

    @Override
    public int getItemCount() {
        return pokemonDataCaptured.size();
    }

    public void removePokemonAdapter(int position) {
        pokemonDataCaptured.remove(position);
        notifyItemRemoved(position);
    }

    public void updateList(ArrayList<PokemonData> newList) {
        this.pokemonDataCaptured.clear();
        this.pokemonDataCaptured.addAll(newList);
        notifyDataSetChanged();
    }

    public String getPokemonIdAtPosition(int position) {
        return pokemonDataCaptured.get(position).getId();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fotoImageView;
        TextView indexTextView, nameTextView, typesTextView, weightTextView, heightTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            fotoImageView = (ImageView) itemView.findViewById(R.id.image_pokemon);
            nameTextView = itemView.findViewById(R.id.tv_pokemon_name);
            indexTextView = itemView.findViewById(R.id.tv_pokedex_index);
            weightTextView = itemView.findViewById(R.id.tv_pokemon_weight);
            heightTextView = itemView.findViewById(R.id.tv_pokemon_height);
            typesTextView = itemView.findViewById(R.id.tv_pokemon_types);
        }
    }
}
