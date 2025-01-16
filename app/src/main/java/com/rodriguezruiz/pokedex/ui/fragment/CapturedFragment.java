package com.rodriguezruiz.pokedex.ui.fragment;

import static com.rodriguezruiz.pokedex.utils.Constants.SETTING_DELETE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rodriguezruiz.pokedex.databinding.FragmentCapturedBinding;
import com.rodriguezruiz.pokedex.ui.activities.MainActivity;
import com.rodriguezruiz.pokedex.ui.adapter.CapturedPokemonAdapter;
import com.rodriguezruiz.pokedex.ui.adapter.PokemonViewHolder;
import com.rodriguezruiz.pokedex.viewmodel.PokemonViewModel;

public class CapturedFragment extends Fragment {

    private static RecyclerView recyclerView;
    private static CapturedPokemonAdapter adapter;
    private FragmentCapturedBinding binding;
    private PokemonViewModel pokemonViewModel;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCapturedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Carga del SharedPreferences el estado del permiso para borrar Pokemon capturados
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean isEnabledDeletePokemon = sharedPreferences.getBoolean(SETTING_DELETE, false);

        // Recoge el arraylist de la lista capturedpokemon (Firestore) cargada en MainActivity
        recyclerView = binding.recyclerviewPokemonList;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new CapturedPokemonAdapter();
        recyclerView.setAdapter(adapter);

        // Almacena la lista cargada en el ViewModel
        pokemonViewModel = new ViewModelProvider(requireActivity()).get(PokemonViewModel.class);
        pokemonViewModel.getPokemonData().observe(getViewLifecycleOwner(), capturedListPokemons -> {
            adapter.updateList(capturedListPokemons);
        });

        // Configurar el ItemTouchHelper para el desplazamiento de la muerte
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // No se permite mover los elementos
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                String pokemonId = adapter.getPokemonIdAtPosition(position);

                // Procede con el borrado de la base de datos
                ((MainActivity) context).deletePokemon(pokemonId);

                // Actualiza el recyclerview.
                adapter.notifyItemRemoved(position); // Notifica al adapter que se ha eliminado un item
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // Aquí puedes personalizar el fondo rojo
                View itemView = viewHolder.itemView;
                Paint paint = new Paint();
                paint.setColor(Color.RED);
                if (dX < 0) { // Deslizando hacia la izquierda
                    c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom(), paint);
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        });
    }
}