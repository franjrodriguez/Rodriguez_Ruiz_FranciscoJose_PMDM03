package com.rodriguezruiz.pokedex.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PokemonResponse {
    private int id;
    private String name;
    private Double weight;
    private Double height;
    private Sprites sprites;
    private List<Type> types;

    public PokemonResponse() {
    }

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

    public static class Sprites {
        @SerializedName("front_default")
        private String frontDefault;

        public String getFrontDefault() {
            return frontDefault;
        }
    }

    public static class Type {
        private TypeDetail type;

        public TypeDetail getType() {
            return type;
        }

        public static class TypeDetail {
            private String name;

            public String getName() {
                return name;
            }
        }
    }
}