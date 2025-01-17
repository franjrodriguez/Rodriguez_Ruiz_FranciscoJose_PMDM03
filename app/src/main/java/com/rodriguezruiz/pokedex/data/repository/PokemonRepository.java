package com.rodriguezruiz.pokedex.data.repository;

import static com.rodriguezruiz.pokedex.utils.Constants.COLLECTION_CAPTURED;
import static com.rodriguezruiz.pokedex.utils.Constants.COLLECTION_TRAINERS;
import static com.rodriguezruiz.pokedex.utils.Constants.TAG;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rodriguezruiz.pokedex.data.model.TrainerData;
import com.rodriguezruiz.pokedex.listener.OperationCallBack;
import com.rodriguezruiz.pokedex.listener.PokemonListCallback;
import com.rodriguezruiz.pokedex.data.model.PokemonData;
import java.util.ArrayList;

public class PokemonRepository {

    private final FirebaseFirestore db;
    private final String userUID;

    public PokemonRepository(String userUID) {
        this.userUID = userUID;
        db = FirebaseFirestore.getInstance();
    }

    public void checkTrainerExist(String userUID) {

        db.collection(COLLECTION_TRAINERS)
                .document(userUID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (!document.exists()) {
                        } else {
                            addTrainer(userUID);
                        }
                    }
                });
    }

    private void addTrainer(String userUID) {
        TrainerData trainerData = new TrainerData(userUID);

        db.collection(COLLECTION_TRAINERS)
                .add(trainerData)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "Entrenador grabado"))
                .addOnFailureListener(e -> Log.e(TAG, "Error al grabar el entrenador."));
    }

//    public void addPokemonWithTrainerValidation(String userUID, PokemonData pokemon, OperationCallBack callback) {
//        Log.i(TAG, "PokemonRepository (addPokemonWithTrainerValidation) -> Verificando trainer: " + userUID);
//
//        // Primero verificamos si existe el entrenador
//        db.collection(COLLECTION_TRAINERS)
//                .document(userUID)
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        DocumentSnapshot document = task.getResult();
//                        if (document.exists()) {
//                            // Si existe el entrenador, procedemos a añadir el Pokémon
//                            addPokemonToTrainer(userUID, pokemon, callback);
//                        } else {
//                            // El entrenador no existe
//                            callback.onFailure(new Exception("El entrenador no existe"));
//                        }
//                    } else {
//                        // Error al verificar el entrenador
//                        callback.onFailure(task.getException());
//                    }
//                });
//    }

//    private void addPokemonToTrainer(String userUID, PokemonData pokemon, OperationCallBack callback) {
//        Log.i(TAG, "PokemonRepository (addPokemonToTrainer) -> Añadiendo pokemon: " + pokemon.toString());
//
//        db.collection(COLLECTION_TRAINERS)
//                .document(userUID)
//                .collection(COLLECTION_CAPTURED)
//                .document(pokemon.getId())
//                .set(pokemon)
//                .addOnSuccessListener(aVoid -> {
//                    Log.d(TAG, "Pokemon añadido correctamente");
//                    callback.onSuccess();
//                })
//                .addOnFailureListener(e -> {
//                    Log.e(TAG, "Error al añadir pokemon", e);
//                    callback.onFailure(e);
//                });
//    }

    public void addPokemon(String userUID, PokemonData pokemon, OperationCallBack callBack) {
        Log.i(TAG, "PokemonRepository (addPokemon) -> userUID: " + userUID + " pokemon: " + pokemon.toString());
        db.collection(COLLECTION_TRAINERS).document(userUID).collection(COLLECTION_CAPTURED).document(pokemon.getId())
                .set(pokemon)
                .addOnSuccessListener(aVoid -> callBack.onSuccess())
                .addOnFailureListener(e -> callBack.onFailure(e));
    }

    public void deletePokemon(String userUID, String pokemonId, OperationCallBack callBack) {
        Log.i(TAG, "PokemonRepository (deletePokemon) -> userUID: " + userUID + " pokemon: " + pokemonId);
        db.collection(COLLECTION_TRAINERS).document(userUID).collection(COLLECTION_CAPTURED).document(pokemonId)
                .delete()
                .addOnSuccessListener(aVoid -> callBack.onSuccess())
                .addOnFailureListener(e -> callBack.onFailure(e));
    }

//    public void getPokemon(String userUID, String pokemonId, PokemonCallBack callBack) {
//        Log.i(TAG, "PokemonRepository (getPokemon) -> userUID: " + userUID + " pokemonID: " + pokemonId.toString());
//        db.collection(COLLECTION_TRAINERS).document(userUID).collection(COLLECTION_CAPTURED).document(pokemonId)
//                .get()
//                .addOnSuccessListener(documentSnapshot -> {
//                    if (documentSnapshot.exists()) {
//                        PokemonData pokemon = documentSnapshot.toObject(PokemonData.class);
//                        callBack.onSuccess(pokemon);
//                    } else {
//                        callBack.onSuccess(null);
//                    }
//                })
//                .addOnFailureListener(e -> callBack.onFailure(e));
//    }

    public void getAllPokemons(String userUID, PokemonListCallback callBack) {
        db.collection(COLLECTION_TRAINERS).document(userUID).collection(COLLECTION_CAPTURED)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (querySnapshot == null) {
                        Log.e(TAG, "Query snapshot es nulo");
                        callBack.onFailure(new NullPointerException("Query snapshot es nulo"));
                        return;
                    }
                    ArrayList<PokemonData> pokemonList = new ArrayList<>();
                    // Añadimos logs para depuración
                    Log.d(TAG, "getAllPokemon -> Query executed. Documents found: " + querySnapshot.size());

                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        PokemonData pokemon = document.toObject(PokemonData.class);
                        if (pokemon != null) {
                            pokemonList.add(pokemon);
                            Log.d(TAG, "Added pokemon: " + pokemon.getId());
                        } else {
                            Log.w(TAG, "Por alguna razon el documento leido no puede convertirse a PokemonData" + document.getId());
                        }
                    }
                    Log.d(TAG, "Final list size: " + pokemonList.size());
                    callBack.onSuccess(pokemonList);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error getting documents: ", e);
                    callBack.onFailure(e);
                });
    }
}
