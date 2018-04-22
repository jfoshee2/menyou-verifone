package com.example.james.menyou_verifone.order;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.james.menyou_verifone.R;
import com.example.james.menyou_verifone.item.MenuItem;
import com.example.james.menyou_verifone.item.MenuItemAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {

    Intent mainOrderIntent;

    Order order;
    List<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);

        mainOrderIntent = new Intent(this, MainOrderActivity.class);

        SharedPreferences sharedPreferences = getSharedPreferences(
                "orders",
                Context.MODE_PRIVATE
        );

        String json = sharedPreferences.getString("ordersJson", "");

        Gson gson = new Gson();


        ArrayList<Order> singletonOrder = getIntent()
                .getParcelableArrayListExtra("orderSingleton");

        order = singletonOrder.get(0); // There should only be one order

        orders = gson.fromJson(json, new TypeToken<List<Order>>(){}.getType());

        order = orders.get(findOrder(order.getOrderNumber(), orders));

        List<MenuItem> menuItems = order.getMenuItems();
        ListView listView = findViewById(R.id.order_items);

        listView.setAdapter(new MenuItemAdapter(this, menuItems));

        TextView priceTextView = findViewById(R.id.order_price);

        Button payButton = findViewById(R.id.pay_button);

        String priceText = order.getTotal() + "";
        priceTextView.setText(priceText);


        payButton.setOnClickListener(view -> {



            System.out.println("\t\t\t\t\t" + json);

            if (!json.equals("")) {
                orders = gson.fromJson(json, new TypeToken<List<Order>>(){}.getType());
                // adapter.notifyDataSetChanged();
                orders.remove(findOrder(order.getOrderNumber(), orders));

                System.out.println(gson.toJson(orders));

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("ordersJson", gson.toJson(orders));
                editor.apply();
            }

            startActivity(mainOrderIntent);

        });


    }

    private int findOrder(int orderId, List<Order> orders) {
        int result = 0;

        for (int i = 0; i < orders.size(); i++) {
            if (orderId == orders.get(i).getOrderNumber()) {
                return i;
            }
        }

        return result;
    }

}