package com.example.james.menyou_verifone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.james.menyou_verifone.item.CreateMenuItemActivity;
import com.example.james.menyou_verifone.item.MenuItem;
import com.example.james.menyou_verifone.item.MenuItemAdapter;
import com.example.james.menyou_verifone.item.MenuItemClient;
import com.example.james.menyou_verifone.item.MenuItemDetailActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        createNewItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(createItemIntent);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://menyou-sp.appspot.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MenuItemClient client = retrofit.create(MenuItemClient.class);

        Call<List<MenuItem>> call = client.getAllMenuItems();

        call.enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<MenuItem>> call,
                                   @NonNull Response<List<MenuItem>> response) {

                List<MenuItem> menuItems = response.body();
                ListView menuListView = findViewById(R.id.menuListView);

                assert menuItems != null;
                menuListView.setAdapter(new MenuItemAdapter(MainActivity.this,
                        menuItems));

                menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        MenuItem menuItem = (MenuItem) adapterView.getItemAtPosition(i);

                        itemDetailIntent.putExtra("detailName", menuItem.getName());
                        itemDetailIntent.putExtra("detailPrice", menuItem.getPrice());
                        itemDetailIntent.putExtra("detailCalories", menuItem.getCalories());

                        startActivity(itemDetailIntent);
                    }
                });
            }

            @Override
            public void onFailure(@NonNull Call<List<MenuItem>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}