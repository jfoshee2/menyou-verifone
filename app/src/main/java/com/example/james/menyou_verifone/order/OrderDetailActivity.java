package com.example.james.menyou_verifone.order;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.james.menyou_verifone.R;
import com.example.james.menyou_verifone.item.MenuItem;
import com.example.james.menyou_verifone.item.MenuItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);

        ArrayList<Order> singletonOrder = getIntent()
                .getParcelableArrayListExtra("orderSingleton");

        Order order = singletonOrder.get(0); // There should only be one order

        List<MenuItem> menuItems = order.getMenuItems();

        ListView listView = findViewById(R.id.order_items);

        listView.setAdapter(new MenuItemAdapter(this, menuItems));


    }
}