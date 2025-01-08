package com.rodriguezruiz.pokedex.models;

public class Pokedex {

    private String namePokemon;
    private int indexPokemon;
    private String imagePokemon;

    public Pokedex(String namePokemon, int indexPokemon, String imagePokemon) {
        this.namePokemon = namePokemon;
        this.indexPokemon = indexPokemon;
        this.imagePokemon = imagePokemon;
    }

    public String getImagePokemon() {
        return imagePokemon;
    }

    public void setImagePokemon(String imagePokemon) {
        this.imagePokemon = imagePokemon;
    }

    public String getNamePokemon() {
        return namePokemon;
    }

    public void setNamePokemon(String namePokemon) {
        this.namePokemon = namePokemon;
    }

    public int getIndexPokemon() {
        return indexPokemon;
    }

    public void setIndexPokemon(int indexPokemon) {
        this.indexPokemon = indexPokemon;
    }
}
