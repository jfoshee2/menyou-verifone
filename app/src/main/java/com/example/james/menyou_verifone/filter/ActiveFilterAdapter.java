package com.example.james.menyou_verifone.filter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.james.menyou_verifone.R;

import java.util.List;

public class ActiveFilterAdapter extends ArrayAdapter<String> {

    private FilterFragment filterFragment;

    public ActiveFilterAdapter(Context context, List<String> filters, FilterFragment filterFragment) {
        super(context, R.layout.active_filter_row, filters);
        this.filterFragment = filterFragment;
    }

    @NonNull
    @Override
    public View getView(int i, View view, @NonNull ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        // @SuppressLint("ViewHolder")
        View customView = layoutInflater.inflate(R.layout.active_filter_row, viewGroup, false);

        String ingredient = getItem(i);

        TextView filterName = customView.findViewById(R.id.filterName);
        filterName.setText(ingredient);

        customView.setOnClickListener((v) -> filterFragment.removeFilter(ingredient));

        return customView;
    }
}
