package com.example.james.menyou_verifone.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.james.menyou_verifone.item.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class Order implements Parcelable {

    private int orderNumber;
    private List<MenuItem> menuItems;

    public Order(int orderNumber) {
        this.orderNumber = orderNumber;
        this.menuItems = new ArrayList<>();
    }

    protected Order(Parcel in) {
        orderNumber = in.readInt();
        menuItems = in.createTypedArrayList(MenuItem.CREATOR);
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public int getOrderNumber() {
        return orderNumber;
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public double getTotal() {
        double result = 0.0;
        for (MenuItem menuItem : menuItems) {
            result += menuItem.getPrice();
        }
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(orderNumber);
        parcel.writeTypedList(menuItems);
    }
}