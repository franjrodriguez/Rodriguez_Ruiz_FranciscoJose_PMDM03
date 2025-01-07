package com.rodriguezruiz.pokedex.data;

public class PokedexListName {

    private String namePokemon;
    private int indexPokemon;
    private String imagePokemon;

    public PokedexListName(String namePokemon, int indexPokemon, String imagePokemon) {
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
