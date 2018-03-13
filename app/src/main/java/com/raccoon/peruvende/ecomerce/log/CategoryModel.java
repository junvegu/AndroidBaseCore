package com.raccoon.peruvende.ecomerce.log;

import java.io.Serializable;

/**
 * Created by junior on 14/02/18.
 */

public class CategoryModel implements Serializable {


    private String _id;
    private String name;


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
}
