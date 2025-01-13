package com.rodriguezruiz.pokedex.ui.adapter;

import static com.rodriguezruiz.pokedex.utils.Constants.TAG;
import static com.rodriguezruiz.pokedex.utils.Constants.URL_SPRITE;
import static com.rodriguezruiz.pokedex.utils.Constants.TYPE_SPRITE;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rodriguezruiz.pokedex.R;
import com.rodriguezruiz.pokedex.data.model.PokedexData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PokedexAdapter extends RecyclerView.Adapter<PokedexAdapter.ViewHolder> {

    private ArrayList<PokedexData> pokedex;
    public PokedexAdapter() {
        pokedex = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        ImageView imageView;
        PokedexData pokemon = pokedex.get(position);
        holder.nombreTextView.setText(pokemon.getName());
        Picasso.get()
                .load(URL_SPRITE + pokemon.getId() + TYPE_SPRITE)
                .placeholder(R.drawable.pokemon_placeholder)
                .error(R.drawable.noimage)
                .into(holder.fotoImageView);
    }

    @Override
    public int getItemCount() {
        return pokedex.size();
    }

    public void updateList(ArrayList<PokedexData> newList) {
        Log.i(TAG, "PokedexAdapter (updateList): -> Se está actualizando la lista de pokemon ");

        this.pokedex.clear();
        this.pokedex.addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView fotoImageView;
        private TextView nombreTextView;

        public ViewHolder (View itemView) {
            super(itemView);

            fotoImageView = (ImageView) itemView.findViewById(R.id.imagePokemon);
            nombreTextView = itemView.findViewById(R.id.namePokemon);
        }
    }

}
