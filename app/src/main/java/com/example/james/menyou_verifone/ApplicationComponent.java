package com.example.james.menyou_verifone;

import com.example.james.menyou_verifone.item.MenuItemRestAdapter;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    MenuItemRestAdapter getMenuItemRestAdapter();
}