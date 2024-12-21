package com.rodriguezruiz.pokedex.api;

import com.rodriguezruiz.pokedex.models.PokemonResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokemonApiService {

    @GET("pokemon")
    Call<PokemonResponse> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);
}
