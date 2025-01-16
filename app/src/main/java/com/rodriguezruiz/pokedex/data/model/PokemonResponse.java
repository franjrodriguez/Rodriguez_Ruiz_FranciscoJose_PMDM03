package com.rodriguezruiz.pokedex.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PokemonResponse {
    private int id;
    private String name;
    private int weight;
    private int height;
    private Sprites sprites;
    private List<Type> types;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
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