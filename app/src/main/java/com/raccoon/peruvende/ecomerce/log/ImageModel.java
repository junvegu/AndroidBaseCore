package com.raccoon.peruvende.ecomerce.log;

import android.os.Build;

import com.raccoon.peruvende.BuildConfig;

import java.io.Serializable;

/**
 * Created by junior on 14/02/18.
 */

public class ImageModel implements Serializable {


    private String _id;
    private String imageUrl;


    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getImageUrl() {
        return  imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
