package com.example.james.menyou_verifone.order;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.james.menyou_verifone.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainOrderActivity extends AppCompatActivity {

    EditText orderNumberEditText;
    Button createOrderButton;
    ListView orderListView;

    List<Order> orders;
    OrderAdapter adapter;

    private Intent orderDetailIntent;

    private ComponentName previousComponentName;

    private final String MAIN_ACTIVITY_NAME = "com.example.james.menyou_verifone.MainActivity";

    private final String ITEM_DETAIL_ACTIVITY_NAME =
            "com.example.james.menyou_verifone.item.MenuItemDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_list);

        orderDetailIntent = new Intent(this, OrderDetailActivity.class);

        previousComponentName = getCallingActivity();

        orderNumberEditText = findViewById(R.id.order_number);
        createOrderButton = findViewById(R.id.create_orders);
        orderListView = findViewById(R.id.order_list);

        orders = new ArrayList<>();

        adapter = new OrderAdapter(this, orders);
        orderListView.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences(
                "orders",
                Context.MODE_PRIVATE
        );

        String json = sharedPreferences.getString("ordersJson", "");
        Gson gson = new Gson();
        if (!json.equals("")) {
            orders = gson.fromJson(json, new TypeToken<List<Order>>(){}.getType());
            adapter = new OrderAdapter(this, orders);
            orderListView.setAdapter(adapter);

            // adapter.notifyDataSetChanged();
            // System.out.println(orders);
        }

        createOrderButton.setOnClickListener(view -> {
            orders.add(new Order(Integer.parseInt(orderNumberEditText.getText().toString())));
            adapter.notifyDataSetChanged();
            orderNumberEditText.setText("");
        });

        orderListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Order order = (Order) adapterView.getItemAtPosition(i);

            if (previousComponentName.getClassName().equals(MAIN_ACTIVITY_NAME)) {
                // Go to order detail activity

                ArrayList<Order> singletonOrder = new ArrayList<>();
                singletonOrder.add(order);

                orderDetailIntent.putParcelableArrayListExtra("orderSingleton",
                        singletonOrder);

                startActivity(orderDetailIntent);
            } else if (previousComponentName.getClassName().equals(ITEM_DETAIL_ACTIVITY_NAME)) {
                System.out.println("hello there");
            }
        });


        // displayData();
    }

    @Override
    public void onBackPressed() {
        saveInfo();
        super.onBackPressed();
    }

    private void saveInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(
                "orders",
                Context.MODE_PRIVATE
        );

        Gson gson = new Gson();
        String json = gson.toJson(orders);
        System.out.println(json); // This is to see if it's in the right format

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ordersJson", json);
        editor.apply();
    }

    private void displayData() {
        SharedPreferences sharedPreferences = getSharedPreferences(
                "orders",
                Context.MODE_PRIVATE
        );

        String json = sharedPreferences.getString("ordersJson", "");
        Gson gson = new Gson();
        if (!json.equals("")) {
            orders = gson.fromJson(json, new TypeToken<List<Order>>(){}.getType());
            // adapter.notifyDataSetChanged();
            System.out.println(orders);
        }
//        System.out.println(json); // This is to make sure we are retrieving the right data

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            System.out.println("back key pressed");
//            super.onBackPressed();
//        }
//
//        return super.onKeyDown(keyCode, keyEvent);
//    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putParcelableArrayList(
                "savedOrders",
                (ArrayList<? extends Parcelable>) orders
        );

        System.out.println("Orders were saved");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        orders = savedInstanceState.getParcelableArrayList("savedOrders");
    }
}