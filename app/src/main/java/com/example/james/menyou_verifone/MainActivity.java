package com.example.james.menyou_verifone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SearchView;

import com.example.james.menyou_verifone.item.CreateMenuItemActivity;
import com.example.james.menyou_verifone.item.MenuItem;
import com.example.james.menyou_verifone.item.MenuItemAdapter;
import com.example.james.menyou_verifone.item.MenuItemDetailActivity;
import com.example.james.menyou_verifone.item.MenuItemRestAdapter;
import com.example.james.menyou_verifone.order.MainOrderActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends Activity {

    private Intent itemDetailIntent;
    private Intent createItemIntent;
    private Intent orderIntent;

    private MenuItemRestAdapter menuItemRestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemDetailIntent = new Intent(this, MenuItemDetailActivity.class);
        createItemIntent = new Intent(this, CreateMenuItemActivity.class);
        orderIntent = new Intent(this, MainOrderActivity.class);

        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
        menuItemRestAdapter = applicationComponent.getMenuItemRestAdapter();

        SearchView searchView = findViewById(R.id.search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                menuItemRestAdapter.searchForMenuItems(s)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(list -> adaptListView(list));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if (s.isEmpty()) {
                    menuItemRestAdapter.getAllMenuItems()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(list -> adaptListView(list));
                }

                return false; // TODO: Implement Suggest feature
            }
        });

        menuItemRestAdapter.getAllMenuItems()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::adaptListView);

        Button createNewItemButton = findViewById(R.id.button);
        Button ordersActivityButton = findViewById(R.id.order_activity_button);

        createNewItemButton.setOnClickListener(view -> startActivity(createItemIntent));
        ordersActivityButton.setOnClickListener(view -> startActivityForResult(
                orderIntent, 1));
    }

    private void adaptListView(List<MenuItem> list) {

        GridView menuGridView = findViewById(R.id.grid_view);

        menuGridView.setAdapter(new MenuItemAdapter(MainActivity.this, list));

        menuGridView.setOnItemClickListener((adapterView, view, i, l) -> {
            MenuItem menuItem = (MenuItem) adapterView.getItemAtPosition(i);

            ArrayList<MenuItem> singletonMenuItem = new ArrayList<>();
            singletonMenuItem.add(menuItem);

            // System.out.println("\t\t\t\t" + menuItem.getId());

            // System.out.println(singletonMenuItem.get(0).getPrice() + "\t\t\t\t\t" + singletonMenuItem.get(0).getCalories());

            itemDetailIntent.putExtra("menuItemId", menuItem.getId());

            itemDetailIntent.putParcelableArrayListExtra("singleMenuItem", singletonMenuItem);

            startActivity(itemDetailIntent);
        });
    }
}