package com.rodriguezruiz.pokedex.data.api;

import static com.rodriguezruiz.pokedex.utils.Constants.TAG;
import static com.rodriguezruiz.pokedex.utils.Constants.URL_API;
import static com.rodriguezruiz.pokedex.utils.Constants.OFFSET;
import static com.rodriguezruiz.pokedex.utils.Constants.LIMIT;

import android.util.Log;
import androidx.annotation.NonNull;
import com.rodriguezruiz.pokedex.data.model.PokedexData;
import com.rodriguezruiz.pokedex.data.model.PokedexResponse;
import com.rodriguezruiz.pokedex.data.model.PokemonData;
import com.rodriguezruiz.pokedex.data.model.PokemonResponse;
import com.rodriguezruiz.pokedex.listener.OnPokedexLoadedListener;
import com.rodriguezruiz.pokedex.listener.OnPokemonLoadedListener;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase que maneja las solicitudes a la API de Pokédex para obtener la lista de Pokémon
 * y los detalles de un Pokémon específico.
 */
public class GetApiPokedex {

    private PokedexResponse pokedexResponse = new PokedexResponse();

    /**
     * Obtiene la lista de Pokémon desde la API utilizando Retrofit.
     * Este método configura Retrofit, realiza la llamada a la API y maneja la respuesta.
     * Si la respuesta es valida, se procesa la lista de Pokémon y se notifica al listener.
     *
     * @param listener Listener que será notificado cuando la lista de Pokémon esté cargada.
     */
    public void gettingListPokedex(OnPokedexLoadedListener listener) {
        Log.d(TAG, "GetApiPokedex -> Iniciando configuración de Retrofit");
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Log.d(TAG, "GetApiPokedex -> Retrofit configurado");

            PokedexApiService service = retrofit.create(PokedexApiService.class);
            Log.d(TAG, "GetApiPokedex -> Servicio creado");

            Call<PokedexResponse> pokedexResponseCall = service.getPokedex(LIMIT, OFFSET);
            Log.d(TAG, "GetApiPokedex -> Llamada creada con LIMIT=" + LIMIT + " y OFFSET=" + OFFSET);

            pokedexResponseCall.enqueue(new Callback<PokedexResponse>() {
                @Override
                public void onResponse(@NonNull Call<PokedexResponse> call, @NonNull Response<PokedexResponse> response) {
                    Log.d(TAG, "GetApiPokedex -> onResponse llamado");

                    if (response.isSuccessful()) {
                        Log.d(TAG, "GetApiPokedex -> Respuesta exitosa");
                        PokedexResponse pokedexResponse = response.body();
                        if (pokedexResponse != null && pokedexResponse.getResults() != null) {
                            ArrayList<PokedexData> listaPokedex = pokedexResponse.getResults();
                            // Revisa la lista para actualizar el id del pokedex en funcion del url
                            for (PokedexData item : listaPokedex) {
                                // Toma la ultima parte de la URL para coger el numero del Pokemon en la lista Pokédex
                                String[] urlPartes = item.getUrl().split("/");
                                item.setId(urlPartes[urlPartes.length - 1]);
                                item.setCaptured(false);
                            }
                            Log.d(TAG, "GetApiPokedex -> Pokemon obtenidos: " + listaPokedex.size());
                            listener.onLoaded(listaPokedex);
                        } else {
                            Log.e(TAG, "GetApiPokedex -> Respuesta body es null o results es null");
                        }
                    } else {
                        Log.e(TAG, "GetApiPokedex -> Error en respuesta: " + response.code());
                        if (response.errorBody() != null) {
                            try {
                                Log.e(TAG, "GetApiPokedex -> Error body: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PokedexResponse> call, @NonNull Throwable t) {
                    Log.e(TAG, "GetApiPokedex -> Error en la llamada: " + t.getMessage());
                    t.printStackTrace();
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "GetApiPokedex -> Excepción al configurar la llamada: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtiene los detalles de un Pokémon específico desde la API utilizando Retrofit.
     * Este método configura Retrofit, realiza la llamada a la API y maneja la respuesta.
     * Si la respuesta es exitosa, se notifica al listener con los detalles del Pokémon.
     *
     * @param pokemonId El ID del Pokémon del cual se desean obtener los detalles.
     * @param listener  Listener que será notificado cuando los detalles del Pokémon estén cargados.
     */
    public void gettingPokemonDetail(String pokemonId, OnPokemonLoadedListener listener) {
        Log.d(TAG, "GetApiPokedex -> Iniciando petición a la API");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokedexApiService service = retrofit.create(PokedexApiService.class);
        Call<PokemonResponse> pokemonDataCall = service.getPokemonDetails(pokemonId);

        pokemonDataCall.enqueue(new Callback<PokemonResponse>(){
            @Override
            public void onResponse(@NonNull Call<PokemonResponse> call, @NonNull Response<PokemonResponse> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse -> Respuesta exitosa de la API");

                    PokemonResponse pokemonResponse = response.body();
                    if (pokemonResponse != null) {
                        listener.onLoaded(pokemonResponse);
                    } else {
                        Log.e(TAG, "El cuerpo de la respuesta es null");
                    }

                } else {
                    Log.e(TAG, "Error en la respuesta: " + response.code());
                    Log.e(TAG, "Error body: " + response.errorBody());                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                Log.e(TAG, " onFailure -> Error en la peticion : " + t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
