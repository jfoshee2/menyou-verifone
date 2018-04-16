package com.example.james.menyou_verifone.filter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.GridView;
import android.widget.Toast;

import com.example.james.menyou_verifone.R;
import com.example.james.menyou_verifone.item.MenuItem;
import com.example.james.menyou_verifone.item.MenuItemAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FilterFragment extends Fragment {

    private List<MenuItem> displayedItems;
    private View view;
    private AutoCompleteTextView autoComplete;
    private Button toggleButton;
    private Button addFilterButton;
    private Button clearFiltersButton;
    private Button applyFiltersButton;
    private GridView activeFilterGrid;
    private GridView mainGrid;
    private List<String> possibleIngredients;
    private List<String> ingredientFilters;
    private FilterFragment filterFragment;
    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.filter_fragment, container, false);
        return view;
    }

    public void registerClickListeners() {
        // button for toggling filter view
        toggleButton = getActivity().findViewById(R.id.filterToggle);
        toggleButton.setOnClickListener(v -> toggleFragmentVisibility());

        // button for adding filters
        addFilterButton = view.findViewById(R.id.addFilter);
        addFilterButton.setOnClickListener(v -> addFilter());

        // button for clearing all filters
        clearFiltersButton = view.findViewById(R.id.clearAll);
        clearFiltersButton.setOnClickListener(v -> clearFilters());

        //button for applying filters
        applyFiltersButton = view.findViewById(R.id.applyFilters);
        applyFiltersButton.setOnClickListener(v -> filterMenuItems());


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            displayedItems = bundle.getParcelableArrayList("currentDisplayedItems");
            determinePossibleIngredients();
            setUpAutoComplete();
        } else {
            displayedItems = new ArrayList<>();
        }

        ingredientFilters = new ArrayList<>();
        fragmentManager = getFragmentManager();
        filterFragment = (FilterFragment) fragmentManager.findFragmentById(R.id.filterFragment);
        activeFilterGrid = view.findViewById(R.id.activeFilterGrid);
        mainGrid = getActivity().findViewById(R.id.grid_view);
        autoComplete = view.findViewById(R.id.ingredientsSuggest);

        registerClickListeners();
        toggleFragmentVisibility();
    }

    public void updateDisplayedItems(Bundle updatedItems) {
        if (updatedItems != null) {
            displayedItems = updatedItems.getParcelableArrayList("currentDisplayedItems");
        }
        determinePossibleIngredients();
        setUpAutoComplete();
    }

    private void determinePossibleIngredients() {
        List<String> possibleIngredients = new ArrayList<>();
        for (MenuItem item: displayedItems) {
            for (String ingredient: item.getIngredients()) {
                if (!possibleIngredients.contains(ingredient)) {
                    possibleIngredients.add(ingredient);
                }
            }
        }
       this.possibleIngredients = possibleIngredients;
    }

    private void filterMenuItems() {
        HashSet<String> filterMap = new HashSet<>(ingredientFilters);
        List<MenuItem> filteredItems = new ArrayList<>(displayedItems);
        for (MenuItem item : displayedItems) {
            String[] itemIngredients = new String[item.getIngredients().size()];
            itemIngredients = item.getIngredients().toArray(itemIngredients);
            for (String ingredient : itemIngredients) {
                if (filterMap.contains(ingredient)) {
                    filteredItems.remove(item);
                }
            }
        }
        mainGrid.setAdapter(new MenuItemAdapter(view.getContext(), filteredItems));
    }

    private void addFilter() {
        AutoCompleteTextView autoSuggest = view.findViewById(R.id.ingredientsSuggest);
        String fieldValue = autoSuggest.getText().toString();
        String toastText = "";
        if (!possibleIngredients.contains(fieldValue)) {
            toastText = "None of our menu items include the ingredient, " + fieldValue + ".";
        } else if (ingredientFilters.contains(fieldValue)) {
            toastText = "This filter is already active.";
        } else {
            ingredientFilters.add(fieldValue);
            activeFilterGrid.setAdapter(new ActiveFilterAdapter(
                    view.getContext(),
                    ingredientFilters,
                    filterFragment));
            clearAutoComplete();
        }
        if (!toastText.isEmpty()) {
            Toast toast = Toast.makeText(
                    view.getContext(),
                    toastText,
                    Toast.LENGTH_SHORT
            );
            toast.show();
        }
    }

    public void removeFilter(String ingredient) {
        if (ingredientFilters.contains(ingredient)) {
            ingredientFilters.remove(ingredient);
            updateActiveFilterGrid();
        }
    }

    private void clearFilters() {
        ingredientFilters.clear();
        updateActiveFilterGrid();
        mainGrid.setAdapter(new MenuItemAdapter(view.getContext(), displayedItems));
    }

    private void updateActiveFilterGrid() {
        activeFilterGrid.setAdapter(new ActiveFilterAdapter(
                view.getContext(),
                ingredientFilters,
                filterFragment));
    }

    private void setUpAutoComplete() {
        String[] ingredientArray = new String[possibleIngredients.size()];
        ingredientArray = possibleIngredients.toArray(ingredientArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                view.getContext(),
                android.R.layout.simple_dropdown_item_1line,
                ingredientArray
        );
        autoComplete.setAdapter(adapter);
    }

    private void clearAutoComplete() {
        autoComplete.setText("");
    }

    private void toggleFragmentVisibility() {
        FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        if (filterFragment.isHidden()) {
            toggleButton.setText(R.string.close_filter);
            fragmentTransaction.show(filterFragment);
        } else {
            toggleButton.setText(R.string.filter_grid);
            fragmentTransaction.hide(filterFragment);
        }
        fragmentTransaction.commit();
    }
}
