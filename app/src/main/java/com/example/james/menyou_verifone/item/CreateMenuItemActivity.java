package com.example.james.menyou_verifone.item;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.james.menyou_verifone.ApplicationComponent;
import com.example.james.menyou_verifone.DaggerApplicationComponent;
import com.example.james.menyou_verifone.MainActivity;
import com.example.james.menyou_verifone.R;
import com.example.james.menyou_verifone.Util;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Activity for creating menu item
 */

public class CreateMenuItemActivity extends AppCompatActivity {

    EditText name;
    EditText ingredients;
    EditText price;
    EditText calories;

    Button createButton;

    Intent main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_create);

        name = findViewById(R.id.itemCreateName);
        ingredients = findViewById(R.id.itemCreateIngredients);
        price = findViewById(R.id.itemCreatePrice);
        calories = findViewById(R.id.itemCreateCalories);

        main = new Intent(this, MainActivity.class);

        createButton = findViewById(R.id.itemCreateButton);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkFieldsForEmptyValues();
            }
        };

        name.addTextChangedListener(textWatcher);
        ingredients.addTextChangedListener(textWatcher);
        price.addTextChangedListener(textWatcher);
        calories.addTextChangedListener(textWatcher);

        createButton.setOnClickListener(view -> {

            String[] ingredientParts = ingredients.getText().toString().split(",");
            List<String> itemIngredients = Arrays.asList(ingredientParts);

           List<String> santizedList = Util.sanitizeList(itemIngredients);


            MenuItem item = new MenuItem(name.getText() + "",
                    itemIngredients,
                    Double.parseDouble(price.getText() + ""),
                    Integer.parseInt(calories.getText() + "")
            );

            ApplicationComponent applicationComponent = DaggerApplicationComponent
                    .builder()
                    .build();

            MenuItemRestAdapter menuItemRestAdapter = applicationComponent
                    .getMenuItemRestAdapter();

            menuItemRestAdapter.createMenuItem(item);

            startActivity(main);
        });

    }

    private void checkFieldsForEmptyValues() {
        if (name.getText().toString().isEmpty() || ingredients.getText().toString().isEmpty()
                || price.getText().toString().isEmpty()
                || calories.getText().toString().isEmpty()) {
            createButton.setEnabled(false);
        } else {
            createButton.setEnabled(true);
        }
    }
}
