package com.rodriguezruiz.pokedex.ui.adapter;

import static com.rodriguezruiz.pokedex.utils.Constants.TAG;
import static com.rodriguezruiz.pokedex.utils.Constants.URL_SPRITE;
import static com.rodriguezruiz.pokedex.utils.Constants.TYPE_SPRITE;

import android.content.Context;
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

public class PokedexAdapter extends RecyclerView.Adapter<PokedexAdapter.ViewHolder> {

    private ArrayList<PokedexData> pokedex;
    private final Context context;

    public PokedexAdapter(ArrayList<PokedexData> pokedex, Context context) {
        this.pokedex = pokedex;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PokedexData itemFromPokedexSelected = pokedex.get(position);
        holder.nombreTextView.setText(itemFromPokedexSelected.getName());

        // Cambiar el estilo si el Pokémon ha sido "capturado"
        if (itemFromPokedexSelected.isCaptured()) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.selected_background));
            holder.nombreTextView.setTypeface(holder.nombreTextView.getTypeface(), Typeface.ITALIC);
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.default_background));
            holder.nombreTextView.setTypeface(holder.nombreTextView.getTypeface(), Typeface.NORMAL);
        }

        Picasso.get()
                .load(URL_SPRITE + itemFromPokedexSelected.getId() + TYPE_SPRITE)
                .placeholder(R.drawable.pokemon_placeholder)
                .error(R.drawable.noimage)
                .into(holder.fotoImageView);

        // Manejamos el evento click del usuario sobre un item
        holder.itemView.setOnClickListener(view -> {
            itemClicked(itemFromPokedexSelected, position);
        });
    }

    private void itemClicked(PokedexData itemFromPokedexSelected, int position) {
        if (!itemFromPokedexSelected.isCaptured()) {
            itemFromPokedexSelected.setCaptured(true);
            notifyItemChanged(position);
        }
        // Llama a la funcion que se encarga en MainActivity
        ((MainActivity) context).userClickedListPokemon(itemFromPokedexSelected);
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
