package com.rodriguezruiz.pokedex.data.model;

import java.io.Serializable;
import java.util.List;

public class PokemonData implements Serializable {
    private String id;
    private String name;
    private Double height;
    private Double weight;
    private String urlImage;
    private List<String> types;

    public PokemonData() {}

    public PokemonData(String id, String name, Double height, Double weight, String urlImage, List<String> types) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.urlImage = urlImage;
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

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
