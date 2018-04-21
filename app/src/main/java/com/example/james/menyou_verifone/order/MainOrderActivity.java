package com.example.james.menyou_verifone.order;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.james.menyou_verifone.R;
import com.example.james.menyou_verifone.item.MenuItem;
import com.example.james.menyou_verifone.order.database.OrderDatabaseHandler;
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

    private OrderDatabaseHandler orderDatabaseHandler;

    private Intent orderDetailIntent;

    private ComponentName previousComponentName;

    private final String MAIN_ACTIVITY_NAME = "com.example.james.menyou_verifone.MainActivity";

    private final String ITEM_DETAIL_ACTIVITY_NAME =
            "com.example.james.menyou_verifone.item.MenuItemDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_list);

        orderDatabaseHandler = new OrderDatabaseHandler(this, null);

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

        // sharedPreferences.edit().clear().apply(); // just for now...

        String json = sharedPreferences.getString("ordersJson", "");
        Gson gson = new Gson();
        if (!json.equals("")) {
            orders = gson.fromJson(json, new TypeToken<List<Order>>(){}.getType());
            adapter = new OrderAdapter(this, orders);
            orderListView.setAdapter(adapter);

            // adapter.notifyDataSetChanged();
            // System.out.println(orders);
        }

        orderNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().isEmpty()) {
                    createOrderButton.setEnabled(false);
                } else {
                    createOrderButton.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        createOrderButton.setOnClickListener(view -> {
//            orderDatabaseHandler.addOrder(
//                    new Order(Integer.parseInt(orderNumberEditText.getText().toString()))
//            );

            // updateListView(); // make sure the data was added


            ////////////////////Old using used preference /////////////////
            orders.add(new Order(Integer.parseInt(orderNumberEditText.getText().toString())));
            adapter.notifyDataSetChanged();
            orderNumberEditText.setText("");
        });

        orderListView.setOnItemClickListener((adapterView, view, i, l) -> {

            saveInfo();

            Order order = (Order) adapterView.getItemAtPosition(i);

            if (previousComponentName.getClassName().equals(MAIN_ACTIVITY_NAME)) {
                // Go to order detail activity

                ArrayList<Order> singletonOrder = new ArrayList<>();
                singletonOrder.add(order);

                orderDetailIntent.putParcelableArrayListExtra("orderSingleton",
                        singletonOrder);

                startActivity(orderDetailIntent);
            } else if (previousComponentName.getClassName().equals(ITEM_DETAIL_ACTIVITY_NAME)) {
                ArrayList<MenuItem> itemSingleton = getIntent()
                        .getParcelableArrayListExtra("itemSingleton");

                MenuItem singleMenuItem = itemSingleton.get(0);

                order.addMenuItem(singleMenuItem);
                System.out.println(orders);
            }
        });
    }

//    private void updateListView() {
//        String dbString = orderDatabaseHandler.toString();
//        // Print out string for now to confirm it works
//        System.out.println(dbString);
//    }

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

    }

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