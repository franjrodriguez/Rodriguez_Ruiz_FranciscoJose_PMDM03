package com.rodriguezruiz.pokedex.data;

public class PokemonData {

    private int orderNumber;
    private String name;
    private String image;
    private String type;
    private double height;
    private double weight;

    public PokemonData(int orderNumber, String name, String image, String type, double height, double weight) {
        this.orderNumber = orderNumber;
        this.name = name;
        this.image = image;
        this.type = type;
        this.height = height;
        this.weight = weight;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
