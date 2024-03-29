package com.example.james.menyou_verifone.item;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * This class will be used for MenuItemRestAdapter
 */
public class MenuItemRestAdapter {

   private final MenuItemClient menuItemClient;

   @Inject
   public MenuItemRestAdapter(Retrofit retrofit) {
        this.menuItemClient = retrofit.create(MenuItemClient.class);
   }

   public Observable<List<MenuItem>> getAllMenuItems() {
       return menuItemClient.getAllMenuItems();
   }

   public Observable<MenuItem> getMenuItemById(int id) {
       return menuItemClient.getMenuItemById(id);
   }

   public Observable<List<MenuItem>> searchForMenuItems(String param) {
       return menuItemClient.searchForMenuItems(param);
   }

   public void createMenuItem(MenuItem menuItem) {
       menuItemClient.createMenuItem(menuItem)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe();
   }

   public void editMenuItem(int id, MenuItem item) {
       menuItemClient.editMenuItem(id, item)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe();
   }

   public void deleteMenuItem(int id) {
       menuItemClient.deleteMenuItem(id)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe();
   }
}