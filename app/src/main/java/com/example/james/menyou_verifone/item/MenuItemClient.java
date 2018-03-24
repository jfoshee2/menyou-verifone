package com.example.james.menyou_verifone.item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Interface to make REST API calls
 */

public interface MenuItemClient {

    @GET("api/items")
    Call<List<MenuItem>> getAllMenuItems();

    @POST("api/items")
    Call<MenuItem> createMenuItem(@Body MenuItem item);

    @DELETE("api/items/{id}")
    Call<MenuItem> deleteMenuItem(int id);
}
