package com.raccoon.peruvende.ecomerce.log;

import java.io.Serializable;

/**
 * Created by junior on 17/02/18.
 */

public class BaseModel<T> implements Serializable {

    private T data;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
