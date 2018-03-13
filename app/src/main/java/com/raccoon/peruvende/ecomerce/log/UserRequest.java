package com.raccoon.peruvende.ecomerce.log;


import com.raccoon.peruvende.core.CategoryModels;
import com.raccoon.peruvende.core.ColorModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by luizkawai on 8/11/17.
 */

public interface UserRequest {


    @GET("products?onlyOffers=false")
    Call<BaseModel<ArrayList<ProductModel>>> getProducts(@Header("X-Auth-Token") String token, @Header("X-User-Id") String id);

    @GET("products?onlyOffers=true")
    Call<BaseModel<ArrayList<ProductModel>>> getProductsOferts(@Header("X-Auth-Token") String token, @Header("X-User-Id") String id);




    @GET("products?category=RSvqMJmGWd3XGwqXa")
    Call<BaseModel<ArrayList<ProductModel>>> getGarments(@Header("X-Auth-Token") String token, @Header("X-User-Id") String id);



    @GET("products")
    Call<BaseModel<ArrayList<ProductModel>>> getGarments(@Header("X-Auth-Token") String token, @Header("X-User-Id") String id,@QueryMap Map<String, String> options);


    @GET("products?category=n7htMKPLA7QqqJsqQ")
    Call<BaseModel<ArrayList<ProductModel>>> getHome(@Header("X-Auth-Token") String token, @Header("X-User-Id") String id);



    @GET("products?category=fucLHkhXhzvjLgEWM")
    Call<BaseModel<ArrayList<ProductModel>>> getAccesories(@Header("X-Auth-Token") String token, @Header("X-User-Id") String id);


    @GET("categories")
    Call<BaseModel<ArrayList<CategoryModels>>> getCategories(@Header("X-Auth-Token") String token,
                                                             @Header("X-User-Id") String id);


    @GET("colors/summary")
    Call<BaseModel<ArrayList<ColorModel>>> getColors(@Header("X-Auth-Token") String token,
                                                     @Header("X-User-Id") String id);

    @GET("products")
    Call<BaseModel<ArrayList<ProductModel>>> searchProducts(@Header("X-Auth-Token") String token,
                                                            @Header("X-User-Id") String id,
                                                            @Query("search") String search);

    @GET("businesses")
    Call<BaseModel<ArrayList<BussinesModel>>> searchCompany(@Header("X-Auth-Token") String token,
                                                            @Header("X-User-Id") String id,
                                                            @Query("search") String search);
}
