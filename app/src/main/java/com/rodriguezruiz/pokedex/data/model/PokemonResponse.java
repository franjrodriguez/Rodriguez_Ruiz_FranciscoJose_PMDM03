package com.rodriguezruiz.pokedex.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Clase que representa la respuesta de datos de un Pokémon obtenida de una API.
 *
 * @author Francisco José Rodríguez Ruiz
 * @version 1.0.0
 */
public class PokemonResponse {
    private int id;
    private String name;
    private Double weight;
    private Double height;
    private Sprites sprites;
    private List<Type> types;

    /** Constructor por defecto necesario para la deserialización. */
    public PokemonResponse() {
    }

    /**
     * Constructor que inicializa todos los campos de la clase.
     *
     * @param id el identificador del Pokémon.
     * @param name el nombre del Pokémon.
     * @param weight el peso del Pokémon.
     * @param height la altura del Pokémon.
     * @param sprites las imágenes asociadas al Pokémon.
     * @param types la lista de tipos del Pokémon.
     */
    public PokemonResponse(int id, String name, Double weight, Double height, Sprites sprites, List<Type> types) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.sprites = sprites;
        this.types = types;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getWeight() { 
        return weight;
    }

    public Double getHeight() {
        return height;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public List<Type> getTypes() {
        return types;
    }

    /**
     * Clase anidada que representa las imágenes del Pokémon.
     */
    public static class Sprites {
        @SerializedName("front_default")
        private String frontDefault;

        public String getFrontDefault() {
            return frontDefault;
        }
    }

    /**
     * Clase anidada que representa un tipo de Pokémon.
     */
    public static class Type {
        private TypeDetail type;

        public TypeDetail getType() {
            return type;
        }

        /**
         * Clase anidada que representa los detalles del tipo de Pokémon.
         */
        public static class TypeDetail {
            private String name;

            public String getName() {
                return name;
            }
        }
    }
}