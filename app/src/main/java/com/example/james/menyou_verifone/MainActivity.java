package com.example.james.menyou_verifone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.ListView;

import com.example.james.menyou_verifone.item.CreateMenuItemActivity;
import com.example.james.menyou_verifone.item.MenuItem;
import com.example.james.menyou_verifone.item.MenuItemAdapter;
import com.example.james.menyou_verifone.item.MenuItemDetailActivity;
import com.example.james.menyou_verifone.item.MenuItemRestAdapter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends Activity {

    private Intent itemDetailIntent;
    private Intent createItemIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemDetailIntent = new Intent(this, MenuItemDetailActivity.class);
        createItemIntent = new Intent(this, CreateMenuItemActivity.class);

        FloatingActionButton createNewItemButton = findViewById(R.id.fab);

        createNewItemButton.setOnClickListener(view -> startActivity(createItemIntent));

        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
        MenuItemRestAdapter menuItemRestAdapter = applicationComponent.getMenuItemRestAdapter();

        menuItemRestAdapter.getAllMenuItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    ListView menuListView = findViewById(R.id.menuListView);

                    menuListView.setAdapter(new MenuItemAdapter(MainActivity.this, list));
                    menuListView.setOnItemClickListener((adapterView, view, i, l) -> {
                        MenuItem menuItem = (MenuItem) adapterView.getItemAtPosition(i);

                        itemDetailIntent.putExtra("detailName", menuItem.getName());
                        itemDetailIntent.putExtra("detailPrice", menuItem.getPrice());
                        itemDetailIntent.putExtra("detailCalories", menuItem.getCalories());

                        startActivity(itemDetailIntent);
                    });
                });
    }
}