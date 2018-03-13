package com.raccoon.peruvende.core;

import java.io.Serializable;

/**
 * Created by junior on 12/03/18.
 */

public class ColorModel implements Serializable {


    private String _id;
    private String value;
    private String name;
    private int quantity;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
