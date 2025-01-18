package com.rodriguezruiz.pokedex.data.api;

import com.rodriguezruiz.pokedex.data.model.PokedexResponse;
import com.rodriguezruiz.pokedex.data.model.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interfaz que define los endpoints de la API de Pokédex.
 * Esta interfaz utiliza anotaciones de Retrofit para mapear las solicitudes HTTP
 * a métodos Java que pueden ser llamados para obtener datos de la API.
 */
public interface PokedexApiService {

    /**
     * Obtiene una lista de Pokémon desde la API.
     * Este método realiza una solicitud GET al endpoint "pokemon" de la API,
     * permitiendo especificar un límite y un offset para la paginación de resultados.
     *
     * @param limit  Número máximo de Pokémon a devolver en la respuesta.
     * @param offset Número de Pokémon a omitir antes de comenzar a devolver resultados.
     * @return Un objeto Call que representa la solicitud HTTP para obtener la lista de Pokémon.
     */
    @GET("pokemon")
    Call<PokedexResponse> getPokedex(@Query("limit") int limit, @Query("offset") int offset);

    /**
     * Obtiene los detalles de un Pokémon específico desde la API.
     * Este método realiza una solicitud GET al endpoint "pokemon/{id}" de la API,
     * donde {id} es el identificador único del Pokémon.
     *
     * @param id El identificador único del Pokémon del cual se desean obtener los detalles.
     * @return Un objeto Call que representa la solicitud HTTP para obtener los detalles del Pokémon.
     */
    @GET("pokemon/{id}")
    Call<PokemonResponse> getPokemonDetails(@Path("id") String id);
}
