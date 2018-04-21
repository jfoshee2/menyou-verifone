package com.example.james.menyou_verifone.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Generated;

/**
 * Object class for JSON mapping
 */
@Generated("org.jsonschema2pojo")
public class MenuItem implements Parcelable {

    @SerializedName("ID")
    private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("Ingredients")
    private List<String> ingredients;
    @SerializedName("Price")
    private double price;
    @SerializedName("Calories")
    private int calories;

    public MenuItem(String name, List<String> ingredients, double price, int calories) {
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
        this.calories = calories;
    }

    private MenuItem(MenuItemBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.ingredients = builder.ingredients;
        this.price = builder.price;
        this.calories = builder.calories;
    }

    protected MenuItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredients = in.createStringArrayList();
        price = in.readDouble();
        calories = in.readInt();
    }

    public static final Creator<MenuItem> CREATOR = new Creator<MenuItem>() {
        @Override
        public MenuItem createFromParcel(Parcel in) {
            return new MenuItem(in);
        }

        @Override
        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public double getPrice() {
        return price;
    }

    public int getCalories() {
        return calories;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeList(ingredients);
        parcel.writeDouble(price);
        parcel.writeInt(calories);
    }

    public static class MenuItemBuilder {
        private int id;
        private String name;
        private List<String> ingredients;
        private double price;
        private int calories;

        public MenuItemBuilder() {

        }

        public MenuItemBuilder withId(int id) {
            this.id = id;
            return this;
        }

        public MenuItemBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public MenuItemBuilder withIngredients(List<String> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public MenuItemBuilder withPrice(double price) {
            this.price = price;
            return this;
        }

        public MenuItemBuilder withCalories(int calories) {
            this.calories = calories;
            return this;
        }

        public MenuItem build() {
            return new MenuItem(this);
        }
    }
}
