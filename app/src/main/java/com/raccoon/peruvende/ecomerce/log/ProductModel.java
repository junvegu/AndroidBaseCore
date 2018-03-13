package com.raccoon.peruvende.ecomerce.log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by junior on 14/02/18.
 */

public class ProductModel implements Serializable {


    private String _id;
    private String name;
    private String description;
    private double price;
    private String Category;
    private double discount;
    private CategoryModel category;
    private ArrayList<ImageModel> photos;


    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public ArrayList<ImageModel> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<ImageModel> photos) {
        this.photos = photos;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
