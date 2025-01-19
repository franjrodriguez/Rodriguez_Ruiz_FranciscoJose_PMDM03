package com.rodriguezruiz.pokedex.ui.adapter;

import static com.rodriguezruiz.pokedex.utils.Constants.TAG;
import static com.rodriguezruiz.pokedex.utils.Constants.URL_SPRITE;
import static com.rodriguezruiz.pokedex.utils.Constants.TYPE_SPRITE;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.rodriguezruiz.pokedex.R;
import com.rodriguezruiz.pokedex.data.model.PokedexData;
import com.rodriguezruiz.pokedex.data.model.PokemonData;
import com.rodriguezruiz.pokedex.ui.activities.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PokedexAdapter extends RecyclerView.Adapter<PokedexAdapter.PokemonViewHolder> {
    private ArrayList<PokedexData> pokedexList;
    private final Context context;

    public PokedexAdapter(ArrayList<PokedexData> pokedexList, Context context) {
        this.pokedexList = pokedexList;
        this.context = context;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        PokedexData pokemon = pokedexList.get(position);
        holder.bind(pokemon, position);       // Se vinculan los datos a la vista
    }

    private void itemClicked(PokedexData pokemon, int position) {
        if (!pokemon.isCaptured()) {
            pokemon.setCaptured(true);
            notifyItemChanged(position);
        }
        // Llama a la funcion que se encarga en MainActivity
        ((MainActivity) context).userClickedListPokemon(pokemon);
    }

    @Override
    public int getItemCount() {
        return pokedexList.size();
    }

    public void updateList(ArrayList<PokedexData> newList) {
        this.pokedexList = new ArrayList<>(newList);    // Actualiza la lista
        notifyDataSetChanged();                         // Notifica los cambios
    }


    public class PokemonViewHolder extends RecyclerView.ViewHolder {
        private final ImageView fotoImageView;
        private final TextView nombreTextView;

        public PokemonViewHolder (@NonNull View itemView) {
            super(itemView);

            fotoImageView = (ImageView) itemView.findViewById(R.id.imagePokemon);
            nombreTextView = itemView.findViewById(R.id.namePokemon);
        }

        public void bind(PokedexData pokemon, int position) {
            nombreTextView.setText(pokemon.getName());
            Picasso.get()
                    .load(URL_SPRITE + pokemon.getId() + TYPE_SPRITE)
                    .placeholder(R.drawable.pokemon_placeholder)
                    .error(R.drawable.noimage)
                    .into(fotoImageView);

            // Cambiar el estilo si el Pokémon ha sido "capturado"
            if (pokemon.isCaptured()) {
                // Se aplica un efecto visual distinto
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0); // Escala de grises
                fotoImageView.setColorFilter(new ColorMatrixColorFilter(matrix));
                nombreTextView.setTypeface(nombreTextView.getTypeface(), Typeface.ITALIC);
                // Se desactiva la interacción
                itemView.setEnabled(false);
                itemView.setOnClickListener(null);
            } else {
                // Restaurar el estado normal
                fotoImageView.clearColorFilter();
                nombreTextView.setTypeface(nombreTextView.getTypeface(), Typeface.NORMAL);
                itemView.setEnabled(true);
                itemView.setOnClickListener(view -> {
                    // Manejar el clic en el Pokémon no capturado
                    itemClicked(pokemon, position);
                });
            }
        }
    }

}
