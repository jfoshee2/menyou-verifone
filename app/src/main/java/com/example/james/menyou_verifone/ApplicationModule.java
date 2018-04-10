package com.example.james.menyou_verifone;

import com.example.james.menyou_verifone.item.MenuItemRestAdapter;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Used for
 */
@Module
public class ApplicationModule {

    @Provides
    @Singleton
    Retrofit provideRetrofit() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://menyou-sp.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    MenuItemRestAdapter provideMenuItemRestAdapter(Retrofit retrofit) {
        return new MenuItemRestAdapter(retrofit);
    }
}