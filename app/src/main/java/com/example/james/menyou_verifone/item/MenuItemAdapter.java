package com.example.james.menyou_verifone.item;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.james.menyou_verifone.R;

import java.util.List;

/**
 * Array adapter for an array of MenuItem instances
 */

public class MenuItemAdapter extends ArrayAdapter<MenuItem> {

    public MenuItemAdapter(
            @NonNull Context context,
            @NonNull List<MenuItem> objects
    ) {
        super(context, R.layout.item_row, objects);
    }


    @NonNull
    @Override
    public View getView(int i, View view, @NonNull ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        // @SuppressLint("ViewHolder") // Not sure why android does this....
        View customView = layoutInflater.inflate(R.layout.item_row, viewGroup, false);

        MenuItem singleMenuItem = getItem(i); // Grab single menu item at a given index

        TextView itemName = customView.findViewById(R.id.itemName);
        itemName.setText(singleMenuItem != null ? singleMenuItem.getName() : null);

        return customView;
    }
}
