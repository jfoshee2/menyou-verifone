package com.example.james.menyou_verifone.order;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.james.menyou_verifone.R;

public class CreateOrderActivity extends AppCompatActivity {

    EditText orderNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_create);

        orderNumberEditText = findViewById(R.id.edit_text_order_number);

        Button createOrderButton = findViewById(R.id.create_order_button);

        createOrderButton.setOnClickListener(view -> {

        });

    }
}