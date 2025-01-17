package com.rodriguezruiz.pokedex.data.api;

import com.rodriguezruiz.pokedex.data.model.PokedexResponse;
import com.rodriguezruiz.pokedex.data.model.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokedexApiService {

    @GET("pokemon")
    Call<PokedexResponse> getPokedex(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{id}")
    Call<PokemonResponse> getPokemonDetails(@Path("id") String id);
}
