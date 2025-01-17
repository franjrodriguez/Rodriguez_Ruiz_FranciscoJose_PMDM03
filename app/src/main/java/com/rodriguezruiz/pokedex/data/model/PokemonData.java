package com.rodriguezruiz.pokedex.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.PropertyName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PokemonData implements Serializable, Parcelable {
    @PropertyName("id")
    private String id;

    @PropertyName("name")
    private String name;

    @PropertyName("height")
    private Double height;

    @PropertyName("weight")
    private Double weight;

    @PropertyName("urlImage")
    private String urlImage;

    @PropertyName("types")
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

    @PropertyName("id")
    public String getId() {
        return id;
    }

    @PropertyName("id")
    public void setId(String id) {
        this.id = id;
    }

    @PropertyName("name")
    public String getName() {
        return name;
    }

    @PropertyName("name")
    public void setName(String name) {
        this.name = name;
    }

    @PropertyName("height")
    public Double getHeight() {
        return height;
    }

    @PropertyName("height")
    public void setHeight(Double height) {
        this.height = height;
    }

    @PropertyName("weight")
    public Double getWeight() {
        return weight;
    }

    @PropertyName("weight")
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @PropertyName("urlImage")
    public String getUrlImage() {
        return urlImage;
    }

    @PropertyName("urlImage")
    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @PropertyName("types")
    public List<String> getTypes() {
        return types;
    }

    @PropertyName("types")
    public void setTypes(List<String> types) {
        this.types = types;
    }

    protected PokemonData(Parcel in) {
        id = in.readString();
        name = in.readString();
        height = in.readDouble();
        weight = in.readDouble();
        urlImage = in.readString();
        types = new ArrayList<>();
        in.readStringList(types);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeDouble(height);
        dest.writeDouble(weight);
        dest.writeString(urlImage);
        dest.writeStringList(types);
    }

    public static final Creator<PokemonData> CREATOR = new Creator<PokemonData>() {
        @Override
        public PokemonData createFromParcel(Parcel in) {
            return new PokemonData(in);
        }

        @Override
        public PokemonData[] newArray(int size) {
            return new PokemonData[size];
        }
    };
}
