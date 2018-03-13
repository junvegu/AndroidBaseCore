package com.raccoon.peruvende.ecomerce.log;

import java.io.Serializable;

/**
 * Created by junior on 18/02/18.
 */

public class BussinesModel implements Serializable {


    private String _id;
    private String businessName;
    private String ruc;
    private String address;


    public static String token = "BbjIxxLWgMk0TeZ3X26qD1LyLgJno5JlpYbWFmbdFWU";
    public static String id = "LQDLGcyMPnSzvdSZb";

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBussinessName() {
        return businessName;
    }

    public void setBussinessName(String bussinessName) {
        this.businessName = bussinessName;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
