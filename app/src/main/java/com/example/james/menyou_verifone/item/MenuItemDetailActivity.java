package com.example.james.menyou_verifone.item;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.james.menyou_verifone.ApplicationComponent;
import com.example.james.menyou_verifone.DaggerApplicationComponent;
import com.example.james.menyou_verifone.MainActivity;
import com.example.james.menyou_verifone.R;
import com.example.james.menyou_verifone.order.MainOrderActivity;

import java.util.ArrayList;

/**
 * Activity for the MenuItem instance details
 */

public class MenuItemDetailActivity extends AppCompatActivity {

    Intent main;
    Intent orderActivity;

    MenuItem menuItem; // single menu item that has all details info

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        main = new Intent(this, MainActivity.class);
        orderActivity = new Intent(this, MainOrderActivity.class);

        TextView itemDetailNameView = findViewById(R.id.itemDetailName);
        TextView itemDetailPriceView = findViewById(R.id.itemDetailPrice);
        TextView itemDetailCaloriesView = findViewById(R.id.itemDetailCalories);

        Button deleteButton = findViewById(R.id.item_delete_button);
        Button addToOrderButton = findViewById(R.id.add_to_order_button);

        ArrayList<MenuItem> singletonMenuItem = getIntent()
                .getParcelableArrayListExtra("itemSingleton");

        menuItem = singletonMenuItem.get(0); // There should only be one element

        itemDetailNameView.setText(menuItem.getName());

        String priceString = menuItem.getPrice() + "";
        itemDetailPriceView.setText(priceString);

        String calorieString = menuItem.getCalories() + "";
        itemDetailCaloriesView.setText(calorieString);

        deleteButton.setOnClickListener(view -> {
            ApplicationComponent applicationComponent = DaggerApplicationComponent
                    .builder()
                    .build();

            MenuItemRestAdapter menuItemRestAdapter = applicationComponent.getMenuItemRestAdapter();
            menuItemRestAdapter.deleteMenuItem(menuItem.getId());
            startActivity(main);
        });

        // Pass that same ArrayList that only has one item to order activity
        orderActivity.putParcelableArrayListExtra("itemSingleton", singletonMenuItem);
        addToOrderButton.setOnClickListener(view ->
                startActivityForResult(orderActivity, 1));
    }
}
