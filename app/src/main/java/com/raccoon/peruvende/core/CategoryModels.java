package com.raccoon.peruvende.core;

import java.io.Serializable;

/**
 * Created by junior on 12/03/18.
 */

public class CategoryModels implements Serializable {


    private String _id;
    private String name;
    private CategoryModels parent;

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

    public CategoryModels getParent() {
        return parent;
    }

    public void setParent(CategoryModels parent) {
        this.parent = parent;
    }
}
