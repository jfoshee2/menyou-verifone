package com.example.james.menyou_verifone.order;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.james.menyou_verifone.R;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<Order> {

    public OrderAdapter(@NonNull Context context, @NonNull List<Order> objects) {
        super(context, R.layout.order_list_row, objects);
    }

    @NonNull
    @Override
    public View getView(int i, View view, @NonNull ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        // @SuppressLint("ViewHolder") // Not sure why android does this....
        View customView = layoutInflater.inflate(
                R.layout.order_list_row,
                viewGroup,
                false
        );

        Order singleOrder = getItem(i); // Grab single menu item at a given index

        TextView itemName = customView.findViewById(R.id.order_label);
        itemName.setText(singleOrder != null ? "Order " + singleOrder.getOrderNumber() : null);

        return customView;
    }
}