package com.example.james.menyou_verifone.filter;

import android.support.annotation.NonNull;
import android.widget.Button;

public class FilterViewHolder {

    private Button filterToggleButton;
    private Button addFilterButton;
    private Button applyFilterButton;
    private Button clearFiltersButton;

    public FilterViewHolder(
            @NonNull Button filterToggleButton,
            @NonNull Button addFilterButton,
            @NonNull Button applyFilterButton,
            @NonNull Button clearFiltersButton
                            ) {
        this.filterToggleButton = filterToggleButton;
        this.addFilterButton = addFilterButton;
        this.applyFilterButton = applyFilterButton;
        this.clearFiltersButton = clearFiltersButton;
    }

    public Button getFilterToggleButton() {
        return filterToggleButton;
    }

    public Button getAddFilterButton() {
        return addFilterButton;
    }

    public Button getApplyFilterButton() {
        return applyFilterButton;
    }

    public Button getClearFiltersButton() {
        return clearFiltersButton;
    }
}
