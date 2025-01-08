package com.rodriguezruiz.pokedex.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class PokemonResponse {
    private String id;
    private String name;
    private String height;
    private String weight;
    @SerializedName("front_default")
    private String front_default;
    private List<TypeSlot> types;

    public static class TypeSlot {
        private Type type;

        public Type getType() {
            return type;
        }
    }

    public static class Type {
        private String name;

        public String getName() {
            return name;
        }
    }

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFront_default() {
        return front_default;
    }

    public void setFront_default(String front_default) {
        this.front_default = front_default;
    }

    public List<String> getTypeNames() {
        List<String> typeNames = new ArrayList<>();
        for (TypeSlot typeSlot : types) {
            typeNames.add(typeSlot.getType().getName());
        }
        return typeNames;
    }
}
