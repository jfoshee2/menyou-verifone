package com.example.james.menyou_verifone.item;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface to make REST API calls
 */
public interface MenuItemClient {

    @GET("api/items")
    Observable<List<MenuItem>> getAllMenuItems();

    @GET("api/search")
    Observable<List<MenuItem>> searchForMenuItems(@Query("q") String param);

    @POST("api/items")
    Observable<MenuItem> createMenuItem(@Body MenuItem item);

    @PUT("api/items/{id}")
    Call<MenuItem> editMenuItem(@Path("id") int id, @Body MenuItem item);

    @DELETE("api/items/{id}")
    Call<MenuItem> deleteMenuItem(@Path("id") int id);
}
