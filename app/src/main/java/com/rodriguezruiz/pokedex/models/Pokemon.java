package com.rodriguezruiz.pokedex.models;

import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Pokemon {
    private final static String URL_SPRITE = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";
    private final static String TYPE_SPRITE = ".png";

    private String id;
    private String name;
    private String height;
    private String weight;
    @SerializedName("front_default")
    private String front_default;
    private List<String> types;
    private boolean deactivated;

    public Pokemon(String id, String name, String height, String weight, String front_default, List<String> types) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.front_default = URL_SPRITE + this.id + TYPE_SPRITE;
        this.types = types;
        this.deactivated = false;
    }

    public Pokemon(String id, String name, String height, String weight, String front_default, List<String> types, boolean deactivated) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.front_default = URL_SPRITE + this.id + TYPE_SPRITE;
        this.types = types;
        this.deactivated = deactivated;
    }

    public String getFront_default() {
        return front_default;
    }

    public void setFront_default(String front_default) {
        this.front_default = front_default;
    }

    public boolean isDeactivated() {
        return deactivated;
    }

    public void setDeactivated(boolean deactivated) {
        this.deactivated = deactivated;
    }

    public void changeState() {
        this.deactivated = !this.deactivated;
    }
    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getId() {
        return id;
    }

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

    @NonNull
    @Override
    public String toString() {
        return "PokemonData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", front_default='" + front_default + '\'' +
                ", types='" + types + '\'' +
                '}';
    }
}
