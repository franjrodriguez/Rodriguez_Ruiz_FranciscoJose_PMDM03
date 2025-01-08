package com.rodriguezruiz.pokedex.api;

import com.rodriguezruiz.pokedex.models.PokedexResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokedexApiService {

    @GET("pokemon")
    Call<PokedexResponse> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);

}