package com.rodriguezruiz.pokedex.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.rodriguezruiz.pokedex.obsolete.PokedexResponse;
import com.rodriguezruiz.pokedex.models.Pokemon;
import com.rodriguezruiz.pokedex.utils.OnPokedexLoadedListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetApiPokedex {
    private final static String TAG = "TAGFRAN";
    private final static String URL_API = "https://pokeapi.co/api/v2/";
    private final static int OFFSET = 0;
    private final static int LIMIT = 150;
    private PokedexResponse pokedexResponse = new PokedexResponse();

    public void gettingListPokedex(OnPokedexLoadedListener listener) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokedexApiService service = retrofit.create(PokedexApiService.class);
        Call<PokedexResponse> pokedexResponseCall = service.getPokemonList(LIMIT, OFFSET);

        pokedexResponseCall.enqueue(new Callback<PokedexResponse>() {
            @Override
            public void onResponse(@NonNull Call<PokedexResponse> call, @NonNull Response<PokedexResponse> response) {
                if (response.isSuccessful()) {
                    PokedexResponse pokedexResponse = response.body();
                    assert pokedexResponse != null;
                    ArrayList<Pokemon> listaPokedex = pokedexResponse.getResults();
                    listener.onLoaded(listaPokedex);

                } else {
                    Log.e(TAG, "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokedexResponse> call, Throwable t) {
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }

}

