package com.example.james.menyou_verifone.item;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.james.menyou_verifone.ApplicationComponent;
import com.example.james.menyou_verifone.DaggerApplicationComponent;
import com.example.james.menyou_verifone.MainActivity;
import com.example.james.menyou_verifone.R;
import com.example.james.menyou_verifone.Util;
import com.example.james.menyou_verifone.order.MainOrderActivity;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
        MenuItemRestAdapter menuItemRestAdapter = applicationComponent.getMenuItemRestAdapter();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        main = new Intent(this, MainActivity.class);
        orderActivity = new Intent(this, MainOrderActivity.class);

        EditText itemDetailNameView = findViewById(R.id.itemDetailName);
        EditText itemDetailPriceView = findViewById(R.id.itemDetailPrice);
        EditText itemDetailCaloriesView = findViewById(R.id.itemDetailCalories);
        EditText itemDetailsIngredientsView = findViewById(R.id.ingredientsDetails);

        Button deleteButton = findViewById(R.id.item_delete_button);
        Button addToOrderButton = findViewById(R.id.add_to_order_button);

        Switch editSwitch = findViewById(R.id.edit_switch);

        ArrayList<MenuItem> singletonMenuItem = getIntent()
                .getParcelableArrayListExtra("singleMenuItem");

        int id = getIntent().getIntExtra("menuItemId", 0);

        menuItemRestAdapter.getMenuItemById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(menuItem1 -> {
                    itemDetailNameView.setText(menuItem1.getName());

                    String priceString = menuItem1.getPrice() + "";
                    itemDetailPriceView.setText(priceString);

                    String calorieString = menuItem1.getCalories() + "";
                    itemDetailCaloriesView.setText(calorieString);

                    itemDetailsIngredientsView.setText(menuItem1.getIngredients().toString());
                });


        menuItem = singletonMenuItem.get(0); // There should only be one element

        deleteButton.setOnClickListener(view -> {
//            applicationComponent = DaggerApplicationComponent
//                    .builder()
//                    .build();
//
//            MenuItemRestAdapter menuItemRestAdapter = applicationComponent.getMenuItemRestAdapter();
            menuItemRestAdapter.deleteMenuItem(menuItem.getId());
            startActivity(main);
        });

        // Pass that same ArrayList that only has one item to order activity
        orderActivity.putParcelableArrayListExtra("itemSingleton", singletonMenuItem);
        addToOrderButton.setOnClickListener(view ->
                startActivityForResult(orderActivity, 1));

        editSwitch.setOnCheckedChangeListener((view, checked) -> {
            boolean tempState = checked;

            if (checked) {
                // Change View to editable view
                itemDetailPriceView.setEnabled(true);
                itemDetailNameView.setEnabled(true);
                itemDetailsIngredientsView.setEnabled(true);
                itemDetailCaloriesView.setEnabled(true);
            } else {

                // Change View to non editable view
                itemDetailPriceView.setEnabled(false);
                itemDetailNameView.setEnabled(false);
                itemDetailsIngredientsView.setEnabled(false);
                itemDetailCaloriesView.setEnabled(false);

                //do a put to save the information you just edited.

                menuItem.setName(itemDetailNameView.getText().toString());

                String[] ingredientParts = itemDetailsIngredientsView.getText().toString().split(",");
                List<String> itemIngredients = Arrays.asList(ingredientParts);


                List<String> sanitizedList = Util.sanitizeList(itemIngredients);

                menuItem.setIngredients(sanitizedList);

                menuItem.setPrice(Double.parseDouble(itemDetailPriceView.getText().toString()));

                menuItem.setCalories(Integer.parseInt(itemDetailCaloriesView.getText().toString()));

                menuItemRestAdapter.editMenuItem(menuItem.getId(), menuItem);
            }

        });

    }
}
