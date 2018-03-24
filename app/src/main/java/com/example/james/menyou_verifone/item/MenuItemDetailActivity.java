package com.example.james.menyou_verifone.item;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.james.menyou_verifone.R;

/**
 * Activity for the MenuItem instance details
 */

public class MenuItemDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        TextView itemDetailNameView = findViewById(R.id.itemDetailName);
        TextView itemDetailPriceView = findViewById(R.id.itemDetailPrice);
        TextView itemDetailCaloriesView = findViewById(R.id.itemDetailCalories);

        String detailName = getIntent().getExtras().getString("detailName");
        double price = getIntent().getExtras().getDouble("detailPrice");
        int calories = getIntent().getExtras().getInt("detailCalories");

        itemDetailNameView.setText(detailName);

        String priceString = price + "";
        itemDetailPriceView.setText(priceString);

        String calorieString = calories + "";
        itemDetailCaloriesView.setText(calorieString);

    }
}
