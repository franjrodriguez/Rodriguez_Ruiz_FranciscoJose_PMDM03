package com.rodriguezruiz.pokedex.data.repository;

import static com.rodriguezruiz.pokedex.utils.Constants.TAG;
import static com.rodriguezruiz.pokedex.utils.Constants.COLLECTION_NAME;
import static com.rodriguezruiz.pokedex.utils.Constants.COLLECTION_USER;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rodriguezruiz.pokedex.listener.OperationCallBack;
import com.rodriguezruiz.pokedex.listener.PokemonCallBack;
import com.rodriguezruiz.pokedex.listener.PokemonListCallback;
import com.rodriguezruiz.pokedex.data.model.PokemonData;
import java.util.ArrayList;

public class PokemonRepository {

    private FirebaseFirestore db;

    public PokemonRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void addPokemon(String userUID, PokemonData pokemon, OperationCallBack callBack) {
        db.collection(COLLECTION_USER).document(userUID).collection(COLLECTION_NAME).document(pokemon.getId())
                .set(pokemon)
                .addOnSuccessListener(aVoid -> callBack.onSuccess())
                .addOnFailureListener(e -> callBack.onFailure(e));
    }

    public void deletePokemon(String userUID, String pokemonId, OperationCallBack callBack) {
        db.collection(COLLECTION_USER).document(userUID).collection(COLLECTION_NAME).document(pokemonId)
                .delete()
                .addOnSuccessListener(aVoid -> callBack.onSuccess())
                .addOnFailureListener(e -> callBack.onFailure(e));
    }

    public void getPokemon(String userUID, String pokemonId, PokemonCallBack callBack) {
        db.collection(COLLECTION_USER).document(userUID).collection(COLLECTION_NAME).document(pokemonId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        PokemonData pokemon = documentSnapshot.toObject(PokemonData.class);
                        callBack.onSuccess(pokemon);
                    } else {
                        callBack.onSuccess(null);
                    }
                })
                .addOnFailureListener(e -> callBack.onFailure(e));
    }

    public void getAllPokemons(String userUID, PokemonListCallback callBack) {
        db.collection(COLLECTION_USER).document(userUID).collection(COLLECTION_NAME)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    ArrayList<PokemonData> pokemonList = new ArrayList<>();
                    // Añadimos logs para depuración
                    Log.d(TAG + "=>POKEMON_REPO", "Query executed. Documents found: " + querySnapshot.size());

                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        PokemonData pokemon = document.toObject(PokemonData.class);
                        if (pokemon != null) {
                            pokemonList.add(pokemon);
                            Log.d("POKEMON_REPO", "Added pokemon: " + pokemon.getId());
                        }
                    }
                    Log.d(TAG + "=>POKEMON_REPO", "Final list size: " + pokemonList.size());
                    callBack.onSuccess(pokemonList);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG + "=>POKEMON_REPO", "Error getting documents: ", e);
                    callBack.onFailure(e);
                });
    }
}
